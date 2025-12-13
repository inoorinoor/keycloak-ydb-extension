package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.tables.daos.RealmsDao
import jooq.generated.default_schema.tables.pojos.Realms
import jooq.generated.default_schema.tables.references.REALMS
import org.jooq.DSLContext
import tech.ydb.jooq.YdbDSLContext

@Suppress("kotlin:S6518")
class RealmRepository(private val dsl: YdbDSLContext) : RealmsDao(dsl.configuration()) {
  fun fetchOneById(txDsl: DSLContext, id: String): Realms? =
    txDsl.selectFrom(REALMS)
      .where(REALMS.ID.eq(id))
      .fetchOne(mapper())

  fun deleteById(txDsl: DSLContext, id: String) =
    txDsl.deleteFrom(REALMS)
      .where(REALMS.ID.eq(id))
      .execute() > 0
}