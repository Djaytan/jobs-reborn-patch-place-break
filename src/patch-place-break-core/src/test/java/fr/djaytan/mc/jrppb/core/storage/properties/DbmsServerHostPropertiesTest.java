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

import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesAssert.assertThat;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesAssert.assertThatInstantiationWithHostname;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesAssert.assertThatInstantiationWithPort;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.NOMINAL_DBMS_SERVER_HOSTNAME;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.NOMINAL_DBMS_SERVER_IS_SSL_ENABLED;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.NOMINAL_DBMS_SERVER_PORT;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomDbmsServerHostname;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomDbmsServerHostnameOfLength;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomDbmsServerPort;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomDbmsServerSslEnabled;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomInvalidDbmsServerPort;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomTooLongDbmsServerHostname;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

final class DbmsServerHostPropertiesTest {

  @Nested
  class WhenInstantiating {

    @Nested
    class SuccessCase {

      @Test
      void withNominalValues() {
        assertThat(
                new DbmsServerHostProperties(
                    NOMINAL_DBMS_SERVER_HOSTNAME,
                    NOMINAL_DBMS_SERVER_PORT,
                    NOMINAL_DBMS_SERVER_IS_SSL_ENABLED))
            .hasHostname(NOMINAL_DBMS_SERVER_HOSTNAME)
            .hasPort(NOMINAL_DBMS_SERVER_PORT)
            .hasSslEnabled(NOMINAL_DBMS_SERVER_IS_SSL_ENABLED);
      }

      @RepeatedTest(100)
      void withRandomlyGeneratedValues() {
        // Assemble
        String hostname = randomDbmsServerHostname();
        int port = randomDbmsServerPort();
        boolean isSslEnabled = randomDbmsServerSslEnabled();

        // Act
        var dbmsServerHostProperties = new DbmsServerHostProperties(hostname, port, isSslEnabled);

        // Assert
        assertThat(dbmsServerHostProperties)
            .hasHostname(hostname)
            .hasPort(port)
            .hasSslEnabled(isSslEnabled);
      }

      @Test
      void withHighestAllowedHostnameLength() {
        assertThatInstantiationWithHostname(randomDbmsServerHostnameOfLength(255))
            .doesNotThrowAnyException();
      }

      @Test
      void withLowestAllowedHostnameLength() {
        assertThatInstantiationWithHostname(randomDbmsServerHostnameOfLength(1))
            .doesNotThrowAnyException();
      }

      @Test
      void withHighestAllowedPort() {
        assertThatInstantiationWithPort(65535).doesNotThrowAnyException();
      }

      @Test
      void withLowestAllowedPort() {
        assertThatInstantiationWithPort(1).doesNotThrowAnyException();
      }
    }

    @Nested
    class FailureCase {

      @Nested
      class Hostname {

        @Test
        void withLengthJustAboveLimit() {
          assertThatInstantiationWithHostname(randomDbmsServerHostnameOfLength(256))
              .doesThrowExceptionAboutIllegalHostnameLength();
        }

        @Test
        void withEmptyValue() {
          assertThatInstantiationWithHostname("").doesThrowExceptionAboutIllegalBlankHostname();
        }

        @Test
        void withBlankValue() {
          assertThatInstantiationWithHostname(" ").doesThrowExceptionAboutIllegalBlankHostname();
        }

        @RepeatedTest(100)
        void withTooLongValue() {
          assertThatInstantiationWithHostname(randomTooLongDbmsServerHostname())
              .doesThrowExceptionAboutIllegalHostnameLength();
        }
      }

      @Nested
      class Port {

        @Test
        void withPortZero() {
          assertThatInstantiationWithPort(0).doesThrowExceptionAboutIllegalPort();
        }

        @Test
        void withNegativeValue() {
          assertThatInstantiationWithPort(-1).doesThrowExceptionAboutIllegalPort();
        }

        @Test
        void withValueJustAboveLimit() {
          assertThatInstantiationWithPort(65536).doesThrowExceptionAboutIllegalPort();
        }

        @RepeatedTest(100)
        void withInvalidValue() {
          assertThatInstantiationWithPort(randomInvalidDbmsServerPort())
              .doesThrowExceptionAboutIllegalPort();
        }
      }
    }
  }
}
