package com.yandex.keycloak.ydb.realm

import com.yandex.keycloak.ydb.connection.YdbConnectionProvider
import org.keycloak.Config
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory
import org.keycloak.models.RealmProviderFactory
import org.keycloak.provider.EnvironmentDependentProviderFactory

class YdbRealmProviderFactory(): RealmProviderFactory<YdbRealmProvider>, EnvironmentDependentProviderFactory {
  override fun create(session: KeycloakSession): YdbRealmProvider {

    val ydbConnectionProvider = session.getProvider(YdbConnectionProvider::class.java) ?: error("YdbConnectionProvider is not configured")

    return YdbRealmProvider()
  }

  override fun init(p0: Config.Scope?) {
    TODO("Not yet implemented")
  }

  override fun postInit(p0: KeycloakSessionFactory?) {
    TODO("Not yet implemented")
  }

  override fun close() {
    TODO("Not yet implemented")
  }

  override fun getId(): String? {
    TODO("Not yet implemented")
  }

  override fun isSupported(p0: Config.Scope?): Boolean {
    TODO("Not yet implemented")
  }
}