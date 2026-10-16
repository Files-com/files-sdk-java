package com.files.net;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Where JNA writes its native library on Windows, decided before JNA is first used, from the ACLs
// that Java reads, without JNA.
//
// Unless told otherwise, JNA extracts its native library from its JAR into a temporary directory,
// writes it there by name and then loads it by path (Native.loadNativeDispatchLibrary): whoever can
// change that directory, what is created in it, or the path to it, could have the process run code
// of theirs. By default the directory is jna-<number> in java.io.tmpdir, which for a service may be
// a shared directory such as C:\Windows\Temp. JNA extracts its library unless the application set
// jna.tmpdir, the directory to extract it to, or turned extraction off with jna.nounpack or
// jna.noclasspath; jna.boot.library.path and jna.nosys=false only add places JNA looks first, and
// where it does not find its library there it extracts it as usual (Native.loadNativeDispatchLibrary,
// getTempDir). In every other case, JNA is used only from a private directory, one that only
// principals as trusted as the user's own profile directory can change: java.io.tmpdir if it is
// private, or else the account's own Temp directory under its profile if that one is. jna.tmpdir is
// set to the real path of the directory that was checked, if no one has set it meanwhile, so that
// JNA does not reach it through links that were not checked; it stays set, so that JNA loaded later
// by other code in the same JVM extracts there too. Where neither is private, JNA is not used, and
// downloads that need it fail.
//
// Java cannot see generic rights, or entries of other types than allow and deny, in a Windows ACL,
// so a private directory is one whose every allowing entry is for a trusted principal, whatever it
// seems to allow.
final class JnaTempDirectory {
  private static final String PROPERTY = "jna.tmpdir";
  // What lets a principal take over the access to a directory, or replace it or what is in it.
  private static final Set<AclEntryPermission> CONTROL = EnumSet.of(AclEntryPermission.WRITE_ACL, AclEntryPermission.WRITE_OWNER);
  private static final Set<AclEntryPermission> REPLACE = EnumSet.of(AclEntryPermission.DELETE, AclEntryPermission.DELETE_CHILD,
      AclEntryPermission.WRITE_ACL, AclEntryPermission.WRITE_OWNER);

  private JnaTempDirectory() {
  }

  // Null if JNA may be used; otherwise why not.
  static IOException prepare() {
    if (extractionChosenByApplication()) {
      return null;
    }
    Set<UserPrincipal> trusted = trusted(Paths.get(System.getProperty("user.home")));
    Path temp = Paths.get(System.getProperty("java.io.tmpdir"));
    Path chosen = privateRealPath(temp, trusted);
    if (chosen == null) {
      Path own = ownTemp();
      chosen = own != null ? privateRealPath(own, trusted) : null;
    }
    if (chosen == null) {
      return new IOException("JNA would write its native library to " + temp + ", which accounts other than this one may be able to change; "
          + "set the system property jna.tmpdir to a directory only this account and administrators can change, or, where "
          + "jna.boot.library.path supplies JNA's native library, set jna.nounpack=true");
    }
    System.getProperties().putIfAbsent(PROPERTY, chosen.toString());
    return null;
  }

  // Whether the application chose the directory JNA extracts its native library to, or that JNA
  // extracts it nowhere.
  private static boolean extractionChosenByApplication() {
    return System.getProperty(PROPERTY) != null || Boolean.getBoolean("jna.nounpack") || Boolean.getBoolean("jna.noclasspath");
  }

  // The account's own Temp directory under its profile, created if it is missing, or null.
  private static Path ownTemp() {
    String local = System.getenv("LOCALAPPDATA");
    Path directory = local != null ? Paths.get(local) : Paths.get(System.getProperty("user.home"), "AppData", "Local");
    Path temp = directory.resolve("Temp");
    try {
      if (Files.isDirectory(directory)) {
        Files.createDirectory(temp);
      }
    } catch (FileAlreadyExistsException e) {
      // As expected.
    } catch (IOException | RuntimeException e) {
      return null;
    }
    return temp;
  }

  private static Set<UserPrincipal> trusted(Path home) {
    try {
      return trusted(views(home.toRealPath()));
    } catch (IOException | RuntimeException e) {
      return Collections.emptySet();
    }
  }

  // The real path of the directory that path leads to, if that directory is private to trusted;
  // otherwise null.
  private static Path privateRealPath(Path path, Set<UserPrincipal> trusted) {
    try {
      Path real = path.toRealPath();
      return isPrivate(views(real), trusted) ? real : null;
    } catch (IOException | RuntimeException e) {
      return null;
    }
  }

  // The ACL views of the directory real, a real path, and of every directory above it, in that
  // order.
  private static List<AclFileAttributeView> views(Path real) throws IOException {
    if (!Files.isDirectory(real, LinkOption.NOFOLLOW_LINKS)) {
      throw new IOException(real + " is not a directory");
    }
    List<AclFileAttributeView> views = new ArrayList<>();
    for (Path directory = real; directory != null; directory = directory.getParent()) {
      AclFileAttributeView view = Files.getFileAttributeView(directory, AclFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
      if (view == null) {
        throw new IOException(directory + " has no ACL");
      }
      views.add(view);
    }
    return views;
  }

  // The principals as trusted as the user's own profile, given the views of the profile directory
  // and of the directories above it: their owners, and those allowed to change their ACL or owner,
  // who could take any of them over already.
  static Set<UserPrincipal> trusted(List<AclFileAttributeView> profile) throws IOException {
    Set<UserPrincipal> trusted = new HashSet<>();
    for (AclFileAttributeView view : profile) {
      trusted.add(view.getOwner());
      for (AclEntry entry : view.getAcl()) {
        if (applies(entry) && !Collections.disjoint(entry.permissions(), CONTROL)) {
          trusted.add(entry.principal());
        }
      }
    }
    return trusted;
  }

  // Whether the directory of the first view, below the others, is private to trusted: it is owned
  // by a trusted principal, and its ACL allows nothing to anyone else, itself or what is created in
  // it; and no one else owns, or may delete, rename or change the ACL or owner of, a directory above
  // it. An empty ACL is not private: Java reads a Windows DACL that allows everyone everything as one.
  static boolean isPrivate(List<AclFileAttributeView> chain, Set<UserPrincipal> trusted) throws IOException {
    for (int i = 0; i < chain.size(); i++) {
      AclFileAttributeView view = chain.get(i);
      List<AclEntry> acl = view.getAcl();
      if (!trusted.contains(view.getOwner()) || acl.isEmpty()) {
        return false;
      }
      for (AclEntry entry : acl) {
        boolean restricted = i == 0 || applies(entry) && !Collections.disjoint(entry.permissions(), REPLACE);
        if (entry.type() == AclEntryType.ALLOW && restricted && !trusted.contains(entry.principal())) {
          return false;
        }
      }
    }
    return true;
  }

  // Whether entry allows something on its directory itself, rather than only on what is created in
  // it.
  private static boolean applies(AclEntry entry) {
    return entry.type() == AclEntryType.ALLOW && !entry.flags().contains(AclEntryFlag.INHERIT_ONLY);
  }
}
