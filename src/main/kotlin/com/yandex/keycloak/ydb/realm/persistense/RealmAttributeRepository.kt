package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.tables.daos.RealmAttributesDao
import jooq.generated.default_schema.tables.references.REALM_ATTRIBUTES
import org.jooq.DSLContext
import tech.ydb.jooq.YdbDSLContext

@Suppress("kotlin:S6518")
class RealmAttributeRepository(
  private val dsl: YdbDSLContext
): RealmAttributesDao(dsl.configuration()) {

  fun deleteByRealmId(txDsl: DSLContext, realmId: String) =
    txDsl.deleteFrom(REALM_ATTRIBUTES)
      .where(REALM_ATTRIBUTES.REALM_ID.eq(realmId))
      .execute()

  fun removeAllRealmAttributes(realmId: String) {
    dsl.deleteFrom(REALM_ATTRIBUTES)
      .where(REALM_ATTRIBUTES.REALM_ID.eq(realmId))
      .execute()
  }
}