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
package fr.djaytan.minecraft.jobsreborn.patchplacebreak.config;

import fr.djaytan.minecraft.jobsreborn.patchplacebreak.commons.test.ExceptionBaseTest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("java:S2187")
class ConfigExceptionTest extends ExceptionBaseTest {

  @Override
  protected @NotNull Exception getException() {
    return new ConfigException();
  }

  @Override
  protected @NotNull Exception getException(@NotNull String message) {
    return new ConfigException(message);
  }

  @Override
  protected @NotNull Exception getException(@Nullable Throwable cause) {
    return new ConfigException(cause);
  }

  @Override
  protected @NotNull Exception getException(@NotNull String message, @Nullable Throwable cause) {
    return new ConfigException(message, cause);
  }
}
