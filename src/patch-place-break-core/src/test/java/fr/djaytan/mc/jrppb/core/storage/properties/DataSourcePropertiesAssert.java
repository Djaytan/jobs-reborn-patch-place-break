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

import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.randomConnectionPoolProperties;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.randomDataSourceType;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.randomDbmsServerProperties;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jetbrains.annotations.NotNull;

public final class DataSourcePropertiesAssert
    extends AbstractAssert<DataSourcePropertiesAssert, DataSourceProperties> {

  private DataSourcePropertiesAssert(@NotNull DataSourceProperties dataSourceProperties) {
    super(dataSourceProperties, DataSourcePropertiesAssert.class);
  }

  public static @NotNull DataSourcePropertiesAssert assertThat(
      @NotNull DataSourceProperties dataSourceProperties) {
    return new DataSourcePropertiesAssert(dataSourceProperties);
  }

  public static @NotNull DataSourcePropertiesAssert.Instantiation
      assertThatInstantiationWithTableName(@NotNull String tableName) {
    return new Instantiation(tableName);
  }

  public @NotNull DataSourcePropertiesAssert hasType(DataSourceType type) {
    Assertions.assertThat(actual.type()).isEqualTo(type);
    return this;
  }

  public @NotNull DataSourcePropertiesAssert hasTableName(String tableName) {
    Assertions.assertThat(actual.tableName()).isEqualTo(tableName);
    return this;
  }

  public @NotNull DataSourcePropertiesAssert hasDbmsServer(DbmsServerProperties dbmsServer) {
    Assertions.assertThat(actual.dbmsServer()).isEqualTo(dbmsServer);
    return this;
  }

  public @NotNull DataSourcePropertiesAssert hasConnectionPool(
      ConnectionPoolProperties connectionPool) {
    Assertions.assertThat(actual.connectionPool()).isEqualTo(connectionPool);
    return this;
  }

  public static final class Instantiation {

    private final ThrowingCallable instantiation;

    private Instantiation(@NotNull String tableName) {
      this.instantiation =
          () ->
              new DataSourceProperties(
                  randomDataSourceType(),
                  tableName,
                  randomDbmsServerProperties(),
                  randomConnectionPoolProperties());
    }

    public void doesThrowExceptionAboutIllegalTableName() {
      assertThatThrownBy(instantiation)
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("The data source table name must not be blank");
    }
  }
}
