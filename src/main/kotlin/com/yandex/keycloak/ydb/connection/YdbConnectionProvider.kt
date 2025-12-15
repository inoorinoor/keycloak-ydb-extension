package com.yandex.keycloak.ydb.connection

import jakarta.persistence.EntityManager
import org.keycloak.provider.Provider
import tech.ydb.jooq.YdbDSLContext

interface YdbConnectionProvider : Provider {
  val ydbDSLContext: YdbDSLContext
  val entityManager: EntityManager
}
