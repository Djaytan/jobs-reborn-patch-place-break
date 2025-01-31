package fr.djaytan.mc.jrppb.core.storage.properties;

import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.CREDENTIALS_MODEL;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.HOST_MODEL;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.NOMINAL_CREDENTIALS;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.NOMINAL_DATABASE_NAME;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.NOMINAL_HOST;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.generateDatabaseName;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.generateHostname;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.generateHostnameOfExactLength;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.generatePassword;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.generatePort;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.generateSslEnabled;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.generateUsername;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerProperties.Credentials;
import fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerProperties.Host;
import org.instancio.Instancio;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

final class DbmsServerPropertiesTest {

  @Test
  void whenInstantiating_withDefaultValues_shallSucceed() {
    var dbmsServerProperties = new DbmsServerProperties();

    assertThat(dbmsServerProperties.host()).isEqualTo(new Host());
    assertThat(dbmsServerProperties.credentials()).isEqualTo(new Credentials());
    assertThat(dbmsServerProperties.databaseName()).isEqualTo("database");
  }

  @Test
  void whenInstantiating_withNominalValues_shallSucceed() {
    var dbmsServerProperties =
        new DbmsServerProperties(NOMINAL_HOST, NOMINAL_CREDENTIALS, NOMINAL_DATABASE_NAME);

    assertThat(dbmsServerProperties.host()).isEqualTo(NOMINAL_HOST);
    assertThat(dbmsServerProperties.credentials()).isEqualTo(NOMINAL_CREDENTIALS);
    assertThat(dbmsServerProperties.databaseName()).isEqualTo(NOMINAL_DATABASE_NAME);
  }

  @RepeatedTest(100)
  void whenInstantiating_withRandomValidValues_shallSucceed() {
    // Assemble
    var host = Instancio.create(HOST_MODEL);
    var credentials = Instancio.create(CREDENTIALS_MODEL);
    String database = generateDatabaseName();

    // Act
    var dbmsServerProperties = new DbmsServerProperties(host, credentials, database);

    // Assert
    assertThat(dbmsServerProperties.host()).isEqualTo(host);
    assertThat(dbmsServerProperties.credentials()).isEqualTo(credentials);
    assertThat(dbmsServerProperties.databaseName()).isEqualTo(database);
  }

  @Test
  void whenInstantiating_withEmptyDatabase_shallFail() {
    assertThat_instantiationWithBlankDatabase_doesThrowException("");
  }

  @Test
  void whenInstantiating_withSingleSpaceBlankDatabase_shallFail() {
    assertThat_instantiationWithBlankDatabase_doesThrowException(" ");
  }

  @RepeatedTest(10)
  void whenInstantiating_withMultipleSpacesBlankDatabase_shallFail() {
    assertThat_instantiationWithBlankDatabase_doesThrowException(
        " ".repeat(Instancio.gen().ints().range(2, 128).get()));
  }

  private static void assertThat_instantiationWithBlankDatabase_doesThrowException(
      @NotNull String hostname) {
    var host = Instancio.create(HOST_MODEL);
    var credentials = Instancio.create(CREDENTIALS_MODEL);

    assertThatThrownBy(() -> new DbmsServerProperties(host, credentials, hostname))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessage("The DBMS server database name cannot be blank");
  }

  @Nested
  class HostTest {

    @Test
    void whenInstantiating_withDefaultValues_shallSucceed() {
      var host = new Host();

      assertThat(host.hostname()).isEqualTo("localhost");
      assertThat(host.port()).isEqualTo(3306);
      assertThat(host.isSslEnabled()).isTrue();
    }

    @RepeatedTest(100)
    void whenInstantiating_withNominalValues_shallSucceed() {
      // Assemble
      String hostname = generateHostname();
      int port = generatePort();
      boolean isSslEnabled = generateSslEnabled();

      // Act
      var host = new Host(hostname, port, isSslEnabled);

      // Assert
      assertThat(host.hostname()).isEqualTo(hostname);
      assertThat(host.port()).isEqualTo(port);
      assertThat(host.isSslEnabled()).isEqualTo(isSslEnabled);
    }

