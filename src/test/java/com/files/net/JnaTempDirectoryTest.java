package com.files.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

// Which directories JNA may write its native library to on Windows, decided from ACLs like those
// Windows gives its directories by default. These are the ACLs as Java reads them, made up here;
// that Windows gives them is not checked. And which JNA settings leave that decision to the
// application, and what prepare gives JNA, with the system properties of the test's own folder.
public class JnaTempDirectoryTest {
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private static final UserPrincipal USER = principal("PC\\user");
  private static final UserPrincipal OTHER = principal("PC\\other");
  private static final UserPrincipal SYSTEM = principal("NT AUTHORITY\\SYSTEM");
  private static final UserPrincipal ADMINISTRATORS = principal("BUILTIN\\Administrators");
  private static final UserPrincipal USERS = principal("BUILTIN\\Users");
  private static final UserPrincipal AUTHENTICATED_USERS = principal("NT AUTHORITY\\Authenticated Users");
  private static final UserPrincipal EVERYONE = principal("Everyone");
  private static final UserPrincipal CREATOR_OWNER = principal("CREATOR OWNER");
  private static final UserPrincipal TRUSTED_INSTALLER = principal("NT SERVICE\\TrustedInstaller");
  private static final Set<AclEntryPermission> FULL = EnumSet.allOf(AclEntryPermission.class);
  private static final Set<AclEntryPermission> READ = EnumSet.of(AclEntryPermission.READ_DATA, AclEntryPermission.READ_ATTRIBUTES,
      AclEntryPermission.READ_NAMED_ATTRS, AclEntryPermission.READ_ACL, AclEntryPermission.EXECUTE, AclEntryPermission.SYNCHRONIZE);
  private static final Set<AclEntryPermission> MODIFY = modify();

  private static final AclFileAttributeView DRIVE = view(TRUSTED_INSTALLER,
      allow(ADMINISTRATORS, FULL), allow(SYSTEM, FULL), allow(USERS, READ),
      allow(AUTHENTICATED_USERS, EnumSet.of(AclEntryPermission.APPEND_DATA)),
      allow(AUTHENTICATED_USERS, MODIFY, AclEntryFlag.INHERIT_ONLY, AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT));
  private static final AclFileAttributeView USERS_DIRECTORY = view(SYSTEM,
      allow(SYSTEM, FULL), allow(ADMINISTRATORS, FULL), allow(USERS, READ), allow(EVERYONE, READ));
  private static final AclFileAttributeView PROFILE = view(USER, allow(SYSTEM, FULL), allow(ADMINISTRATORS, FULL), allow(USER, FULL));
  private static final AclFileAttributeView WINDOWS = view(TRUSTED_INSTALLER,
      allow(TRUSTED_INSTALLER, FULL), allow(SYSTEM, MODIFY), allow(ADMINISTRATORS, MODIFY), allow(USERS, READ),
      allow(CREATOR_OWNER, FULL, AclEntryFlag.INHERIT_ONLY, AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT));
  private static final AclFileAttributeView SYSTEM_PROFILE = view(SYSTEM, allow(SYSTEM, FULL), allow(ADMINISTRATORS, FULL));
  // C:\Windows\Temp, where users may create files and directories.
  private static final AclFileAttributeView WINDOWS_TEMP = view(SYSTEM,
      allow(SYSTEM, FULL), allow(ADMINISTRATORS, FULL),
      allow(CREATOR_OWNER, FULL, AclEntryFlag.INHERIT_ONLY, AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT),
      allow(USERS, EnumSet.of(AclEntryPermission.WRITE_DATA, AclEntryPermission.APPEND_DATA, AclEntryPermission.EXECUTE),
          AclEntryFlag.DIRECTORY_INHERIT));

  // The user's AppData\Local\Temp, and their profile directory, in C:\Users.
  private static List<AclFileAttributeView> userTemp() {
    return Arrays.asList(PROFILE, PROFILE, PROFILE, PROFILE, USERS_DIRECTORY, DRIVE);
  }

  private static List<AclFileAttributeView> userProfile() {
    return Arrays.asList(PROFILE, USERS_DIRECTORY, DRIVE);
  }

  // C:\Windows\system32\config\systemprofile, for LocalSystem.
  private static List<AclFileAttributeView> systemProfile() {
    return Arrays.asList(SYSTEM_PROFILE, SYSTEM_PROFILE, WINDOWS, WINDOWS, DRIVE);
  }

