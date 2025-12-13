package com.yandex.keycloak.ydb.realm.service

import com.yandex.keycloak.ydb.realm.RealmMapper.toDomain
import com.yandex.keycloak.ydb.realm.RealmMapper.toPojo
import com.yandex.keycloak.ydb.realm.domain.Realm
import com.yandex.keycloak.ydb.realm.persistense.RealmAttributeRepository
import com.yandex.keycloak.ydb.realm.persistense.RealmRepository
import tech.ydb.jooq.YdbDSLContext


class RealmService(
  private val dsl: YdbDSLContext
) {
  private val realmRepository = RealmRepository(dsl)
  private val realmAttributeRepository = RealmAttributeRepository(dsl)

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
}