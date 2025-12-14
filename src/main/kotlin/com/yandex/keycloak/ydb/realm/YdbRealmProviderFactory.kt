package com.yandex.keycloak.ydb.realm

import com.yandex.keycloak.ydb.config.ProviderPriority.PROVIDER_PRIORITY
import com.yandex.keycloak.ydb.config.YdbProfile.IS_YDB_PROFILE_ENABLED
import com.yandex.keycloak.ydb.connection.YdbConnectionProvider
import com.yandex.keycloak.ydb.realm.service.RealmService
import org.keycloak.Config
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory
import org.keycloak.models.RealmProviderFactory
import org.keycloak.provider.EnvironmentDependentProviderFactory

class YdbRealmProviderFactory() : RealmProviderFactory<YdbRealmProvider>, EnvironmentDependentProviderFactory {
  override fun create(session: KeycloakSession): YdbRealmProvider =
    session.getProvider(YdbConnectionProvider::class.java)?.let {
      YdbRealmProvider(session, RealmService(it.ydbDSLContext))
    } ?: error("YdbConnectionProvider is not configured")

  override fun init(scope: Config.Scope) {
    // no operations
  }

  override fun postInit(p0: KeycloakSessionFactory?) {
    // no operations
  }

  override fun close() {
    // no operations
  }

  override fun getId(): String = ID

  override fun isSupported(scope: Config.Scope): Boolean = IS_YDB_PROFILE_ENABLED

  override fun order(): Int = PROVIDER_PRIORITY + 1

  private companion object {
    private const val ID = "ydb-realm-provider-factory"
  }
}
