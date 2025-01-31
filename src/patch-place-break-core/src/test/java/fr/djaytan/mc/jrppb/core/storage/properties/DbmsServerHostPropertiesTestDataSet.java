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

public final class DbmsServerHostPropertiesTestDataSet {

  public static final String NOMINAL_DBMS_SERVER_HOSTNAME = "db.amazing.com";
  public static final int NOMINAL_DBMS_SERVER_PORT = 4123;
  public static final boolean NOMINAL_DBMS_SERVER_IS_SSL_ENABLED = true;
  public static final DbmsServerHostProperties NOMINAL_DBMS_SERVER_HOST_PROPERTIES =
      new DbmsServerHostProperties(
          NOMINAL_DBMS_SERVER_HOSTNAME,
          NOMINAL_DBMS_SERVER_PORT,
          NOMINAL_DBMS_SERVER_IS_SSL_ENABLED);

  public static @NotNull DbmsServerHostProperties randomDbmsServerHostProperties() {
    return new DbmsServerHostProperties(
        randomDbmsServerHostname(), randomDbmsServerPort(), randomDbmsServerSslEnabled());
  }

  public static @NotNull String randomDbmsServerHostname() {
    return randomDbmsServerHostnameOfLength(1, 255);
  }

  public static @NotNull String randomDbmsServerHostnameOfLength(int length) {
    return randomDbmsServerHostnameOfLength(length, length);
  }

  public static @NotNull String randomDbmsServerHostnameOfLength(int minLength, int maxLength) {
    return Instancio.gen().string().alphaNumeric().mixedCase().length(minLength, maxLength).get();
  }

  public static @NotNull String randomTooLongDbmsServerHostname() {
    return randomDbmsServerHostnameOfLength(256, 100000);
  }

  public static int randomDbmsServerPort() {
    return Instancio.gen().ints().range(1, 65535).get();
  }

  public static int randomInvalidDbmsServerPort() {
    return Instancio.gen().ints().range(Integer.MIN_VALUE, 0).range(65536, Integer.MAX_VALUE).get();
  }

  public static boolean randomDbmsServerSslEnabled() {
    return Instancio.gen().booleans().get();
  }
}
