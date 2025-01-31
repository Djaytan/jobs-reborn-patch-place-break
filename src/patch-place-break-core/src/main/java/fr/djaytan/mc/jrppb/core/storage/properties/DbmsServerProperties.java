package fr.djaytan.mc.jrppb.core.storage.properties;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the properties related to the DBMS server.
 *
 * <p>These properties are applicable only for DBMS servers like MySQL/MariaDB (i.e. does not apply
 * for SQLite, which is just a file in the local file system).
 *
 * @param host The DBMS server properties related to the host.
 * @param credentials The DBMS server properties related to the credentials.
 * @param databaseName The targeted database name when establishing connection with the DBMS server.
 */
public record DbmsServerProperties(
    @NotNull Host host, @NotNull Credentials credentials, @NotNull String databaseName) {

  public DbmsServerProperties {
    Validate.notBlank(databaseName, "The DBMS server database name cannot be blank");
  }

  public DbmsServerProperties() {
    this(new Host(), new Credentials(), "database");
  }

  /**
   * Represents the DBMS server properties related to the host.
   *
   * <p>A hostname cannot exceed 255 characters as per the DNS standard specification.
   *
   * <p><i>Note: we explicitly allow most invalid hostnames since otherwise it will require too much
   * work for too limited earnings. In fact, specifying an invalid hostname will be detected by
   * underlying systems (e.g. JDBC). So, we only focus on easily detectable invalid addresses.</i>
   *
   * <p>A port cannot exceed 65535, which is the maximum value allowed by the Transport Control
   * Protocol (TCP) and User Datagram Protocol (UDP) standards. The value "0" is excluded since it's
   * a reserved one and must not be used.
   *
   * @param hostname The hostname of the targeted DBMS server (an IP address (IPv4/IPv6) or a domain
   *     name).
   * @param port The port on which the targeted DBMS server is exposed.
   * @param isSslEnabled <code>true</code> if the SSL communication is enabled, <code>false</code>
   *     otherwise.
   */
  public record Host(@NotNull String hostname, int port, boolean isSslEnabled) {

    public Host {
      Validate.notBlank(hostname, "The DBMS server hostname cannot be blank");
      Validate.isTrue(
          hostname.length() <= 255, "The DBMS server hostname cannot exceed 255 characters");
      Validate.inclusiveBetween(1, 65535, port, "The DBMS server port must be between 1 and 65535");
    }

    public Host() {
      this("localhost", 3306, true);
    }
  }

  /**
   * Represents the DBMS server properties related to credentials.
   *
   * @param username The username to be used for authentication with the DBMS server.
   * @param password The password to be used for authentication with the DBMS server.
   */
  public record Credentials(@NotNull String username, @NotNull String password) {

    public Credentials {
      Validate.notBlank(username, "The DBMS server username cannot be blank");
      Validate.notBlank(password, "The DBMS server password cannot be blank");
    }

    public Credentials() {
      // TODO: do we really want to set default values for username and password?
      this("username", "password");
    }
  }
}
