package com.yandex.keycloak.ydb.connection

import com.yandex.keycloak.ydb.YdbProfiles.IS_YDB_PROFILE_ENABLED
import com.yandex.keycloak.ydb.migration.YdbMigrationManager.migrate
import com.yandex.keycloak.ydb.util.hikariDataSource
import com.zaxxer.hikari.HikariDataSource
import org.jboss.logging.Logger
import org.keycloak.Config
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory
import org.keycloak.provider.EnvironmentDependentProviderFactory
import tech.ydb.jooq.YdbDSLContext
import tech.ydb.jooq.impl.YdbDSLContextImpl

class DefaultYdbConnectionProviderFactory() : YdbConnectionProviderFactory<YdbConnectionProvider>,
  EnvironmentDependentProviderFactory {
  private val logger: Logger = Logger.getLogger(DefaultYdbConnectionProviderFactory::class.java)

  private lateinit var dataSource: HikariDataSource
  private lateinit var dslContext: YdbDSLContext

  override fun create(session: KeycloakSession): YdbConnectionProvider = createYdbConnectionProvider()

  override fun init(scope: Config.Scope) {
    val jdbcUrl = scope["jdbcUrl"]
    val poolSize = scope.getInt("poolSize", 10)
    val connectionTimeout = scope.getLong("connectionTimeout", 5000L)

    dataSource = hikariDataSource(jdbcUrl, poolSize, connectionTimeout)

    dslContext = YdbDSLContextImpl(dataSource)

    logger.info("YDB HikariCP connection pool and JOOQ DSLContext configured successfully")

    migrate(dataSource)
  }


  override fun postInit(p0: KeycloakSessionFactory) {
    // no postInit operations
  }

  override fun close() = dataSource.close()

  override fun getId(): String = PROVIDER_ID

  override fun isSupported(scope: Config.Scope): Boolean = IS_YDB_PROFILE_ENABLED

  private fun createYdbConnectionProvider(): YdbConnectionProvider = object : YdbConnectionProvider {
    override val ydbDSLContext: YdbDSLContext = dslContext

    override fun close() {
      // no operations
    }
  }

  private companion object {
    private const val PROVIDER_ID: String = "default"
  }
}
