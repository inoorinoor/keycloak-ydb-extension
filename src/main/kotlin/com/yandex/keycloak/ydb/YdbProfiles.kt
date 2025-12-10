package com.yandex.keycloak.ydb

object YdbProfiles {
  private const val ENV_YDB_PROFILE_ENABLED: String = "KC_COMMUNITY_DATASTORE_YDB_ENABLED"
  private const val PROP_YDB_PROFILE_ENABLED = "kc.community.datastore.ydb.enabled"

  val IS_YDB_PROFILE_ENABLED: Boolean = System.getenv(ENV_YDB_PROFILE_ENABLED).toBoolean()
    || System.getProperty(PROP_YDB_PROFILE_ENABLED).toBoolean()
}