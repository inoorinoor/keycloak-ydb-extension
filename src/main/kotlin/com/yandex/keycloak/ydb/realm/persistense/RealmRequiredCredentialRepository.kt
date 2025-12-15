package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.tables.pojos.RealmRequiredCredential
import tech.ydb.jooq.YdbDSLContext


@Suppress("kotlin:S6518")
class RealmRequiredCredentialRepository(
  private val dsl: YdbDSLContext
) {
  fun fetchByRealmId(realmId: String): List<RealmRequiredCredential> {
    TODO()
  }

  fun insert(toPojo: RealmRequiredCredential) {
    TODO()
  }
}
