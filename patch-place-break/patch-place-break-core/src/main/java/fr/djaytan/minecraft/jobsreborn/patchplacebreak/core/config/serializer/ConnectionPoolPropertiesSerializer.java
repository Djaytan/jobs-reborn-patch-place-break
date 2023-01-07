/*
 * MIT License
 *
 * Copyright (c) 2023 Loïc DUBOIS-TERMOZ (alias Djaytan)
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

package fr.djaytan.minecraft.jobsreborn.patchplacebreak.core.config.serializer;

import java.lang.reflect.Type;

import javax.inject.Singleton;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import fr.djaytan.minecraft.jobsreborn.patchplacebreak.internal.storage.api.properties.ConnectionPoolProperties;

@Singleton
public class ConnectionPoolPropertiesSerializer
    implements TypeSerializer<ConnectionPoolProperties> {

  private static final String CONNECTION_TIMEOUT = "connectionTimeout";
  private static final String POOL_SIZE = "poolSize";

  @Override
  public ConnectionPoolProperties deserialize(Type type, ConfigurationNode node)
      throws SerializationException {
    long connectionTimeout = node.node(CONNECTION_TIMEOUT).require(Long.class);
    int poolSize = node.node(POOL_SIZE).require(Integer.class);
    return ConnectionPoolProperties.of(connectionTimeout, poolSize);
  }

  @Override
  public void serialize(Type type, @Nullable ConnectionPoolProperties obj, ConfigurationNode node) {
    throw ConfigSerializationException.unexpectedSerialization();
  }
}