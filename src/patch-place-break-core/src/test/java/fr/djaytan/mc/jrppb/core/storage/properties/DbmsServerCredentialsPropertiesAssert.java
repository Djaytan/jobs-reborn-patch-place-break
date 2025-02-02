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

import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerCredentialsPropertiesTestDataSet.randomDbmsServerPassword;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;

public final class DbmsServerCredentialsPropertiesAssert
    extends AbstractAssert<DbmsServerCredentialsPropertiesAssert, DbmsServerCredentialsProperties> {

  private DbmsServerCredentialsPropertiesAssert(
      @NotNull DbmsServerCredentialsProperties dbmsServerCredentialsProperties) {
    super(dbmsServerCredentialsProperties, DbmsServerCredentialsPropertiesAssert.class);
  }

  public static @NotNull DbmsServerCredentialsPropertiesAssert assertThat(
      @NotNull DbmsServerCredentialsProperties dbmsServerCredentialsProperties) {
    return new DbmsServerCredentialsPropertiesAssert(dbmsServerCredentialsProperties);
  }

  public static @NotNull DbmsServerCredentialsPropertiesAssert.Instantiation
      assertThatInstantiationWithUsername(@NotNull String username) {
    return new Instantiation(username);
  }

  @CanIgnoreReturnValue
  public @NotNull DbmsServerCredentialsPropertiesAssert hasUsername(@NotNull String username) {
    Assertions.assertThat(actual.username()).isEqualTo(username);
    return this;
  }

  @CanIgnoreReturnValue
  public @NotNull DbmsServerCredentialsPropertiesAssert hasPassword(@NotNull String password) {
    Assertions.assertThat(actual.password()).isEqualTo(password);
    return this;
  }

  public static final class Instantiation {

    private final ThrowingCallable instantiation;

    private Instantiation(@NotNull String username) {
      this.instantiation =
          () -> new DbmsServerCredentialsProperties(username, randomDbmsServerPassword());
    }

    public void doesThrowExceptionAboutIllegalUsername() {
      assertThatThrownBy(instantiation)
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("The DBMS server username cannot be blank");
    }
  }
}
