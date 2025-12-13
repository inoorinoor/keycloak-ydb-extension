package com.yandex.keycloak.ydb.realm.domain

data class RealmAttribute(
  val id: String,
  val name: String,
  val realmId: String,
  val value: String?,
)