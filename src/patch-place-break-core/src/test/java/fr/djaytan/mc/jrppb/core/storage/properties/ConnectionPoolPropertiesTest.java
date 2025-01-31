package fr.djaytan.mc.jrppb.core.storage.properties;

import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.NOMINAL_CONNECTION_TIMEOUT;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.NOMINAL_POOL_SIZE;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.generateConnectionTimeout;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.generatePoolSize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.instancio.Instancio;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

final class ConnectionPoolPropertiesTest {

  @Test
  void whenInstantiating_withDefaultValues_shallSucceed() {
    var connectionPoolProperties = new ConnectionPoolProperties();

    assertThat(connectionPoolProperties.connectionTimeout()).isEqualTo(30000);
    assertThat(connectionPoolProperties.poolSize()).isEqualTo(10);
  }

  @Test
  void whenInstantiating_withNominalValues_shallSucceed() {
    var connectionPoolProperties =
        new ConnectionPoolProperties(NOMINAL_CONNECTION_TIMEOUT, NOMINAL_POOL_SIZE);

    assertThat(connectionPoolProperties.connectionTimeout()).isEqualTo(NOMINAL_CONNECTION_TIMEOUT);
    assertThat(connectionPoolProperties.poolSize()).isEqualTo(NOMINAL_POOL_SIZE);
  }

  @RepeatedTest(100)
  void whenInstantiating_withRandomValidValues_shallSucceed() {
    // Assemble
    long connectionTimeout = generateConnectionTimeout();
    int poolSize = generatePoolSize();

    // Act
    var connectionPoolProperties = new ConnectionPoolProperties(connectionTimeout, poolSize);

    // Assert
    assertThat(connectionPoolProperties.connectionTimeout()).isEqualTo(connectionTimeout);
    assertThat(connectionPoolProperties.poolSize()).isEqualTo(poolSize);
  }

  @Nested
  class ConnectionTimeoutTest {

    @Nested
    class SuccessCase {

      @Test
      void whenInstantiating_withHighestAllowedConnectionTimeout_shallSucceed() {
        assertThat_instantiationWithValidConnectionTimeout_doesNotThrowAnyException(600000L);
      }

      @Test
      void whenInstantiating_withLowestAllowedConnectionTimeout_shallSucceed() {
        assertThat_instantiationWithValidConnectionTimeout_doesNotThrowAnyException(1L);
      }

      private static void
          assertThat_instantiationWithValidConnectionTimeout_doesNotThrowAnyException(
              long connectionTimeout) {
        assertThat_instantiationWithValidValues_doesNotThrowAnyException(
            connectionTimeout, generatePoolSize());
      }
    }

    @Nested
    class FailureCase {

      @Test
      void whenInstantiating_withConnectionTimeoutJustAboveLimit_shallFail() {
        assertThat_instantiationWithInvalidConnectionTimeout_doesThrowException(600001L);
      }

      @Test
      void whenInstantiating_withConnectionTimeoutJustBelowLimit_shallFail() {
        assertThat_instantiationWithInvalidConnectionTimeout_doesThrowException(0L);
      }

      @Test
      void whenInstantiating_withNegativeConnectionTimeout_shallFail() {
        assertThat_instantiationWithInvalidConnectionTimeout_doesThrowException(-1L);
      }

      @RepeatedTest(100)
      void whenInstantiating_withInvalidConnectionTimeout_shallFail() {
        long invalidValue =
            Instancio.gen().longs().range(Long.MIN_VALUE, 0L).range(600001L, Long.MAX_VALUE).get();

        assertThat_instantiationWithInvalidConnectionTimeout_doesThrowException(invalidValue);
      }

      private static void assertThat_instantiationWithInvalidConnectionTimeout_doesThrowException(
          long connectionTimeout) {
        int poolSize = generatePoolSize();

        assertThatThrownBy(() -> new ConnectionPoolProperties(connectionTimeout, poolSize))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("The connection timeout must be between 1 and 600000");
      }
    }
  }

  @Nested
  class PoolSizeTest {

    @Nested
    class SuccessCase {

      @Test
      void whenInstantiating_withHighestAllowedPoolSize_shallSucceed() {
        assertThat_instantiationWithValidPoolSize_doesNotThrowException(100);
      }

      @Test
      void whenInstantiating_withLowestAllowedPoolSize_shallSucceed() {
        assertThat_instantiationWithValidPoolSize_doesNotThrowException(1);
      }

      private static void assertThat_instantiationWithValidPoolSize_doesNotThrowException(
          int poolSize) {
        assertThat_instantiationWithValidValues_doesNotThrowAnyException(
            generateConnectionTimeout(), poolSize);
      }
    }

    @Nested
    class FailureCase {

      @Test
      void whenInstantiating_withValueJustAboveLimit_shallFail() {
        assertThat_instantiationWithInvalidPoolSize_doesThrowException(101);
      }

      @Test
      void whenInstantiating_withValueJustBelowLimit_shallFail() {
        assertThat_instantiationWithInvalidPoolSize_doesThrowException(0);
      }

      @Test
      void whenInstantiating_withNegativeValue_shallFail() {
        assertThat_instantiationWithInvalidPoolSize_doesThrowException(-1);
      }

      @RepeatedTest(100)
      void whenInstantiating_withInvalidValue_shallFail() {
        int poolSize =
            Instancio.gen().ints().range(Integer.MIN_VALUE, 0).range(101, Integer.MAX_VALUE).get();

        assertThat_instantiationWithInvalidPoolSize_doesThrowException(poolSize);
      }

      private static void assertThat_instantiationWithInvalidPoolSize_doesThrowException(
          int poolSize) {
        long connectionTimeout = generateConnectionTimeout();

        assertThatThrownBy(() -> new ConnectionPoolProperties(connectionTimeout, poolSize))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("The pool size must be between 1 and 100");
      }
    }
  }

  private static void assertThat_instantiationWithValidValues_doesNotThrowAnyException(
      long connectionTimeout, int poolSize) {
    assertThatCode(() -> new ConnectionPoolProperties(connectionTimeout, poolSize))
        .doesNotThrowAnyException();
  }
}
