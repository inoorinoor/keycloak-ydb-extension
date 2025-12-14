package com.yandex.keycloak.ydb.realm

import com.yandex.keycloak.ydb.realm.domain.Realm
import com.yandex.keycloak.ydb.realm.service.RealmService
import org.keycloak.models.KeycloakSession
import org.keycloak.models.ModelDuplicateException
import org.keycloak.models.RealmModel
import org.keycloak.models.RealmProvider
import org.keycloak.models.utils.KeycloakModelUtils.generateId
import java.util.stream.Stream

class YdbRealmProvider(
  private val session: KeycloakSession,
  private val realmService: RealmService,
) : RealmProvider {

  override fun createRealm(name: String?): RealmModel = createRealm(generateId(), name)

  override fun createRealm(id: String?, name: String?): RealmModel {
    requireNotNull(id) { "Realm id cannot be null" }
    requireNotNull(name) { "Realm name cannot be null" }

    if (getRealmByName(name) != null) {
      throw ModelDuplicateException("Realm with given name exists: $name")
    }

    val existingRealm = realmService.getRealmById(id)
    if (existingRealm != null) {
      throw ModelDuplicateException("Realm with id='$id' exists")
    }

    val entity = Realm(id = id, name = name)
    realmService.createRealm(entity)

    val adapter = YdbRealmAdapter(entity, session, realmService)

    return adapter
  }

  override fun getRealm(id: String?): RealmModel? {
    if (id == null) return null

    val entity = realmService.getRealmById(id) ?: return null

    return YdbRealmAdapter(entity, session, realmService)
  }

  override fun getRealmByName(name: String?): RealmModel? {
    if (name == null) return null

    val entity = realmService.getRealmByName(name) ?: return null

    return YdbRealmAdapter(entity, session, realmService)
  }

  override fun getRealmsStream(): Stream<RealmModel> = realmService.getAllRealms()
    .map { entity -> YdbRealmAdapter(entity, session, realmService) as RealmModel }
    .stream()

  override fun getRealmsWithProviderTypeStream(type: Class<*>?): Stream<RealmModel> {
    if (type == null) return Stream.empty()

    return getRealmsStream().filter { realm ->
      realm.componentsStream
        .anyMatch { component ->
          component.providerType == type.name
        }
    }
  }

  override fun removeRealm(id: String?): Boolean {
    if (id == null || getRealm(id) == null) return false

    return realmService.removeRealm(id)
  }

  override fun removeExpiredClientInitialAccess() {
    // TODO: after adding client_initial_access
  }

  override fun saveLocalizationText(
    realm: RealmModel?,
    locale: String?,
    key: String?,
    text: String?
  ) {
    if (realm == null || locale == null || key == null || text == null) return

    val currentTexts = realm.getRealmLocalizationTextsByLocale(locale)?.toMutableMap()
      ?: mutableMapOf()

    currentTexts[key] = text

    realm.createOrUpdateRealmLocalizationTexts(locale, currentTexts)
  }

  override fun saveLocalizationTexts(
    realm: RealmModel?,
    locale: String?,
    localizationTexts: Map<String?, String?>?
  ) {
    if (realm == null || locale == null || localizationTexts == null) return

    val texts = localizationTexts.mapNotNull { (key, value) ->
      (key to value).takeIf { key != null && value != null }
    }.toMap()

    realm.createOrUpdateRealmLocalizationTexts(locale, texts)
  }

  override fun updateLocalizationText(
    realm: RealmModel?,
    locale: String?,
    key: String?,
    text: String?
  ): Boolean {
    if (realm == null || locale == null || key == null || text == null) return false

    val currentTexts = realm.getRealmLocalizationTextsByLocale(locale) ?: return false

    if (!currentTexts.containsKey(key)) return false

    val updatedTexts = currentTexts - key

    realm.createOrUpdateRealmLocalizationTexts(locale, updatedTexts)

    return true
  }

  override fun deleteLocalizationTextsByLocale(
    realm: RealmModel?,
    locale: String?
  ): Boolean {
    if (realm == null || locale == null) return false

    return realm.removeRealmLocalizationTexts(locale)
  }

  override fun deleteLocalizationText(
    realm: RealmModel?,
    locale: String?,
    key: String?
  ): Boolean {
    if (realm == null || locale == null || key == null) return false

    val currentTexts = realm.getRealmLocalizationTextsByLocale(locale) ?: return false

    if (!currentTexts.containsKey(key)) return false

    val updatedTexts = currentTexts - key

    return if (updatedTexts.isEmpty()) {
      realm.removeRealmLocalizationTexts(locale)
    } else {
      realm.createOrUpdateRealmLocalizationTexts(locale, updatedTexts)
      true
    }
  }

  override fun getLocalizationTextsById(
    realm: RealmModel?,
    locale: String?,
    key: String?
  ): String? {
    if (realm == null || locale == null || key == null) return null

    return realm.getRealmLocalizationTextsByLocale(locale)?.get(key)
  }

  override fun close() {
    // no operations
  }
}
