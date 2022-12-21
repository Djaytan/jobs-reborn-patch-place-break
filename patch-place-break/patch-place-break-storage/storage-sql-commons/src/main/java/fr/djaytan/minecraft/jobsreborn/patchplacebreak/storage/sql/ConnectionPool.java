/*
 * MIT License
 *
 * Copyright (c) 2022 Loïc DUBOIS-TERMOZ (alias Djaytan)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.NonNull;

public abstract class ConnectionPool {

  private final Logger logger;
  private HikariDataSource hikariDataSource;

  protected ConnectionPool(Logger logger) {
    this.logger = logger;
  }

  public void connect() {
    String jdbcUrl = getJdbcUrl();

    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(jdbcUrl);
    hikariConfig.setMaximumPoolSize(10);
    hikariConfig.setMinimumIdle(1);
    hikariDataSource = new HikariDataSource(hikariConfig);

    logger.atInfo().log("Connected to the database '{}'.", jdbcUrl);
  }

  public void disconnect() {
    if (hikariDataSource == null) {
      logger.atWarn().log("Database disconnection impossible: no existing connection.");
      return;
    }
    hikariDataSource.close();
    logger.atInfo().log("Disconnected from the database '{}'.", hikariDataSource.getJdbcUrl());
  }

  public @NonNull Connection getConnection() {
    if (hikariDataSource == null) {
      throw SqlStorageException.connectionPoolNotSetup();
    }

    try {
      return hikariDataSource.getConnection();
    } catch (SQLException e) {
      throw SqlStorageException.databaseConnectionEstablishment(e);
    }
  }

  protected abstract @NonNull String getJdbcUrl();
}
