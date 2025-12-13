package com.yandex.keycloak.ydb.realm.persistense

import com.yandex.keycloak.ydb.realm.persistense.entity.RealmEntity
import jooq.generated.default_schema.tables.references.REALMS
import jooq.generated.default_schema.tables.references.REALM_ATTRIBUTES
import org.keycloak.models.utils.KeycloakModelUtils.generateId
import tech.ydb.jooq.YdbDSLContext

@Suppress("kotlin:S6518")
class RealmRepository(private val dsl: YdbDSLContext) {

  fun createRealm(id: String, name: String): RealmEntity? =
    dsl.insertInto(REALMS)
      .set(REALMS.ID, id)
      .set(REALMS.NAME, name)
      .returning()
      .fetchOne()
      ?.let { RealmEntity(it.id, it.name) }


  fun findRealmById(id: String): RealmEntity? = dsl.selectFrom(REALMS)
    .where(REALMS.ID.eq(id))
    .fetchOne()
    ?.let { RealmEntity(it.id, it.name) }

  fun findRealmByName(name: String): RealmEntity? = dsl.selectFrom(REALMS)
    .where(REALMS.NAME.eq(name))
    .fetchOne()
    ?.let { RealmEntity(it.id, it.name) }

  fun updateRealm(id: String, name: String): Boolean = dsl.update(REALMS)
    .set(REALMS.NAME, name)
    .where(REALMS.ID.eq(id))
    .execute() > 0

  fun deleteRealm(id: String): Boolean = dsl.deleteFrom(REALMS)
    .where(REALMS.ID.eq(id))
    .execute() > 0

  fun getAllRealms(): List<RealmEntity> = dsl.selectFrom(REALMS)
    .fetch()
    .map { RealmEntity(it.id, it.name) }

  fun saveAttribute(realmId: String, name: String, value: String?) {
    dsl.insertInto(REALM_ATTRIBUTES)
      .set(REALM_ATTRIBUTES.ID, generateId())
      .set(REALM_ATTRIBUTES.REALM_ID, realmId)
      .set(REALM_ATTRIBUTES.NAME, name)
      .set(REALM_ATTRIBUTES.VALUE, value)
      .onConflict(REALM_ATTRIBUTES.REALM_ID, REALM_ATTRIBUTES.NAME)
      .doUpdate()
      .set(REALM_ATTRIBUTES.VALUE, value)
      .execute()
  }

  fun getAttribute(realmId: String, name: String): String? = dsl.selectFrom(REALM_ATTRIBUTES)
    .where(REALM_ATTRIBUTES.REALM_ID.eq(realmId))
    .and(REALM_ATTRIBUTES.NAME.eq(name))
    .fetchOne()
    ?.value

  fun getAttributes(realmId: String): Map<String, String> = dsl.selectFrom(REALM_ATTRIBUTES)
    .where(REALM_ATTRIBUTES.REALM_ID.eq(realmId))
    .fetch()
    .associate { it.name to (it.value ?: "") }

  fun removeAttribute(realmId: String, name: String): Boolean = dsl.deleteFrom(REALM_ATTRIBUTES)
    .where(REALM_ATTRIBUTES.REALM_ID.eq(realmId))
    .and(REALM_ATTRIBUTES.NAME.eq(name))
    .execute() > 0
}
