package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.Tables.REALM_ATTRIBUTE
import jooq.generated.default_schema.tables.pojos.RealmAttribute
import org.jooq.DSLContext
import org.keycloak.models.utils.KeycloakModelUtils.generateId
import tech.ydb.jooq.YdbDSLContext

@Suppress("kotlin:S6518")
class RealmAttributeRepository(
  private val dsl: YdbDSLContext
) {

  fun deleteByRealmId(txDsl: DSLContext, realmId: String) =
    txDsl.deleteFrom(REALM_ATTRIBUTE)
      .where(REALM_ATTRIBUTE.REALM_ID.eq(realmId))
      .execute()

  fun removeAllRealmAttribute(realmId: String) {
    dsl.deleteFrom(REALM_ATTRIBUTE)
      .where(REALM_ATTRIBUTE.REALM_ID.eq(realmId))
      .execute()
  }

  fun upsertRealmAttribute(realmId: String, name: String, values: List<String>) {
    dsl.upsertInto(REALM_ATTRIBUTE)
      .values(values.map { RealmAttribute(name, it, realmId) })
      .execute()
  }

  // TODO: ask is it good to pass context like this
  fun deleteByRealmIdAndName(realmId: String, name: String) = deleteByRealmIdAndName(dsl, realmId, name)
  fun deleteByRealmIdAndName(txDsl: DSLContext, realmId: String, name: String) = with(REALM_ATTRIBUTE) {
    txDsl.deleteFrom(this)
      .where(REALM_ID.eq(realmId).and(NAME.eq(name)))
      .execute()
  }

  // TODO: ask is it good to pass context like this
  fun fetchByRealmIdAndName(realmId: String, name: String): List<RealmAttribute> =
    fetchByRealmIdAndName(dsl, realmId, name)

  // TODO: ask is it good to pass context like this
  fun fetchByRealmIdAndName(txDsl: DSLContext, realmId: String, name: String): List<RealmAttribute> =
    with(REALM_ATTRIBUTE) {
      TODO()
//    txDsl.selectFrom(this)
//      .where(REALM_ID.eq(realmId).and(NAME.eq(name)))
//      .fetch(mapping(RealmAttribute::class.java))
    }

  fun fetchByRealmId(realmId: String): List<RealmAttribute> {
    TODO()
  }

  fun insert(RealmAttribute: RealmAttribute) {
    TODO()
  }

  fun update(map: List<RealmAttribute>) {
    TODO()
  }
}
