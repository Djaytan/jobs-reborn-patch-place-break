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

import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerCredentialsPropertiesTestDataSet.randomDbmsServerCredentialsProperties;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerHostPropertiesTestDataSet.randomDbmsServerHostProperties;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;

public final class DbmsServerPropertiesAssert
    extends AbstractAssert<DbmsServerPropertiesAssert, DbmsServerProperties> {

  private DbmsServerPropertiesAssert(@NotNull DbmsServerProperties dbmsServerProperties) {
    super(dbmsServerProperties, DbmsServerPropertiesAssert.class);
  }

  public static @NotNull DbmsServerPropertiesAssert assertThat(
      @NotNull DbmsServerProperties dbmsServerProperties) {
    return new DbmsServerPropertiesAssert(dbmsServerProperties);
  }

  public static @NotNull DbmsServerPropertiesAssert.Instantiation
      assertThatInstantiationWithDatabaseName(@NotNull String databaseName) {
    return new DbmsServerPropertiesAssert.Instantiation(databaseName);
  }

  @CanIgnoreReturnValue
  public @NotNull DbmsServerPropertiesAssert hasHost(@NotNull DbmsServerHostProperties host) {
    Assertions.assertThat(actual.host()).isEqualTo(host);
    return this;
  }

  @CanIgnoreReturnValue
  public @NotNull DbmsServerPropertiesAssert hasCredentials(
      @NotNull DbmsServerCredentialsProperties credentials) {
    Assertions.assertThat(actual.credentials()).isEqualTo(credentials);
    return this;
  }

  @CanIgnoreReturnValue
  public @NotNull DbmsServerPropertiesAssert hasDatabaseName(@NotNull String databaseName) {
    Assertions.assertThat(actual.databaseName()).isEqualTo(databaseName);
    return this;
  }

  public static final class Instantiation {

    private final ThrowingCallable instantiation;

    private Instantiation(@NotNull String databaseName) {
      this.instantiation =
          () ->
              new DbmsServerProperties(
                  randomDbmsServerHostProperties(),
                  randomDbmsServerCredentialsProperties(),
                  databaseName);
    }

    public void doesThrowExceptionAboutIllegalDatabaseName() {
      assertThatThrownBy(instantiation)
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("The DBMS server database name cannot be blank");
    }
  }
}
