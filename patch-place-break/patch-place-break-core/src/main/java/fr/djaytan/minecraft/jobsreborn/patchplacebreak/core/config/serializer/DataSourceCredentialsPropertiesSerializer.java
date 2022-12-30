package fr.djaytan.minecraft.jobsreborn.patchplacebreak.core.config.serializer;

import java.lang.reflect.Type;

import javax.inject.Singleton;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import fr.djaytan.minecraft.jobsreborn.patchplacebreak.internal.storage.api.properties.DataSourceCredentialsProperties;

@Singleton
final class DataSourceCredentialsPropertiesSerializer
    implements TypeSerializer<DataSourceCredentialsProperties> {

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";

  @Override
  public DataSourceCredentialsProperties deserialize(Type type, ConfigurationNode node)
      throws SerializationException {
    String username = node.node(USERNAME).require(String.class);
    String password = node.node(PASSWORD).require(String.class);
    return DataSourceCredentialsProperties.of(username, password);
  }

  @Override
  public void serialize(Type type, @Nullable DataSourceCredentialsProperties obj,
      ConfigurationNode node) {
    throw ConfigSerializationException.unexpectedSerialization();
  }
}
