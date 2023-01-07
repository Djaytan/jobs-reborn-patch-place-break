/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Loïc DUBOIS-TERMOZ (alias Djaytan)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.djaytan.minecraft.jobsreborn.patchplacebreak.bukkit.slf4j;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * /!\ WARNING /!\
 * <p>
 *  This class is a copy from the slf4j-jdk14 module:
 *  <a href="https://github.com/qos-ch/slf4j/edit/master/slf4j-jdk14/src/main/java/org/slf4j/jul/JULServiceProvider.java">source</a>
 * </p>
 * <p>
 *  The purpose is to permit the creation of a SLF4J instance of the Bukkit logger to ease the decoupling.
 * </p>
 */
public class BukkitLoggerServiceProvider implements SLF4JServiceProvider {

  /**
   * Declare the version of the SLF4J API this implementation is compiled
   * against. The value of this field is modified with each major release.
   */
  // to avoid constant folding by the compiler, this field must *not* be final
  public static String REQUESTED_API_VERSION = "2.0.99"; // !final

  private ILoggerFactory loggerFactory;
  private IMarkerFactory markerFactory;
  private MDCAdapter mdcAdapter;

  @Override
  public ILoggerFactory getLoggerFactory() {
    return loggerFactory;
  }

  @Override
  public IMarkerFactory getMarkerFactory() {
    return markerFactory;
  }

  public MDCAdapter getMDCAdapter() {
    return mdcAdapter;
  }

  @Override
  public String getRequestedApiVersion() {
    return REQUESTED_API_VERSION;
  }

  @Override
  public void initialize() {
    loggerFactory = new BukkitLoggerFactory();
    markerFactory = new BasicMarkerFactory();
    mdcAdapter = new BasicMDCAdapter();
  }
}
