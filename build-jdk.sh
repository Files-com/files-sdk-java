# Sourced by bash scripts that build this SDK, such as test.sh and build.sh (`. ./build-jdk.sh`).
# Points JAVA_HOME at the JDK that builds and checks the SDK, downloading and verifying it on first
# use. Applications that use the SDK only need Java 8 or later.
#
# FILES_SDK_BUILD_JAVA_HOME: use this JDK 25 installation instead of downloading one.
# FILES_SDK_JDK_CACHE: where downloaded JDKs are kept (default ~/.cache/files-sdk-java).
# FILES_SDK_BUILD_TRUST_STORE: a Java trust store for Maven to use instead of the JDK's own, such as
#   the one a Linux system keeps for the JDKs it installs (see the end of this file).

build_jdk_release="jdk-25.0.4.1+1"

build_jdk_archive() {
  case "$(uname -s)-$(uname -m)" in
    Linux-x86_64)
      echo "OpenJDK25U-jdk_x64_linux_hotspot_25.0.4.1_1.tar.gz dbb698396d478e7fa2b1e50f4103324b2a99b90569ee27c33f2261f9215cf41e" ;;
    Linux-aarch64 | Linux-arm64)
      echo "OpenJDK25U-jdk_aarch64_linux_hotspot_25.0.4.1_1.tar.gz 69df11a02cfa3ef7d7ca645e03edce6778ec090e100f6ae2b42097865730ac52" ;;
    Darwin-arm64)
      echo "OpenJDK25U-jdk_aarch64_mac_hotspot_25.0.4.1_1.tar.gz 61979887f7506a24a57439ff99adb8b3a7fc89977d9cfe3b8984f58a981b7b9d" ;;
    Darwin-x86_64)
      echo "OpenJDK25U-jdk_x64_mac_hotspot_25.0.4.1_1.tar.gz e6229d9504f7922053ab31821b9e6bee8761daf7b026a3476d1a027563009880" ;;
    *)
      echo "build-jdk.sh: no JDK download for $(uname -s) $(uname -m); set FILES_SDK_BUILD_JAVA_HOME to a JDK 25" >&2
      return 1 ;;
  esac
}

build_jdk_sha256() {
  if command -v sha256sum > /dev/null; then
    sha256sum "$1" | cut -d ' ' -f 1
  else
    shasum -a 256 "$1" | cut -d ' ' -f 1
  fi
}

build_jdk_install() {
  local archive name sha256 cache jdk_dir download
  archive=$(build_jdk_archive) || return 1
  name=${archive% *}
  sha256=${archive#* }
  cache=${FILES_SDK_JDK_CACHE:-${XDG_CACHE_HOME:-$HOME/.cache}/files-sdk-java}
  jdk_dir="$cache/${name%.tar.gz}"

  if [ ! -d "$jdk_dir" ]; then
    mkdir -p "$cache"
    download=$(mktemp -d "$cache/download.XXXXXX")
    echo "Downloading $build_jdk_release ($name)" >&2
    if ! curl --fail --silent --show-error --location --retry 3 --output "$download/$name" \
        "https://github.com/adoptium/temurin25-binaries/releases/download/${build_jdk_release/+/%2B}/$name"; then
      rm -rf "$download"
      return 1
    fi
    if [ "$(build_jdk_sha256 "$download/$name")" != "$sha256" ]; then
      echo "build-jdk.sh: $name does not match its SHA-256 $sha256" >&2
      rm -rf "$download"
      return 1
    fi
    mkdir "$download/jdk"
    tar -xzf "$download/$name" -C "$download/jdk" --strip-components 1
    [ -d "$jdk_dir" ] || mv "$download/jdk" "$jdk_dir"
    rm -rf "$download"
  fi

  if [ -d "$jdk_dir/Contents/Home" ]; then
    echo "$jdk_dir/Contents/Home"
  else
    echo "$jdk_dir"
  fi
}

if [ -n "${FILES_SDK_BUILD_JAVA_HOME:-}" ]; then
  JAVA_HOME=$FILES_SDK_BUILD_JAVA_HOME
else
  JAVA_HOME=$(build_jdk_install) || return 1
fi
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

# The JDK selected above trusts only the certificate authorities it ships with. Where the build must
# trust others, such as the one that issued a private Maven mirror's certificate,
# FILES_SDK_BUILD_TRUST_STORE names a Java trust store that holds them, and Maven uses it instead.
# Certificates and host names are still verified against it. A trust store that MAVEN_OPTS,
# JAVA_TOOL_OPTIONS or JDK_JAVA_OPTIONS already names is kept.
if [ -n "${FILES_SDK_BUILD_TRUST_STORE:-}" ]; then
  if [ ! -f "$FILES_SDK_BUILD_TRUST_STORE" ]; then
    echo "build-jdk.sh: FILES_SDK_BUILD_TRUST_STORE ($FILES_SDK_BUILD_TRUST_STORE) is not a file" >&2
    return 1
  fi
  case $FILES_SDK_BUILD_TRUST_STORE in
    *[[:space:]]*)
      # Maven splits MAVEN_OPTS at whitespace.
      echo "build-jdk.sh: FILES_SDK_BUILD_TRUST_STORE ($FILES_SDK_BUILD_TRUST_STORE) contains whitespace" >&2
      return 1
      ;;
  esac
  # Maven splits whitespace; Java environment options also permit whole-option quotes.
  case " ${MAVEN_OPTS:-} ${JAVA_TOOL_OPTIONS:-} ${JDK_JAVA_OPTIONS:-} " in
    *[[:space:]\"\']-Djavax.net.ssl.trustStore=*) ;;
    *)
      MAVEN_OPTS="${MAVEN_OPTS:+$MAVEN_OPTS }-Djavax.net.ssl.trustStore=$FILES_SDK_BUILD_TRUST_STORE"
      export MAVEN_OPTS
      ;;
  esac
fi
