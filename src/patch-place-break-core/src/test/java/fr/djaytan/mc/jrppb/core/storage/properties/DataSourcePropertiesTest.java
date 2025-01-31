package fr.djaytan.mc.jrppb.core.storage.properties;

import static fr.djaytan.mc.jrppb.core.storage.properties.ConnectionPoolPropertiesTestDataSet.NOMINAL_CONNECTION_POOL_PROPERTIES;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.NOMINAL_TABLE_NAME;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.NOMINAL_TYPE;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.generateConnectionPoolProperties;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.generateDbmsServerProperties;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.generateTableName;
import static fr.djaytan.mc.jrppb.core.storage.properties.DataSourcePropertiesTestDataSet.generateType;
import static fr.djaytan.mc.jrppb.core.storage.properties.DbmsServerPropertiesTestDataSet.NOMINAL_DBMS_SERVER_PROPERTIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.instancio.Instancio;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

final class DataSourcePropertiesTest {

  @Test
  void whenInstantiating_withDefaultValues_shallSucceed() {
    var dataSourceProperties = new DataSourceProperties();

    assertThat(dataSourceProperties.type()).isEqualTo(DataSourceProperties.Type.SQLITE);
    assertThat(dataSourceProperties.tableName()).isEqualTo("patch_place_break_tag");
    assertThat(dataSourceProperties.connectionPool()).isEqualTo(new ConnectionPoolProperties());
    assertThat(dataSourceProperties.dbmsServer()).isEqualTo(new DbmsServerProperties());
  }

  @Test
  void whenInstantiating_withNominalValues_shallSucceed() {
    var dataSourceProperties =
        new DataSourceProperties(
            NOMINAL_TYPE,
            NOMINAL_TABLE_NAME,
            NOMINAL_DBMS_SERVER_PROPERTIES,
            NOMINAL_CONNECTION_POOL_PROPERTIES);

    assertThat(dataSourceProperties.type()).isEqualTo(NOMINAL_TYPE);
    assertThat(dataSourceProperties.tableName()).isEqualTo(NOMINAL_TABLE_NAME);
    assertThat(dataSourceProperties.connectionPool()).isEqualTo(NOMINAL_CONNECTION_POOL_PROPERTIES);
    assertThat(dataSourceProperties.dbmsServer()).isEqualTo(NOMINAL_DBMS_SERVER_PROPERTIES);
  }

  @RepeatedTest(100)
  void whenInstantiating_withRandomValidValues_shallSucceed() {
    // Assemble
    var type = generateType();
    String tableName = generateTableName();
    var dbmsServerProperties = generateDbmsServerProperties();
    var connectionPoolProperties = generateConnectionPoolProperties();

    var dataSourceProperties =
        new DataSourceProperties(type, tableName, dbmsServerProperties, connectionPoolProperties);
    System.out.println(dataSourceProperties);

    assertThat(dataSourceProperties.type()).isEqualTo(type);
    assertThat(dataSourceProperties.tableName()).isEqualTo(tableName);
    assertThat(dataSourceProperties.dbmsServer()).isEqualTo(dbmsServerProperties);
    assertThat(dataSourceProperties.connectionPool()).isEqualTo(connectionPoolProperties);
  }

  @Test
  void whenInstantiating_withEmptyTableName_shallThrowException() {
    assertThat_instantiationWithBlankName_doesThrowException("");
  }

  @Test
  void whenInstantiating_withSingleSpaceBlankTableName_shallThrowException() {
    assertThat_instantiationWithBlankName_doesThrowException(" ");
  }

  @Test
  void whenInstantiating_withMultipleSpacesBlankTableName_shallThrowException() {
    assertThat_instantiationWithBlankName_doesThrowException(
        " ".repeat(Instancio.gen().ints().range(2, 32).get()));
  }

  private static void assertThat_instantiationWithBlankName_doesThrowException(String tableName) {
    var type = generateType();
    var dbmsServerProperties = generateDbmsServerProperties();
    var connectionPoolProperties = generateConnectionPoolProperties();

    assertThatThrownBy(
            () ->
                new DataSourceProperties(
                    type, tableName, dbmsServerProperties, connectionPoolProperties))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The SQL table name must not be blank");
  }
}
