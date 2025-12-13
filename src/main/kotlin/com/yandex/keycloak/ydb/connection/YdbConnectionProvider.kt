package com.yandex.keycloak.ydb.connection

import org.keycloak.provider.Provider
import tech.ydb.jooq.YdbDSLContext

interface YdbConnectionProvider : Provider {
  val ydbDSLContext: YdbDSLContext
}
