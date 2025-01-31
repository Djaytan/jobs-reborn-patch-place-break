package fr.djaytan.mc.jrppb.core.storage.properties;

import static org.instancio.Select.field;

import org.instancio.Instancio;
import org.instancio.Model;

public final class ConnectionPoolPropertiesTestDataSet {

  static final long NOMINAL_CONNECTION_TIMEOUT = 60000L;
  static final int NOMINAL_POOL_SIZE = 20;
  public static final ConnectionPoolProperties NOMINAL_CONNECTION_POOL_PROPERTIES =
      new ConnectionPoolProperties(NOMINAL_CONNECTION_TIMEOUT, NOMINAL_POOL_SIZE);

  public static final Model<ConnectionPoolProperties> CONNECTION_POOL_PROPERTIES_MODEL =
      Instancio.of(ConnectionPoolProperties.class)
          .supply(
              field(ConnectionPoolProperties::connectionTimeout), () -> generateConnectionTimeout())
          .supply(field(ConnectionPoolProperties::poolSize), () -> generatePoolSize())
          .toModel();

  static long generateConnectionTimeout() {
    return Instancio.gen().longs().range(1L, 600000L).get();
  }

  static int generatePoolSize() {
    return Instancio.gen().ints().range(1, 100).get();
  }
}
