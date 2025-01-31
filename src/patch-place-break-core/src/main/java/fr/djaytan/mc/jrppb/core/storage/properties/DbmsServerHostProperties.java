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
 * Represents the DBMS server properties related to the host.
 *
 * <p>A hostname cannot exceed 255 characters as per the DNS standard specification.
 *
 * <p><i>Note: we explicitly allow most invalid hostnames since otherwise it will require too much
 * work for too limited earnings. In fact, specifying an invalid hostname will be detected by
 * underlying systems (e.g. JDBC). So, we only focus on easily detectable invalid addresses.</i>
 *
 * <p>A port cannot exceed 65535, which is the maximum value allowed by the Transport Control
 * Protocol (TCP) and User Datagram Protocol (UDP) standards. The value "0" is excluded since it's a
 * reserved one and must not be used.
 *
 * @param hostname The hostname of the targeted DBMS server (an IP address (IPv4/IPv6) or a domain
 *     name).
 * @param port The port on which the targeted DBMS server is exposed.
 * @param isSslEnabled <code>true</code> if the SSL communication is enabled, <code>false</code>
 *     otherwise.
 */
public record DbmsServerHostProperties(@NotNull String hostname, int port, boolean isSslEnabled) {

  public static final DbmsServerHostProperties DEFAULT =
      new DbmsServerHostProperties("localhost", 3306, true);

  public DbmsServerHostProperties {
    Validate.notBlank(hostname, "The DBMS server hostname cannot be blank");
    Validate.isTrue(
        hostname.length() <= 255, "The DBMS server hostname cannot exceed 255 characters");
    Validate.inclusiveBetween(1, 65535, port, "The DBMS server port must be between 1 and 65535");
  }
}
