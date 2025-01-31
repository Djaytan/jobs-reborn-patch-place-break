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

import org.instancio.Instancio;
import org.jetbrains.annotations.NotNull;

public final class ConnectionPoolPropertiesTestDataSet {

  public static final long NOMINAL_CONNECTION_TIMEOUT = 60000L;
  public static final int NOMINAL_POOL_SIZE = 20;
  public static final ConnectionPoolProperties NOMINAL_CONNECTION_POOL_PROPERTIES =
      new ConnectionPoolProperties(NOMINAL_CONNECTION_TIMEOUT, NOMINAL_POOL_SIZE);

  public static @NotNull ConnectionPoolProperties randomConnectionPoolProperties() {
    return new ConnectionPoolProperties(randomConnectionTimeout(), randomPoolSize());
  }

  public static long randomConnectionTimeout() {
    return Instancio.gen().longs().range(1L, 600000L).get();
  }

  public static long randomInvalidConnectionTimeout() {
    return Instancio.gen().longs().range(Long.MIN_VALUE, 0L).range(600001L, Long.MAX_VALUE).get();
  }

  public static int randomPoolSize() {
    return Instancio.gen().ints().range(1, 100).get();
  }

  public static int randomInvalidPoolSize() {
    return Instancio.gen().ints().range(Integer.MIN_VALUE, 0).range(101, Integer.MAX_VALUE).get();
  }
}
