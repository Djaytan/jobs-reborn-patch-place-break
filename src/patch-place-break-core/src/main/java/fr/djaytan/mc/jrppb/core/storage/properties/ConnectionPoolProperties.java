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

  public ConnectionPoolProperties {
    Validate.inclusiveBetween(
        1, 600000, connectionTimeout, "The connection timeout must be between 1 and 600000");
    Validate.inclusiveBetween(1, 100, poolSize, "The pool size must be between 1 and 100");
  }

  public ConnectionPoolProperties() {
    this(30000, 10);
  }
}