    @Nested
    class HostnameTest {

      @Nested
      class SuccessCase {

        @Test
        void whenInstantiating_withHighestAllowedHostnameLength_shallSucceed() {
          assertThat_instantiationWithValidHostname_doesNotThrowAnyException(
              generateHostnameOfExactLength(255));
        }

        @Test
        void whenInstantiating_withLowestAllowedHostnameLength_shallSucceed() {
          assertThat_instantiationWithValidHostname_doesNotThrowAnyException(
              generateHostnameOfExactLength(1));
        }

        private static void assertThat_instantiationWithValidHostname_doesNotThrowAnyException(
            @NotNull String hostname) {
          assertThat_instantiationWithValidValues_doesNotThrowAnyException(
              hostname, generatePort(), generateSslEnabled());
        }
      }

      @Nested
      class FailureCase {

        @Test
        void whenInstantiating_withHostnameLengthJustAboveLimit_shallFail() {
          assertThat_instantiationWithTooLongHostname_doesThrowException(
              generateHostnameOfExactLength(256));
        }

        @Test
        void whenInstantiating_withEmptyHostnameAndLengthJustBelowLimit_shallFail() {
          assertThat_instantiationWithBlankHostname_doesThrowException("");
        }

        @Test
        void whenInstantiating_withSingleSpaceBlankHostname_shallFail() {
          assertThat_instantiationWithBlankHostname_doesThrowException(" ");
        }

        @RepeatedTest(10)
        void whenInstantiating_withMultipleSpacesBlankHostname_shallFail() {
          assertThat_instantiationWithBlankHostname_doesThrowException(
              " ".repeat(Instancio.gen().ints().range(2, 255).get()));
        }

        @RepeatedTest(100)
        void whenInstantiating_withInvalidHostnameLength_shallFail() {
          String hostname =
              generateHostnameOfExactLength(Instancio.gen().ints().range(256, 10000).get());

          assertThat_instantiationWithTooLongHostname_doesThrowException(hostname);
        }

        private static void assertThat_instantiationWithTooLongHostname_doesThrowException(
            @NotNull String hostname) {
          assertThat_instantiationWithInvalidHostname_doesThrowException(
              hostname, "The DBMS server hostname cannot exceed 255 characters");
        }

        private static void assertThat_instantiationWithBlankHostname_doesThrowException(
            @NotNull String hostname) {
          assertThat_instantiationWithInvalidHostname_doesThrowException(
              hostname, "The DBMS server hostname cannot be blank");
        }

        private static void assertThat_instantiationWithInvalidHostname_doesThrowException(
            @NotNull String hostname, @NotNull String expectedExceptionMessage) {
          int port = generatePort();
          boolean isSslEnabled = generateSslEnabled();

          assertThatThrownBy(() -> new Host(hostname, port, isSslEnabled))
              .isExactlyInstanceOf(IllegalArgumentException.class)
              .hasMessage(expectedExceptionMessage);
        }
      }
    }

    @Nested
    class PortTest {

      @Nested
      class SuccessCase {

        @Test
        void whenInstantiating_withLowestAllowedPort_shallSucceed() {
          assertThat_instantiationWithValidPort_doesNotThrowAnyException(1);
        }

        @Test
        void whenInstantiating_withHighestAllowedPort_shallSucceed() {
          assertThat_instantiationWithValidPort_doesNotThrowAnyException(65535);
        }

        private static void assertThat_instantiationWithValidPort_doesNotThrowAnyException(
            int port) {
          assertThat_instantiationWithValidValues_doesNotThrowAnyException(
              generateHostname(), port, generateSslEnabled());
        }
      }

      @Nested
      class FailureCase {

