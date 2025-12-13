package com.yandex.keycloak.ydb.realm

import org.keycloak.models.RealmModel
import org.keycloak.models.RealmProvider
import java.util.stream.Stream

class YdbRealmProvider(): RealmProvider {
  override fun createRealm(p0: String?): RealmModel? {
    TODO("Not yet implemented")
  }

  override fun createRealm(p0: String?, p1: String?): RealmModel? {
    TODO("Not yet implemented")
  }

  override fun getRealm(p0: String?): RealmModel? {
    TODO("Not yet implemented")
  }

  override fun getRealmByName(p0: String?): RealmModel? {
    TODO("Not yet implemented")
  }

  override fun getRealmsStream(): Stream<RealmModel?>? {
    TODO("Not yet implemented")
  }

  override fun getRealmsWithProviderTypeStream(p0: Class<*>?): Stream<RealmModel?>? {
    TODO("Not yet implemented")
  }

  override fun removeRealm(p0: String?): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeExpiredClientInitialAccess() {
    TODO("Not yet implemented")
  }

  override fun saveLocalizationText(
    p0: RealmModel?,
    p1: String?,
    p2: String?,
    p3: String?
  ) {
    TODO("Not yet implemented")
  }

  override fun saveLocalizationTexts(
    p0: RealmModel?,
    p1: String?,
    p2: Map<String?, String?>?
  ) {
    TODO("Not yet implemented")
  }

  override fun updateLocalizationText(
    p0: RealmModel?,
    p1: String?,
    p2: String?,
    p3: String?
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun deleteLocalizationTextsByLocale(
    p0: RealmModel?,
    p1: String?
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun deleteLocalizationText(
    p0: RealmModel?,
    p1: String?,
    p2: String?
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun getLocalizationTextsById(
    p0: RealmModel?,
    p1: String?,
    p2: String?
  ): String? {
    TODO("Not yet implemented")
  }

  override fun close() {
    TODO("Not yet implemented")
  }
}