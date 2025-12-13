package com.yandex.keycloak.ydb.util

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

fun hikariDataSource(
  jdbcUrl: String?,
  poolSize: Int,
  connectionTimeout: Long,
): HikariDataSource = HikariDataSource(hikariConfig(jdbcUrl, poolSize, connectionTimeout))

fun hikariConfig(
  jdbcUrl: String?,
  poolSize: Int,
  connectionTimeout: Long
): HikariConfig = HikariConfig().apply {// todo revise
  this.jdbcUrl = jdbcUrl
  this.driverClassName = "tech.ydb.jdbc.YdbDriver"
  this.maximumPoolSize = poolSize
  this.connectionTimeout = connectionTimeout
  this.poolName = "YDB-HikariPool"
  this.isAutoCommit = false // todo revise
}