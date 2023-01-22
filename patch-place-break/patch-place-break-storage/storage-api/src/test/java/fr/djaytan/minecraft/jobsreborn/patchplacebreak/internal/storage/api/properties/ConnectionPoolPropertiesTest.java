package fr.djaytan.minecraft.jobsreborn.patchplacebreak.internal.storage.api.properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;

import nl.jqno.equalsverifier.EqualsVerifier;

class ConnectionPoolPropertiesTest {

  @Test
  @DisplayName("Constructor - Successful nominal case")
  void shouldSuccessWhenCreatingWithNominalValues() {
    // Given
    long connectionTimeout = 158652381;
    int poolSize = 12;

    // When
    ConnectionPoolProperties connectionPoolProperties =
        ConnectionPoolProperties.of(connectionTimeout, poolSize);

    // Then
    assertAll("Verification of returned values from getters",
        () -> assertEquals(connectionTimeout, connectionPoolProperties.getConnectionTimeout()),
        () -> assertEquals(poolSize, connectionPoolProperties.getPoolSize()));
  }

  @Test
  @DisplayName("equals() & hashCode() - Verifications")
  void equalsAndHashcodeContractVerification() {
    EqualsVerifier.forClass(ConnectionPoolProperties.class).verify();
  }

  @Test
  @DisplayName("toString() - Verifications")
  void toStringContractVerification() {
    ToStringVerifier.forClass(ConnectionPoolProperties.class).withClassName(NameStyle.SIMPLE_NAME)
        .verify();
  }
}