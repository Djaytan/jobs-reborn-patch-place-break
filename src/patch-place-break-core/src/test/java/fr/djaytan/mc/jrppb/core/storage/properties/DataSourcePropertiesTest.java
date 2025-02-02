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

import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.NOMINAL_CONNECTION_POOL_PROPERTIES;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.randomConnectionPoolProperties;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesAssert.assertThat;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesAssert.assertThatInstantiationWithTableName;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.NOMINAL_DATA_SOURCE_TABLE_NAME;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.NOMINAL_DATA_SOURCE_TYPE;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.randomDataSourceTableName;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.randomDataSourceType;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.NOMINAL_DBMS_SERVER_PROPERTIES;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.randomDbmsServerProperties;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

final class DataSourcePropertiesTest {

  @Nested
  class WhenInstantiating {

    @Nested
    class SuccessCase {

      @Test
      void withNominalValues() {
        assertThat(
                new DataSourceProperties(
                    NOMINAL_DATA_SOURCE_TYPE,
                    NOMINAL_DATA_SOURCE_TABLE_NAME,
                    NOMINAL_DBMS_SERVER_PROPERTIES,
                    NOMINAL_CONNECTION_POOL_PROPERTIES))
            .hasType(NOMINAL_DATA_SOURCE_TYPE)
            .hasTableName(NOMINAL_DATA_SOURCE_TABLE_NAME)
            .hasDbmsServer(NOMINAL_DBMS_SERVER_PROPERTIES)
            .hasConnectionPool(NOMINAL_CONNECTION_POOL_PROPERTIES);
      }

      @RepeatedTest(100)
      void withRandomlyGeneratedValues() {
        // Assemble
        var type = randomDataSourceType();
        String tableName = randomDataSourceTableName();
        var dbmsServerProperties = randomDbmsServerProperties();
        var connectionPoolProperties = randomConnectionPoolProperties();

        // Act
        var dataSourceProperties =
            new DataSourceProperties(
                type, tableName, dbmsServerProperties, connectionPoolProperties);

        // Assert
        assertThat(dataSourceProperties)
            .hasType(type)
            .hasTableName(tableName)
            .hasDbmsServer(dbmsServerProperties)
            .hasConnectionPool(connectionPoolProperties);
      }
    }

    @Nested
    class FailureCase {

      @Test
      void withEmptyTableName() {
        assertThatInstantiationWithTableName("").doesThrowExceptionAboutIllegalTableName();
      }

      @Test
      void withBlankTableName() {
        assertThatInstantiationWithTableName(" ").doesThrowExceptionAboutIllegalTableName();
      }
    }
  }
}
