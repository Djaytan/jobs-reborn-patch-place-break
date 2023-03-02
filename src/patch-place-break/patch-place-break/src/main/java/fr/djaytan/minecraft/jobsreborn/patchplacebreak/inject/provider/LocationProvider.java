/*-
 * #%L
 * JobsReborn-PatchPlaceBreak
 * %%
 * Copyright (C) 2022 - 2023 Loïc DUBOIS-TERMOZ (alias Djaytan)
 * %%
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
 * #L%
 */

package fr.djaytan.minecraft.jobsreborn.patchplacebreak.inject.provider;

import fr.djaytan.minecraft.jobsreborn.patchplacebreak.PatchPlaceBreakException;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.properties.DataSourceProperties;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.properties.DataSourceType;
import javax.inject.Inject;
import javax.inject.Provider;
import lombok.NonNull;
import org.flywaydb.core.api.Location;

public class LocationProvider implements Provider<Location> {

  private static final String DB_MIGRATION_DESCRIPTOR_FORMAT = "/db/migration/%s";

  private final DataSourceProperties dataSourceProperties;

  @Inject
  public LocationProvider(DataSourceProperties dataSourceProperties) {
    this.dataSourceProperties = dataSourceProperties;
  }

  @Override
  public @NonNull Location get() {
    DataSourceType dataSourceType = dataSourceProperties.getType();

    switch (dataSourceType) {
      case MYSQL:
      case SQLITE:
        {
          String descriptor =
              String.format(DB_MIGRATION_DESCRIPTOR_FORMAT, dataSourceType.name().toLowerCase());
          return new Location(descriptor);
        }
      default:
        {
          throw PatchPlaceBreakException.unsupportedDataSourceType(dataSourceType);
        }
    }
  }
}
