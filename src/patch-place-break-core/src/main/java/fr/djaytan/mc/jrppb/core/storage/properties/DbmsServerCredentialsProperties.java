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
 * Represents the DBMS server properties related to credentials.
 *
 * @param username The username to be used for authentication with the DBMS server.
 * @param password The password to be used for authentication with the DBMS server.
 */
public record DbmsServerCredentialsProperties(@NotNull String username, @NotNull String password) {

  // TODO: do we really want to set default values for username and password?
  public static final DbmsServerCredentialsProperties DEFAULT =
      new DbmsServerCredentialsProperties("username", "password");

  public DbmsServerCredentialsProperties {
    Validate.notBlank(username, "The DBMS server username cannot be blank");
  }
}
