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
 * Represents the properties related to the DBMS server.
 *
 * <p>These properties are applicable only for DBMS servers like MySQL/MariaDB (i.e. does not apply
 * for SQLite, which is just a file in the local file system).
 *
 * @param host The DBMS server properties related to the host.
 * @param credentials The DBMS server properties related to the credentials.
 * @param databaseName The targeted database name when establishing connection with the DBMS server.
 */
public record DbmsServerProperties(
    @NotNull DbmsServerHostProperties host,
    @NotNull DbmsServerCredentialsProperties credentials,
    @NotNull String databaseName) {

  public static final DbmsServerProperties DEFAULT =
      new DbmsServerProperties(
          DbmsServerHostProperties.DEFAULT, DbmsServerCredentialsProperties.DEFAULT, "database");

  public DbmsServerProperties {
    Validate.notBlank(databaseName, "The DBMS server database name cannot be blank");
  }
}
