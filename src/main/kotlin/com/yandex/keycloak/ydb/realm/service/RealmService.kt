package com.yandex.keycloak.ydb.realm.service

import com.yandex.keycloak.ydb.realm.RealmMapper.toDomain
import com.yandex.keycloak.ydb.realm.RealmMapper.toPojo
import com.yandex.keycloak.ydb.realm.domain.Realm
import com.yandex.keycloak.ydb.realm.persistense.RealmAttributeRepository
import com.yandex.keycloak.ydb.realm.persistense.RealmRepository
import jooq.generated.default_schema.tables.pojos.RealmAttributes
import org.jboss.logging.Logger
import org.keycloak.models.utils.KeycloakModelUtils.generateId
import tech.ydb.jooq.YdbDSLContext


class RealmService(
  private val dsl: YdbDSLContext
) {
  private val realmRepository = RealmRepository(dsl)
  private val realmAttributeRepository = RealmAttributeRepository(dsl)

  private val logger = Logger.getLogger(RealmService::class.java.name)

  fun getRealmById(id: String) = realmRepository.fetchOneById(id)?.toDomain()

  fun createRealm(entity: Realm) = realmRepository.insert(entity.toPojo())

  fun getRealmByName(name: String) = realmRepository.fetchByName(name).firstOrNull()?.toDomain()

  fun getAllRealms(): List<Realm> = realmRepository.findAll().map { it.toDomain() }

  fun removeRealm(id: String): Boolean = dsl.transactionResult { ctx ->
    realmRepository.fetchOneById(ctx.dsl(), id) ?: return@transactionResult false

    realmAttributeRepository.deleteByRealmId(ctx.dsl(), id)

    realmRepository.deleteById(ctx.dsl(), id)
  }

  fun updateRealm(entity: Realm) = realmRepository.update(entity.toPojo())

  fun updateRealmAttributes(realmId: String, name: String, values: List<String>) {
    realmAttributeRepository.upsertRealmAttributes(realmId, name, values)
  }

  fun getAttributesByRealmId(realmId: String) =
    realmAttributeRepository.fetchByRealmId(realmId).map { it.toDomain() }

  fun getAttributeByRealmIdAndName(realmId: String, name: String): String? {
    val attributes = realmAttributeRepository.fetchByRealmIdAndName(realmId, name).map { it.toDomain() }

    // TODO: find out can here be several attributes or not
    if (attributes.size > 1) {
      logger.warn("Found more than one attribute found for realmId: $realmId, name: $name")
    }

    return attributes.firstOrNull()?.value
  }

  fun removeRealmAttributes(realmId: String, name: String) =
    realmAttributeRepository.deleteByRealmIdAndName(realmId, name)


  fun setAttribute(realmId: String, name: String, value: String): Unit = dsl.transactionResult { ctx ->
    val attributes = realmAttributeRepository.fetchByRealmIdAndName(ctx.dsl(), realmId, name)

    // TODO: find out can here be several attributes or not
    if(attributes.size > 1) {
      logger.warn("Found more than one attribute found for realmId: $realmId, name: $name")
    }

    if (attributes.isEmpty()) {
      realmAttributeRepository.insert(RealmAttributes(generateId(), realmId, name, value))
    } else {
      realmAttributeRepository.update(attributes.map { it.copy(value = value) })
    }
  }
}
