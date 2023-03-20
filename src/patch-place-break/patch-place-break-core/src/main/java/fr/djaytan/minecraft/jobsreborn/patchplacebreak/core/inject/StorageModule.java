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
package fr.djaytan.minecraft.jobsreborn.patchplacebreak.core.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.core.inject.provider.DataSourceInitializerProvider;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.core.inject.provider.JdbcUrlProvider;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.DataSourceManager;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.TagRepository;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.properties.DataSourceProperties;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.sql.JdbcUrl;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.sql.SqlDataSourceManager;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.sql.access.SqlTagRepository;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.sql.init.DataSourceInitializer;
import java.nio.file.Path;
import javax.inject.Named;
import javax.inject.Singleton;
import org.flywaydb.core.api.Location;
import org.jetbrains.annotations.NotNull;

final class StorageModule extends AbstractModule {

  private static final String DB_MIGRATION_DESCRIPTOR_FORMAT = "/db/migration/%s";
  private static final String SQLITE_DATABASE_FILE_NAME = "sqlite-data.db";

  @Override
  protected void configure() {
    bind(DataSourceInitializer.class).toProvider(DataSourceInitializerProvider.class);
    bind(DataSourceManager.class).to(SqlDataSourceManager.class);
    bind(JdbcUrl.class).toProvider(JdbcUrlProvider.class);
    bind(TagRepository.class).to(SqlTagRepository.class);
  }

  @Provides
  @Singleton
  static @NotNull Location location(@NotNull DataSourceProperties dataSourceProperties) {
    String descriptor =
        String.format(
            DB_MIGRATION_DESCRIPTOR_FORMAT, dataSourceProperties.getType().name().toLowerCase());
    return new Location(descriptor);
  }

  @Provides
  @Named("sqliteDatabaseFile")
  @Singleton
  static @NotNull Path sqliteDatabaseFile(@NotNull @Named("dataFolder") Path dataFolder) {
    return dataFolder.resolve(SQLITE_DATABASE_FILE_NAME);
  }
}
