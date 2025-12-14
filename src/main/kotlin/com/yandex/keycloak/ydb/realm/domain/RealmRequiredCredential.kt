package com.yandex.keycloak.ydb.realm.domain

data class RealmRequiredCredential(
    val id: String,
    val type: String,
    val formLabel: String?,
    val input: Boolean,
    val secret: Boolean,
    val realmId: String
)