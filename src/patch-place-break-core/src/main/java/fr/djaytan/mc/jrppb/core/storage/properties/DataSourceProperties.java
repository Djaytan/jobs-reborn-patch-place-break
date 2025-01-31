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

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the properties related to the data source to be used for storage.
 *
 * @param type The data source type to be used for storage.
 * @param tableName The table name to be used for storage.
 * @param dbmsServer The properties related to the DBMS server (not applicable for all data source
 *     types like {@link Type#SQLITE}).
 * @param connectionPool The properties related to the connection pool.
 */
// TODO: DbmsServerProperties shall be omited for SQLite data source type.
public record DataSourceProperties(
    @NotNull Type type,
    @NotNull String tableName,
    @NotNull DbmsServerProperties dbmsServer,
    @NotNull ConnectionPoolProperties connectionPool) {

  public DataSourceProperties {
    Validate.notBlank(tableName, "The SQL table name must not be blank");
  }

  public DataSourceProperties() {
    this(
        Type.SQLITE,
        "patch_place_break_tag",
        new DbmsServerProperties(),
        new ConnectionPoolProperties());
  }

  /**
   * Represents the data source type to be used as a solution for storing and retrieving {@link
   * fr.djaytan.mc.jrppb.api.entities.Tag}s.
   */
  public enum Type {
    /** The MySQL data source type which is compatible with MariaDB as well. */
    MYSQL,
    /** The SQLite data source type. */
    SQLITE
  }
}
