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
package fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.sql.impl.sqlite;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SqliteJdbcUrlTest {

  private final FileSystem imfs = Jimfs.newFileSystem(Configuration.unix());

  @AfterEach
  void afterEach() throws IOException {
    imfs.close();
  }

  @Test
  @DisplayName("When getting JDBC URL from dummy path")
  void whenGettingJdbcUrl_fromDummyPath() {
    // Given
    Path dummySqliteDatabasePath = imfs.getPath("/dummy/sqlite-data.db");
    SqliteJdbcUrl sqliteJdbcUrl = new SqliteJdbcUrl(dummySqliteDatabasePath);

    // When
    String actualSqliteJdbcUrl = sqliteJdbcUrl.get();

    // Then
    String expectedSqliteJdbcUrl = "jdbc:sqlite:/dummy/sqlite-data.db";

    assertThat(actualSqliteJdbcUrl).isEqualTo(expectedSqliteJdbcUrl);
  }
}
