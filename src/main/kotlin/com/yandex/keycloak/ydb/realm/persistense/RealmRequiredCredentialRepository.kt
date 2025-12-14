package com.yandex.keycloak.ydb.realm.persistense

import jooq.generated.default_schema.tables.daos.RealmRequiredCredentialsDao
import tech.ydb.jooq.YdbDSLContext


@Suppress("kotlin:S6518")
class RealmRequiredCredentialRepository(
  private val dsl: YdbDSLContext
) : RealmRequiredCredentialsDao(dsl.configuration())