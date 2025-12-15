package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.tables.pojos.RealmRequiredCredentials
import tech.ydb.jooq.YdbDSLContext


@Suppress("kotlin:S6518")
class RealmRequiredCredentialRepository(
  private val dsl: YdbDSLContext
) {
  fun fetchByRealmId(realmId: String): List<RealmRequiredCredentials> {
    TODO()
  }

  fun insert(toPojo: RealmRequiredCredentials) {
    TODO()
  }
}
