package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.Tables.REALMS
import jooq.generated.default_schema.tables.pojos.Realms
import org.jooq.DSLContext
import tech.ydb.jooq.YdbDSLContext

@Suppress("kotlin:S6518")
class RealmRepository(private val dsl: YdbDSLContext) {
  fun fetchOneById(txDsl: DSLContext, id: String): Realms? {
    TODO()
//    return txDsl.selectFrom(REALMS)
//      .where(REALMS.ID.eq(id))
//      .fetchOne(mapper())
  }

  fun deleteById(txDsl: DSLContext, id: String) =
    txDsl.deleteFrom(REALMS)
      .where(REALMS.ID.eq(id))
      .execute() > 0

  fun insert(toPojo: Realms) {
    TODO()
  }
  fun fetchByName(name: String): List<Realms> {
    TODO()
  }
  fun findAll(): List<Realms> {
    TODO()
  }
  fun fetchOneById(id: String): Realms? {
    TODO()
  }

  fun update(toPojo: Realms) {
    TODO()
  }
}
