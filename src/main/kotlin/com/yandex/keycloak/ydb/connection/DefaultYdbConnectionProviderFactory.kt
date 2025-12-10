package com.yandex.keycloak.ydb.connection

import com.yandex.keycloak.ydb.YdbProfiles.IS_YDB_PROFILE_ENABLED
import com.yandex.keycloak.ydb.migration.YdbMigrationManager.migrate
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jboss.logging.Logger
import org.keycloak.Config
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory
import org.keycloak.provider.EnvironmentDependentProviderFactory
import tech.ydb.jooq.YdbDSLContext
import java.sql.Connection
import java.sql.SQLException

class DefaultYdbConnectionProviderFactory() : YdbConnectionProviderFactory<YdbConnectionProvider>,
  EnvironmentDependentProviderFactory {
  private val logger: Logger = Logger.getLogger(DefaultYdbConnectionProviderFactory::class.java)

  private lateinit var dataSource: HikariDataSource
  private lateinit var dslContext: YdbDSLContext

  override fun create(session: KeycloakSession): YdbConnectionProvider = object : YdbConnectionProvider {
    override val connection: Connection = try {
      dataSource.connection
    } catch (e: SQLException) {
      logger.error("Failed to get YDB connection from pool", e)
      throw e
    }

    override val ydbDSLContext: YdbDSLContext = dslContext

    override fun close() = connection.close()
  }

  override fun init(scope: Config.Scope) {
    val jdbcUrl = scope["jdbcUrl"]
    val poolSize = scope.getInt("poolSize", 10)
    val connectionTimeout = scope.getLong("connectionTimeout", 5000L)

    val config = HikariConfig().apply {// todo revise
      this.jdbcUrl = jdbcUrl
      this.driverClassName = "tech.ydb.jdbc.YdbDriver"
      this.maximumPoolSize = poolSize
      this.connectionTimeout = connectionTimeout
      this.poolName = "YDB-HikariPool"
      this.isAutoCommit = false // todo revise
    }

    dataSource = HikariDataSource(config)

    logger.info("YDB HikariCP connection pool and JOOQ DSLContext configured successfully")

    migrate(dataSource)
  }

  override fun postInit(p0: KeycloakSessionFactory) {
    // no postInit operations
  }

  override fun close() = dataSource.close()

  override fun getId(): String = PROVIDER_ID

  override fun isSupported(scope: Config.Scope): Boolean = IS_YDB_PROFILE_ENABLED.also {
    logger.info("was in isSupported")
  }

  private companion object {
    private const val PROVIDER_ID: String = "default"
  }
}
