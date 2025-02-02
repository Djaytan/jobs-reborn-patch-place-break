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

import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomDbmsServerHostname;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomDbmsServerPort;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomDbmsServerSslEnabled;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;

public final class DbmsServerHostPropertiesAssert
    extends AbstractAssert<DbmsServerHostPropertiesAssert, DbmsServerHostProperties> {

  private DbmsServerHostPropertiesAssert(
      @NotNull DbmsServerHostProperties dbmsServerHostProperties) {
    super(dbmsServerHostProperties, DbmsServerHostPropertiesAssert.class);
  }

  public static @NotNull DbmsServerHostPropertiesAssert assertThat(
      @NotNull DbmsServerHostProperties dbmsServerHostProperties) {
    return new DbmsServerHostPropertiesAssert(dbmsServerHostProperties);
  }

  public static @NotNull DbmsServerHostPropertiesAssert.Instantiation
      assertThatInstantiationWithHostname(@NotNull String hostname) {
    return Instantiation.withHostname(hostname);
  }

  public static @NotNull DbmsServerHostPropertiesAssert.Instantiation
      assertThatInstantiationWithPort(int port) {
    return Instantiation.withPort(port);
  }

  @CanIgnoreReturnValue
  public @NotNull DbmsServerHostPropertiesAssert hasHostname(@NotNull String hostname) {
    Assertions.assertThat(actual.hostname()).isEqualTo(hostname);
    return this;
  }

  @CanIgnoreReturnValue
  public @NotNull DbmsServerHostPropertiesAssert hasPort(int port) {
    Assertions.assertThat(actual.port()).isEqualTo(port);
    return this;
  }

  @CanIgnoreReturnValue
  public @NotNull DbmsServerHostPropertiesAssert hasSslEnabled(boolean isSslEnabled) {
    Assertions.assertThat(actual.isSslEnabled()).isEqualTo(isSslEnabled);
    return this;
  }

  public static final class Instantiation {

    private final ThrowingCallable instantiation;

    private Instantiation(@NotNull String hostname, int port) {
      this.instantiation =
          () -> new DbmsServerHostProperties(hostname, port, randomDbmsServerSslEnabled());
    }

    private static @NotNull Instantiation withHostname(@NotNull String hostname) {
      return new Instantiation(hostname, randomDbmsServerPort());
    }

    private static @NotNull Instantiation withPort(int port) {
      return new Instantiation(randomDbmsServerHostname(), port);
    }

    public void doesNotThrowAnyException() {
      assertThatCode(instantiation).doesNotThrowAnyException();
    }

    public void doesThrowExceptionAboutIllegalHostnameLength() {
      assertThatThrownBy(instantiation)
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("The DBMS server hostname cannot exceed 255 characters");
    }

    public void doesThrowExceptionAboutIllegalBlankHostname() {
      assertThatThrownBy(instantiation)
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("The DBMS server hostname cannot be blank");
    }

    public void doesThrowExceptionAboutIllegalPort() {
      assertThatThrownBy(instantiation)
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("The DBMS server port must be between 1 and 65535");
    }
  }
}