  @Test
  public void anAccountsOwnTempDirectoryIsPrivate() throws Exception {
    Set<UserPrincipal> trusted = JnaTempDirectory.trusted(userProfile());
    assertEquals(new HashSet<>(Arrays.asList(USER, SYSTEM, ADMINISTRATORS, TRUSTED_INSTALLER)), trusted);
    assertTrue(JnaTempDirectory.isPrivate(userTemp(), trusted));
  }

  @Test
  public void theSharedWindowsTempIsNotPrivateButLocalSystemsOwnTempIs() throws Exception {
    Set<UserPrincipal> trusted = JnaTempDirectory.trusted(systemProfile());
    assertEquals(new HashSet<>(Arrays.asList(SYSTEM, ADMINISTRATORS, TRUSTED_INSTALLER)), trusted);
    assertFalse(JnaTempDirectory.isPrivate(Arrays.asList(WINDOWS_TEMP, WINDOWS, DRIVE), trusted));
    // C:\Windows\system32\config\systemprofile\AppData\Local\Temp.
    List<AclFileAttributeView> ownTemp = new ArrayList<>(Arrays.asList(SYSTEM_PROFILE, SYSTEM_PROFILE, SYSTEM_PROFILE));
    ownTemp.addAll(systemProfile());
    assertTrue(JnaTempDirectory.isPrivate(ownTemp, trusted));
  }

  @Test
  public void aDirectoryAnotherUserCreatedInTheSharedTempIsNotPrivate() throws Exception {
    Set<UserPrincipal> trusted = JnaTempDirectory.trusted(systemProfile());
    AclFileAttributeView planted = view(OTHER, allow(SYSTEM, FULL), allow(ADMINISTRATORS, FULL), allow(OTHER, FULL));
    assertFalse(JnaTempDirectory.isPrivate(Arrays.asList(planted, WINDOWS_TEMP, WINDOWS, DRIVE), trusted));
    AclFileAttributeView given = view(SYSTEM, allow(SYSTEM, FULL), allow(ADMINISTRATORS, FULL), allow(OTHER, FULL));
    assertFalse("owned by a trusted principal, but another may change it",
        JnaTempDirectory.isPrivate(Arrays.asList(given, WINDOWS_TEMP, WINDOWS, DRIVE), trusted));
  }

  @Test
  public void anyEntryForAnotherPrincipalMakesADirectoryNotPrivate() throws Exception {
    Set<UserPrincipal> trusted = JnaTempDirectory.trusted(userProfile());
    // An entry that Java reads as allowing nothing may allow generic rights, which Java cannot see,
    // to what is created in the directory.
    AclFileAttributeView generic = view(USER, allow(SYSTEM, FULL), allow(USER, FULL),
        allow(USERS, EnumSet.noneOf(AclEntryPermission.class), AclEntryFlag.INHERIT_ONLY, AclEntryFlag.FILE_INHERIT));
    List<AclFileAttributeView> chain = new ArrayList<>(userTemp());
    chain.set(0, generic);
    assertFalse(JnaTempDirectory.isPrivate(chain, trusted));
  }

  @Test
  public void aDirectoryAboveThatAnotherPrincipalMayReplaceIsNotPrivate() throws Exception {
    Set<UserPrincipal> trusted = JnaTempDirectory.trusted(userProfile());
    AclFileAttributeView replaceable = view(USER, allow(USER, FULL), allow(USERS, EnumSet.of(AclEntryPermission.DELETE_CHILD)));
    List<AclFileAttributeView> chain = new ArrayList<>(userTemp());
    chain.set(1, replaceable);
    assertFalse(JnaTempDirectory.isPrivate(chain, trusted));
    AclFileAttributeView foreign = view(OTHER, allow(USER, FULL));
    chain.set(1, foreign);
    assertFalse("a directory above owned by another principal", JnaTempDirectory.isPrivate(chain, trusted));
  }

  @Test
  public void anEmptyAclIsNotPrivate() throws Exception {
    Set<UserPrincipal> trusted = JnaTempDirectory.trusted(userProfile());
    List<AclFileAttributeView> chain = new ArrayList<>(userTemp());
    chain.set(0, view(USER));
    assertFalse(JnaTempDirectory.isPrivate(chain, trusted));
  }

  @Test
  public void aBootLibraryPathOrTheSystemsLibrariesDoNotSkipTheCheckOfWhereJnaWouldExtract() throws Exception {
    Map<String, String> defaults = ownedSettings();
    Map<String, String> elsewhereFirst = new HashMap<>(defaults);
    elsewhereFirst.put("jna.boot.library.path", folder.newFolder("boot").toString());
    elsewhereFirst.put("jna.nosys", "false");
    // JNA still extracts its library when it does not find it there, so the decision is the same.
    assertEquals(prepareWith(defaults), prepareWith(elsewhereFirst));
  }

