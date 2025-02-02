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

/**
 * Represents the properties related to the connection pool.
 *
 * @param connectionTimeout The timeout when attempting to establish a new connection to the DBMS
 *     server.
 * @param poolSize The connection pool size.
 */
public record ConnectionPoolProperties(long connectionTimeout, int poolSize) {

  public static final ConnectionPoolProperties DEFAULT = new ConnectionPoolProperties(30000, 10);

  public ConnectionPoolProperties {
    Validate.inclusiveBetween(
        1, 600000, connectionTimeout, "The connection timeout must be between 1 and 600000");
    Validate.inclusiveBetween(1, 100, poolSize, "The pool size must be between 1 and 100");
  }
}
