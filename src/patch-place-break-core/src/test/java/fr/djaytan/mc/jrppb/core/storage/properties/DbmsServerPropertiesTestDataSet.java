package fr.djaytan.mc.jrppb.core.storage.properties;

import static org.instancio.Select.field;

import fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerProperties.Credentials;
import fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerProperties.Host;
import org.instancio.Instancio;
import org.instancio.Model;
import org.jetbrains.annotations.NotNull;

public final class DbmsServerPropertiesTestDataSet {

  static final Host NOMINAL_HOST = new Host("db.amazing.com", 4123, true);
  static final Credentials NOMINAL_CREDENTIALS = new Credentials("admin", "my-admin-pwd");
  static final String NOMINAL_DATABASE_NAME = "minecraft";
  public static final DbmsServerProperties NOMINAL_DBMS_SERVER_PROPERTIES =
      new DbmsServerProperties(NOMINAL_HOST, NOMINAL_CREDENTIALS, NOMINAL_DATABASE_NAME);

  public static final Model<Host> HOST_MODEL =
      Instancio.of(Host.class)
          .supply(field(Host::hostname), () -> generateHostname())
          .supply(field(Host::port), () -> generatePort())
          .supply(field(Host::isSslEnabled), () -> generateSslEnabled())
          .toModel();

  public static final Model<Credentials> CREDENTIALS_MODEL =
      Instancio.of(Credentials.class)
          .supply(field(Credentials::username), () -> generateUsername())
          .supply(field(Credentials::password), () -> generatePassword())
          .toModel();

  public static final Model<DbmsServerProperties> DBMS_SERVER_PROPERTIES_MODEL =
      Instancio.of(DbmsServerProperties.class)
          .setModel(field(DbmsServerProperties::host), HOST_MODEL)
          .setModel(field(DbmsServerProperties::credentials), CREDENTIALS_MODEL)
          .supply(field(DbmsServerProperties::databaseName), () -> generateDatabaseName())
          .toModel();

  static @NotNull String generateHostname() {
    return Instancio.gen().string().alphaNumeric().mixedCase().length(1, 255).get();
  }

  static @NotNull String generateHostnameOfExactLength(int length) {
    return Instancio.gen().string().alphaNumeric().mixedCase().length(length).get();
  }

  static int generatePort() {
    return Instancio.gen().ints().range(1, 65535).get();
  }

  static boolean generateSslEnabled() {
    return Instancio.gen().booleans().get();
  }

  static @NotNull String generateUsername() {
    return Instancio.gen().string().alphaNumeric().mixedCase().length(1, 32).get();
  }

  static @NotNull String generatePassword() {
    return Instancio.gen().string().alphaNumeric().mixedCase().length(1, 32).get();
  }

  static @NotNull String generateDatabaseName() {
    return Instancio.gen().string().alphaNumeric().mixedCase().length(1, 128).get();
  }
}
