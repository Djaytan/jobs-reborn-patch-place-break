package fr.djaytan.mc.jrppb.core.storage.properties;

import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.CONNECTION_POOL_PROPERTIES_MODEL;
import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.NOMINAL_CONNECTION_POOL_PROPERTIES;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.DBMS_SERVER_PROPERTIES_MODEL;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.NOMINAL_DBMS_SERVER_PROPERTIES;
import static org.instancio.Select.field;

import fr.djaytan.mc.jrppb.core.storage.properties.DataSourceProperties.Type;
import org.instancio.Instancio;
import org.instancio.Model;
import org.jetbrains.annotations.NotNull;

public final class DataSourcePropertiesTestDataSet {

  static final Type NOMINAL_TYPE = Type.MYSQL;
  static final String NOMINAL_TABLE_NAME = "nominal_table_name";
  public static final DataSourceProperties NOMINAL_DATA_SOURCE_PROPERTIES =
      new DataSourceProperties(
          NOMINAL_TYPE,
          NOMINAL_TABLE_NAME,
          NOMINAL_DBMS_SERVER_PROPERTIES,
          NOMINAL_CONNECTION_POOL_PROPERTIES);

  public static final Model<DataSourceProperties> DATA_SOURCE_PROPERTIES_MODEL =
      Instancio.of(DataSourceProperties.class)
          .supply(field(DataSourceProperties::type), () -> generateType())
          .supply(field(DataSourceProperties::tableName), () -> generateTableName())
          .setModel(field(DataSourceProperties::dbmsServer), DBMS_SERVER_PROPERTIES_MODEL)
          .setModel(field(DataSourceProperties::connectionPool), CONNECTION_POOL_PROPERTIES_MODEL)
          .toModel();

  static @NotNull Type generateType() {
    return Instancio.create(Type.class);
  }

  static @NotNull String generateTableName() {
    return Instancio.gen().string().alphaNumeric().mixedCase().length(1, 64).get();
  }

  static @NotNull DbmsServerProperties generateDbmsServerProperties() {
    return Instancio.create(DBMS_SERVER_PROPERTIES_MODEL);
  }

  static @NotNull ConnectionPoolProperties generateConnectionPoolProperties() {
    return Instancio.create(CONNECTION_POOL_PROPERTIES_MODEL);
  }
}
