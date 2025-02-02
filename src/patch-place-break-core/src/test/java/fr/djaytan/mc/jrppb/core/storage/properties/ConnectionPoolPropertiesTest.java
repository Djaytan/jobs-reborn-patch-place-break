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

import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesAssert.assertThat;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesAssert.assertThatInstantiationWithConnectionTimeout;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesAssert.assertThatInstantiationWithPoolSize;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.NOMINAL_CONNECTION_TIMEOUT;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.NOMINAL_POOL_SIZE;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.randomConnectionTimeout;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.randomInvalidConnectionTimeout;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.randomInvalidPoolSize;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.randomPoolSize;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

final class ConnectionPoolPropertiesTest {

  @Nested
  class WhenInstantiating {

    @Nested
    class SuccessCase {

      @Test
      void withNominalValues() {
        assertThat(new ConnectionPoolProperties(NOMINAL_CONNECTION_TIMEOUT, NOMINAL_POOL_SIZE))
            .hasConnectionTimeout(NOMINAL_CONNECTION_TIMEOUT)
            .hasPoolSize(NOMINAL_POOL_SIZE);
      }

      @RepeatedTest(100)
      void withRandomlyGeneratedValues() {
        // Assemble
        long connectionTimeout = randomConnectionTimeout();
        int poolSize = randomPoolSize();

        // Act
        var connectionPoolProperties = new ConnectionPoolProperties(connectionTimeout, poolSize);

        // Assert
        assertThat(connectionPoolProperties)
            .hasConnectionTimeout(connectionTimeout)
            .hasPoolSize(poolSize);
      }

      @Test
      void withHighestAllowedConnectionTimeout() {
        assertThatInstantiationWithConnectionTimeout(600000).doesNotThrowAnyException();
      }

      @Test
      void withLowestAllowedConnectionTimeout() {
        assertThatInstantiationWithConnectionTimeout(1).doesNotThrowAnyException();
      }

      @Test
      void withHighestAllowedPoolSize() {
        assertThatInstantiationWithPoolSize(100).doesNotThrowAnyException();
      }

      @Test
      void withLowestAllowedPoolSize() {
        assertThatInstantiationWithPoolSize(1).doesNotThrowAnyException();
      }
    }

    @Nested
    class FailureCase {

      @Nested
      class ConnectionPool {

        @Test
        void withValueJustAboveLimit() {
          assertThatInstantiationWithConnectionTimeout(600001)
              .doesThrowExceptionAboutIllegalConnectionTimeout();
        }

        @Test
        void withValueJustBelowLimit() {
          assertThatInstantiationWithConnectionTimeout(0)
              .doesThrowExceptionAboutIllegalConnectionTimeout();
        }

        @Test
        void withNegativeValue() {
          assertThatInstantiationWithConnectionTimeout(-1)
              .doesThrowExceptionAboutIllegalConnectionTimeout();
        }

        @RepeatedTest(100)
        void withRandomlyGeneratedInvalidValue() {
          assertThatInstantiationWithConnectionTimeout(randomInvalidConnectionTimeout())
              .doesThrowExceptionAboutIllegalConnectionTimeout();
        }
      }

      @Nested
      class PoolSize {

        @Test
        void withValueJustAboveLimit() {
          assertThatInstantiationWithPoolSize(101).doesThrowExceptionAboutIllegalPoolSize();
        }

        @Test
        void withValueJustBelowLimit() {
          assertThatInstantiationWithPoolSize(0).doesThrowExceptionAboutIllegalPoolSize();
        }

        @Test
        void withNegativeValue() {
          assertThatInstantiationWithPoolSize(-1).doesThrowExceptionAboutIllegalPoolSize();
        }

        @RepeatedTest(100)
        void withRandomlyGeneratedInvalidValue() {
          assertThatInstantiationWithPoolSize(randomInvalidPoolSize())
              .doesThrowExceptionAboutIllegalPoolSize();
        }
      }
    }
  }
}
