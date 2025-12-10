package com.yandex.keycloak.ydb.connection

import org.keycloak.provider.Provider
import tech.ydb.jooq.YdbDSLContext
import java.sql.Connection

interface YdbConnectionProvider : Provider {
  val connection: Connection

  val ydbDSLContext: YdbDSLContext
}
