/*
 * The MIT License
 * Copyright © 2022 Loïc DUBOIS-TERMOZ (alias Djaytan)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.djaytan.mc.jrppb.core.storage.properties;

import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerCredentialsPropertiesAssert.assertThat;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerCredentialsPropertiesAssert.assertThatInstantiationWithUsername;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerCredentialsPropertiesTestDataSet.NOMINAL_DBMS_SERVER_PASSWORD;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerCredentialsPropertiesTestDataSet.NOMINAL_DBMS_SERVER_USERNAME;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerCredentialsPropertiesTestDataSet.randomDbmsServerPassword;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerCredentialsPropertiesTestDataSet.randomDbmsServerUsername;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

final class DbmsServerCredentialsPropertiesTest {

  @Nested
  class WhenInstantiating {

    @Nested
    class SuccessCase {

      @Test
      void withNominalValues() {
        assertThat(
                new DbmsServerCredentialsProperties(
                    NOMINAL_DBMS_SERVER_USERNAME, NOMINAL_DBMS_SERVER_PASSWORD))
            .hasUsername(NOMINAL_DBMS_SERVER_USERNAME)
            .hasPassword(NOMINAL_DBMS_SERVER_PASSWORD);
      }

      @RepeatedTest(100)
      void withRandomlyGeneratedValues() {
        // Assemble
        String username = randomDbmsServerUsername();
        String password = randomDbmsServerPassword();

        // Act
        var dbmsServerCredentialsProperties =
            new DbmsServerCredentialsProperties(username, password);

        // Assert
        assertThat(dbmsServerCredentialsProperties).hasUsername(username).hasPassword(password);
      }
    }

    @Nested
    class FailureCase {

      @Test
      void withEmptyUsername() {
        assertThatInstantiationWithUsername("").doesThrowExceptionAboutIllegalUsername();
      }

      @Test
      void withBlankUsername() {
        assertThatInstantiationWithUsername(" ").doesThrowExceptionAboutIllegalUsername();
      }
    }
  }
}
