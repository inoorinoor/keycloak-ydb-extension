package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.Tables.REALM
import jooq.generated.default_schema.tables.pojos.Realm
import org.jooq.DSLContext
import tech.ydb.jooq.YdbDSLContext

@Suppress("kotlin:S6518")
class RealmRepository(private val dsl: YdbDSLContext) {
  fun fetchOneById(txDsl: DSLContext, id: String): Realm? {
    TODO()
//    return txDsl.selectFrom(REALM)
//      .where(REALM.ID.eq(id))
//      .fetchOne(mapper())
  }

  fun deleteById(txDsl: DSLContext, id: String) =
    txDsl.deleteFrom(REALM)
      .where(REALM.ID.eq(id))
      .execute() > 0

  fun insert(toPojo: Realm) {
    TODO()
  }
  fun fetchByName(name: String): List<Realm> {
    TODO()
  }
  fun findAll(): List<Realm> {
    TODO()
  }
  fun fetchOneById(id: String): Realm? {
    TODO()
  }

  fun update(toPojo: Realm) {
    TODO()
  }
}