        @Test
        void whenInstantiating_withPortZero_shallFail() {
          assertThat_instantiationWithInvalidPort_doesThrowException(0);
        }

        @Test
        void whenInstantiating_withNegativePort_shallFail() {
          assertThat_instantiationWithInvalidPort_doesThrowException(-1);
        }

        @Test
        void whenInstantiating_withPortJustAboveLimit_shallFail() {
          assertThat_instantiationWithInvalidPort_doesThrowException(65536);
        }

        @RepeatedTest(100)
        void whenInstantiating_withInvalidPort_shallFail() {
          int port =
              Instancio.gen()
                  .ints()
                  .range(Integer.MIN_VALUE, -2)
                  .range(65537, Integer.MAX_VALUE)
                  .get();

          assertThat_instantiationWithInvalidPort_doesThrowException(port);
        }

        private static void assertThat_instantiationWithInvalidPort_doesThrowException(int port) {
          String hostname = generateHostname();
          boolean isSslEnabled = generateSslEnabled();

          assertThatThrownBy(() -> new Host(hostname, port, isSslEnabled))
              .isExactlyInstanceOf(IllegalArgumentException.class)
              .hasMessage("The DBMS server port must be between 1 and 65535");
        }
      }
    }

    private static void assertThat_instantiationWithValidValues_doesNotThrowAnyException(
        @NotNull String hostname, int port, boolean isSslEnabled) {
      assertThatCode(() -> new Host(hostname, port, isSslEnabled)).doesNotThrowAnyException();
    }
  }

  @Nested
  class CredentialsTest {

    @Test
    void whenInstantiating_withDefaultValues_shallSucceed() {
      var credentials = new Credentials();

      assertThat(credentials.username()).isEqualTo("username");
      assertThat(credentials.password()).isEqualTo("password");
    }

    @RepeatedTest(100)
    void whenInstantiating_withNominalValues_shallSucceed() {
      // Assemble
      String username = generateUsername();
      String password = generatePassword();

      // Act
      var credentials = new Credentials(username, password);

      // Assert
      assertThat(credentials.username()).isEqualTo(username);
      assertThat(credentials.password()).isEqualTo(password);
    }

    @Nested
    class UsernameTest {

      @Test
      void whenInstantiating_withEmptyUsername_shallFail() {
        assertThat_instantiationWithBlankUsername_doesThrowException("");
      }

      @Test
      void whenInstantiating_withSingleSpaceBlankUsername_shallFail() {
        assertThat_instantiationWithBlankUsername_doesThrowException(" ");
      }

      @RepeatedTest(10)
      void whenInstantiating_withMultipleSpacesBlankUsername_shallFail() {
        assertThat_instantiationWithBlankUsername_doesThrowException(
            " ".repeat(Instancio.gen().ints().range(2, 32).get()));
      }

      private static void assertThat_instantiationWithBlankUsername_doesThrowException(
          @NotNull String username) {
        String password = generatePassword();

        assertThatThrownBy(() -> new Credentials(username, password))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("The DBMS server username cannot be blank");
      }
    }

    @Nested
    class PasswordTest {

      @Test
      void whenInstantiating_withEmptyPassword_shallFail() {
        assertThat_instantiationWithBlankPassword_doesThrowException("");
      }

      @Test
      void whenInstantiating_withSingleSpaceBlankPassword_shallFail() {
        assertThat_instantiationWithBlankPassword_doesThrowException(" ");
      }

      @RepeatedTest(10)
      void whenInstantiating_withMultipleSpacesBlankPassword_shallFail() {
        assertThat_instantiationWithBlankPassword_doesThrowException(
            " ".repeat(Instancio.gen().ints().range(2, 32).get()));
      }

      private static void assertThat_instantiationWithBlankPassword_doesThrowException(
          @NotNull String password) {
        String username = generateUsername();

        assertThatThrownBy(() -> new Credentials(username, password))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("The DBMS server password cannot be blank");
      }
    }
  }
}