  @Test
  public void anExplicitExtractionDirectoryOrNoExtractionIsLeftToTheApplication() throws Exception {
    String chosen = folder.newFolder("chosen").toString();
    Map<String, String> explicit = ownedSettings();
    explicit.put("jna.tmpdir", chosen);
    assertEquals("jna.tmpdir=" + chosen, prepareWith(explicit));
    for (String off : new String[] {"jna.nounpack", "jna.noclasspath"}) {
      Map<String, String> noExtraction = ownedSettings();
      noExtraction.put(off, "true");
      assertEquals(off, "jna.tmpdir=null", prepareWith(noExtraction));
    }
  }

  @Test
  public void aPrivateJavaTmpdirIsGivenToJnaByItsRealPath() throws Exception {
    org.junit.Assume.assumeTrue(System.getProperty("os.name", "").startsWith("Windows"));
    Path temp = folder.newFolder("temp").toPath();
    Map<String, String> settings = new HashMap<>();
    settings.put("java.io.tmpdir", temp.resolve(".").toString());
    assertEquals("jna.tmpdir=" + temp.toRealPath(), prepareWith(settings));
  }

  // System properties for prepare that keep it to the test's own folder: no JNA settings, and
  // java.io.tmpdir and user.home in the folder.
  private Map<String, String> ownedSettings() throws Exception {
    Map<String, String> settings = new HashMap<>();
    settings.put("java.io.tmpdir", folder.newFolder().toString());
    settings.put("user.home", folder.newFolder().toString());
    return settings;
  }

  // What prepare decides with the JNA settings cleared but for those given, and the other system
  // properties given: "jna.tmpdir=" and its value then if JNA may be used, or why not. Every
  // property is restored afterwards.
  private static String prepareWith(Map<String, String> settings) {
    Set<String> names = new HashSet<>(Arrays.asList("jna.tmpdir", "jna.boot.library.path", "jna.nosys", "jna.nounpack", "jna.noclasspath"));
    names.addAll(settings.keySet());
    Map<String, String> saved = new HashMap<>();
    for (String name : names) {
      saved.put(name, System.getProperty(name));
    }
    try {
      for (String name : names) {
        setProperty(name, settings.get(name));
      }
      java.io.IOException refused = JnaTempDirectory.prepare();
      return refused != null ? "refused: " + refused.getMessage() : "jna.tmpdir=" + System.getProperty("jna.tmpdir");
    } finally {
      for (String name : names) {
        setProperty(name, saved.get(name));
      }
    }
  }

  private static void setProperty(String name, String value) {
    if (value == null) {
      System.clearProperty(name);
    } else {
      System.setProperty(name, value);
    }
  }

  private static Set<AclEntryPermission> modify() {
    Set<AclEntryPermission> modify = EnumSet.allOf(AclEntryPermission.class);
    modify.removeAll(EnumSet.of(AclEntryPermission.DELETE_CHILD, AclEntryPermission.WRITE_ACL, AclEntryPermission.WRITE_OWNER));
    return modify;
  }

  private static AclEntry allow(UserPrincipal principal, Set<AclEntryPermission> permissions, AclEntryFlag... flags) {
    return AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(principal).setPermissions(permissions).setFlags(flags).build();
  }

  private static UserPrincipal principal(String name) {
    return new UserPrincipal() {
      @Override
      public String getName() {
        return name;
      }

      @Override
      public boolean equals(Object other) {
        return other instanceof UserPrincipal && ((UserPrincipal) other).getName().equals(name);
      }

      @Override
      public int hashCode() {
        return name.hashCode();
      }
    };
  }

  private static AclFileAttributeView view(UserPrincipal owner, AclEntry... acl) {
    List<AclEntry> entries = Collections.unmodifiableList(Arrays.asList(acl));
    return new AclFileAttributeView() {
      @Override
      public String name() {
        return "acl";
      }

      @Override
      public List<AclEntry> getAcl() {
        return entries;
      }

      @Override
      public void setAcl(List<AclEntry> acl) {
        throw new UnsupportedOperationException();
      }

      @Override
      public UserPrincipal getOwner() {
        return owner;
      }

      @Override
      public void setOwner(UserPrincipal owner) {
        throw new UnsupportedOperationException();
      }
    };
  }
}
