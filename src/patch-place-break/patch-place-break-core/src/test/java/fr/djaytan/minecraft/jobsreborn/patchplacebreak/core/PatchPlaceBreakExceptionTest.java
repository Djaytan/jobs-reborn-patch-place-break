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
package fr.djaytan.minecraft.jobsreborn.patchplacebreak.core;

import static org.assertj.core.api.Assertions.assertThat;

import fr.djaytan.minecraft.jobsreborn.patchplacebreak.commons.test.ExceptionBaseTest;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.properties.DataSourceType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("java:S2187")
class PatchPlaceBreakExceptionTest extends ExceptionBaseTest {

  @Override
  protected @NotNull Exception getException() {
    return new PatchPlaceBreakException();
  }

  @Override
  protected @NotNull Exception getException(@NotNull String message) {
    return new PatchPlaceBreakException(message);
  }

  @Override
  protected @NotNull Exception getException(@Nullable Throwable cause) {
    return new PatchPlaceBreakException(cause);
  }

  @Override
  protected @NotNull Exception getException(@NotNull String message, @Nullable Throwable cause) {
    return new PatchPlaceBreakException(message, cause);
  }

  @Test
  @DisplayName("When instantiating unsupported data source type exception")
  void whenInstantiatingUnsupportedDataSourceTypeException() {
    // Given
    DataSourceType dataSourceType = DataSourceType.MYSQL;

    // When
    PatchPlaceBreakException patchPlaceBreakException =
        PatchPlaceBreakException.unsupportedDataSourceType(dataSourceType);

    // Then
    assertThat(patchPlaceBreakException).hasMessage("Unsupported data source type 'MYSQL'");
  }
}
