package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.tables.daos.RealmAttributesDao
import jooq.generated.default_schema.tables.pojos.RealmAttributes
import jooq.generated.default_schema.tables.references.REALM_ATTRIBUTES
import org.jooq.DSLContext
import org.keycloak.models.utils.KeycloakModelUtils.generateId
import tech.ydb.jooq.YdbDSLContext

@Suppress("kotlin:S6518")
class RealmAttributeRepository(
  private val dsl: YdbDSLContext
) : RealmAttributesDao(dsl.configuration()) {

  fun deleteByRealmId(txDsl: DSLContext, realmId: String) =
    txDsl.deleteFrom(REALM_ATTRIBUTES)
      .where(REALM_ATTRIBUTES.REALM_ID.eq(realmId))
      .execute()

  fun removeAllRealmAttributes(realmId: String) {
    dsl.deleteFrom(REALM_ATTRIBUTES)
      .where(REALM_ATTRIBUTES.REALM_ID.eq(realmId))
      .execute()
  }

  fun upsertRealmAttributes(realmId: String, name: String, values: List<String>) {
    dsl.upsertInto(REALM_ATTRIBUTES)
      .values(values.map { RealmAttributes(generateId(), name, it, realmId) })
      .execute()
  }

  // TODO: ask is it good to pass context like this
  fun deleteByRealmIdAndName(realmId: String, name: String) = deleteByRealmIdAndName(dsl, realmId, name)
  fun deleteByRealmIdAndName(txDsl: DSLContext, realmId: String, name: String) = with(REALM_ATTRIBUTES) {
    txDsl.deleteFrom(this)
      .where(REALM_ID.eq(realmId).and(NAME.eq(name)))
      .execute()
  }

  // TODO: ask is it good to pass context like this
  fun fetchByRealmIdAndName(realmId: String, name: String): List<RealmAttributes> =
    fetchByRealmIdAndName(dsl, realmId, name)

  // TODO: ask is it good to pass context like this
  fun fetchByRealmIdAndName(txDsl: DSLContext, realmId: String, name: String) = with(REALM_ATTRIBUTES) {
    txDsl.selectFrom(this)
      .where(REALM_ID.eq(realmId).and(NAME.eq(name)))
      .fetch(mapper())
  }
}
