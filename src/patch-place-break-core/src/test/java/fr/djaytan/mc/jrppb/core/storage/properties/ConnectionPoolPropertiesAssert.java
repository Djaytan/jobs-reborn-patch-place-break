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

import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.randomConnectionTimeout;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.randomPoolSize;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;

public final class ConnectionPoolPropertiesAssert
    extends AbstractAssert<ConnectionPoolPropertiesAssert, ConnectionPoolProperties> {

  private ConnectionPoolPropertiesAssert(
      @NotNull ConnectionPoolProperties connectionPoolProperties) {
    super(connectionPoolProperties, ConnectionPoolPropertiesAssert.class);
  }

  public static @NotNull ConnectionPoolPropertiesAssert assertThat(
      @NotNull ConnectionPoolProperties connectionPoolProperties) {
    return new ConnectionPoolPropertiesAssert(connectionPoolProperties);
  }

  public static @NotNull ConnectionPoolPropertiesAssert.Instantiation
      assertThatInstantiationWithConnectionTimeout(long connectionTimeout) {
    return Instantiation.withConnectionTimeout(connectionTimeout);
  }

  public static @NotNull ConnectionPoolPropertiesAssert.Instantiation
      assertThatInstantiationWithPoolSize(int poolSize) {
    return Instantiation.withPoolSize(poolSize);
  }

  @CanIgnoreReturnValue
  public @NotNull ConnectionPoolPropertiesAssert hasConnectionTimeout(long connectionTimeout) {
    Assertions.assertThat(actual.connectionTimeout()).isEqualTo(connectionTimeout);
    return this;
  }

  @CanIgnoreReturnValue
  public @NotNull ConnectionPoolPropertiesAssert hasPoolSize(int poolSize) {
    Assertions.assertThat(actual.poolSize()).isEqualTo(poolSize);
    return this;
  }

  public static final class Instantiation {

    private final ThrowingCallable instantiation;

    private Instantiation(long connectionTimeout, int poolSize) {
      this.instantiation = () -> new ConnectionPoolProperties(connectionTimeout, poolSize);
    }

    private static @NotNull Instantiation withConnectionTimeout(long connectionTimeout) {
      return new Instantiation(connectionTimeout, randomPoolSize());
    }

    private static @NotNull Instantiation withPoolSize(int poolSize) {
      return new Instantiation(randomConnectionTimeout(), poolSize);
    }

    public void doesNotThrowAnyException() {
      assertThatCode(instantiation).doesNotThrowAnyException();
    }

    public void doesThrowExceptionAboutIllegalConnectionTimeout() {
      assertThatThrownBy(instantiation)
          .isExactlyInstanceOf(IllegalArgumentException.class)
          .hasMessage("The connection timeout must be between 1 and 600000");
    }

    public void doesThrowExceptionAboutIllegalPoolSize() {
      assertThatThrownBy(instantiation)
          .isExactlyInstanceOf(IllegalArgumentException.class)
          .hasMessage("The pool size must be between 1 and 100");
    }
  }
}
