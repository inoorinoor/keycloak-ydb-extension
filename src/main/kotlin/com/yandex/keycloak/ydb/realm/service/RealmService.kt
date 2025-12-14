package com.yandex.keycloak.ydb.realm.service

import com.yandex.keycloak.ydb.realm.RealmMapper.toDomain
import com.yandex.keycloak.ydb.realm.RealmMapper.toPojo
import com.yandex.keycloak.ydb.realm.domain.Realm
import com.yandex.keycloak.ydb.realm.domain.RealmRequiredCredential
import com.yandex.keycloak.ydb.realm.persistense.RealmAttributeRepository
import com.yandex.keycloak.ydb.realm.persistense.RealmRepository
import com.yandex.keycloak.ydb.realm.persistense.RealmRequiredCredentialRepository
import jooq.generated.default_schema.tables.pojos.RealmAttributes
import org.jboss.logging.Logger
import org.jooq.DSLContext
import org.keycloak.models.RequiredCredentialModel
import org.keycloak.models.WebAuthnPolicy
import org.keycloak.models.jpa.entities.RealmAttributes.*
import org.keycloak.models.utils.KeycloakModelUtils.generateId
import tech.ydb.jooq.YdbDSLContext
import java.util.stream.Stream


class RealmService(
  private val dsl: YdbDSLContext
) {
  private val realmRepository = RealmRepository(dsl)
  private val realmAttributeRepository = RealmAttributeRepository(dsl)
  private val realmRequiredCredentialRepository = RealmRequiredCredentialRepository(dsl)

  private val logger = Logger.getLogger(RealmService::class.java.name)

  fun getRealmById(id: String) = realmRepository.fetchOneById(id)?.toDomain()

  fun createRealm(entity: Realm) = realmRepository.insert(entity.toPojo())

  fun getRealmByName(name: String) = realmRepository.fetchByName(name).firstOrNull()?.toDomain()

  fun getAllRealms(): List<Realm> = realmRepository.findAll().map { it.toDomain() }

  fun removeRealm(id: String): Boolean = dsl.transactionResult { ctx ->
    realmRepository.fetchOneById(ctx.dsl(), id) ?: return@transactionResult false

    realmAttributeRepository.deleteByRealmId(ctx.dsl(), id)

    realmRepository.deleteById(ctx.dsl(), id)
  }

  fun updateRealm(entity: Realm) = realmRepository.update(entity.toPojo())

  fun updateRealmAttributes(realmId: String, name: String, values: List<String>) {
    realmAttributeRepository.upsertRealmAttributes(realmId, name, values)
  }

  fun getAttributesByRealmId(realmId: String) =
    realmAttributeRepository.fetchByRealmId(realmId).map { it.toDomain() }

  // TODO: ask is it good to pass context like this
  fun getAttributeByRealmIdAndName(realmId: String, name: String): String? =
    getAttributeByRealmIdAndName(dsl, realmId, name)

  fun getAttributeByRealmIdAndName(txDsl: DSLContext, realmId: String, name: String): String? {
    val attributes = realmAttributeRepository.fetchByRealmIdAndName(txDsl, realmId, name).map { it.toDomain() }

    // TODO: find out can here be several attributes or not
    if (attributes.size > 1) {
      logger.warn("Found more than one attribute found for realmId: $realmId, name: $name")
    }

    return attributes.firstOrNull()?.value
  }

  fun removeRealmAttributes(realmId: String, name: String) =
    realmAttributeRepository.deleteByRealmIdAndName(realmId, name)

  fun setAttribute(realmId: String, name: String, value: String): Unit =
    setAttribute(dsl, realmId, name, value)

  fun setAttribute(txDsl: DSLContext, realmId: String, name: String, value: String): Unit = txDsl.transactionResult { ctx ->
    val attributes = realmAttributeRepository.fetchByRealmIdAndName(ctx.dsl(), realmId, name)

    // TODO: find out can here be several attributes or not
    if (attributes.size > 1) {
      logger.warn("Found more than one attribute found for realmId: $realmId, name: $name")
    }

    if (attributes.isEmpty()) {
      realmAttributeRepository.insert(RealmAttributes(generateId(), realmId, name, value))
    } else {
      realmAttributeRepository.update(attributes.map { it.copy(value = value) })
    }
  }

  fun getRequiredCredentialsByRealmId(realmId: String): Stream<RequiredCredentialModel> =
    realmRequiredCredentialRepository.fetchByRealmId(realmId).map { it.toDomain() }.stream()

  fun addRequiredCredential(model: RealmRequiredCredential) =
    realmRequiredCredentialRepository.insert(model.toPojo())

  fun getWebAuthnPolicy(
    realmId: String,
    defaultConfig: WebAuthnPolicy,
    prefix: String,
  ): WebAuthnPolicy = dsl.transactionResult { ctx ->
    fun getStringField(attributeName: String, defaultValue: String): String = getAttributeByRealmIdAndName(
      txDsl = ctx.dsl(),
      realmId = realmId,
      name = attributeName + prefix
    ).takeUnless { it.isNullOrEmpty() } ?: defaultValue

    fun getIntField(attributeName: String, defaultValue: Int): Int = getAttributeByRealmIdAndName(
      txDsl = ctx.dsl(),
      realmId = realmId,
      name = attributeName + prefix
    )?.toInt() ?: defaultValue

    fun getListField(attributeName: String, defaultValue: List<String>): List<String> = getAttributeByRealmIdAndName(
      txDsl = ctx.dsl(),
      realmId = realmId,
      name = attributeName + prefix,
    ).takeUnless { it.isNullOrEmpty() }
      ?.split(",")
      ?: defaultValue

    fun getBooleanField(attributeName: String, defaultValue: Boolean): Boolean = getAttributeByRealmIdAndName(
      txDsl = ctx.dsl(),
      realmId = realmId,
      name = attributeName + prefix,
    )?.toBoolean() ?: defaultValue

    WebAuthnPolicy().apply {
      rpEntityName = getStringField(
        WEBAUTHN_POLICY_RP_ENTITY_NAME,
        defaultConfig.rpEntityName
      )

      signatureAlgorithm = getListField(
        WEBAUTHN_POLICY_SIGNATURE_ALGORITHMS,
        defaultConfig.signatureAlgorithm
      )

      rpId = getStringField(WEBAUTHN_POLICY_RP_ID, defaultConfig.rpId)

      attestationConveyancePreference = getStringField(
        WEBAUTHN_POLICY_ATTESTATION_CONVEYANCE_PREFERENCE,
        defaultConfig.attestationConveyancePreference
      )

      authenticatorAttachment = getStringField(
        WEBAUTHN_POLICY_AUTHENTICATOR_ATTACHMENT,
        defaultConfig.authenticatorAttachment
      )

      requireResidentKey = getAttributeByRealmIdAndName(
        WEBAUTHN_POLICY_REQUIRE_RESIDENT_KEY,
        defaultConfig.requireResidentKey,
      )

      userVerificationRequirement = getAttributeByRealmIdAndName(
        WEBAUTHN_POLICY_USER_VERIFICATION_REQUIREMENT,
        defaultConfig.userVerificationRequirement,
      )

      createTimeout = getIntField(
        WEBAUTHN_POLICY_CREATE_TIMEOUT,
        defaultConfig.createTimeout
      )

      isAvoidSameAuthenticatorRegister = getBooleanField(
        WEBAUTHN_POLICY_AVOID_SAME_AUTHENTICATOR_REGISTER,
      defaultConfig.isAvoidSameAuthenticatorRegister
      )

      acceptableAaguids = getListField(
        WEBAUTHN_POLICY_ACCEPTABLE_AAGUIDS,
        defaultConfig.acceptableAaguids
      )

      extraOrigins = getListField(
        WEBAUTHN_POLICY_EXTRA_ORIGINS,
        defaultConfig.extraOrigins
      )

      isPasskeysEnabled = getBooleanField(
        WEBAUTHN_POLICY_PASSKEYS_ENABLED,
        defaultConfig.isPasskeysEnabled
      )
    }
  }

  fun setWebAuthnPolicy(realmId: String, policy: WebAuthnPolicy, prefix: String) {
    dsl.transactionResult { ctx ->
      fun setAttribute(attributeName: String, value: String) {
        setAttribute(ctx.dsl(), realmId, attributeName + prefix, value)
      }

      fun setAttribute(attributeName: String, value: Int) {
        setAttribute(ctx.dsl(), realmId, attributeName + prefix, value.toString())
      }

      fun setAttribute(attributeName: String, value: Boolean) {
        setAttribute(ctx.dsl(), realmId, attributeName + prefix, value.toString())
      }

      fun setAttribute(attributeName: String, value: List<String>) {
        setAttribute(ctx.dsl(), realmId, attributeName + prefix, value.joinToString(","))
      }

      fun setOrRemoveAttribute(attributeName: String, value: List<String>?) {
        if (!value.isNullOrEmpty()) {
          setAttribute(attributeName, value)
        } else {
          realmAttributeRepository.deleteByRealmIdAndName(ctx.dsl(), realmId, attributeName + prefix)
        }
      }

      fun setOrRemoveAttribute(attributeName: String, value: Boolean?) {
        if (value != null) {
          setAttribute(attributeName, value)
        } else {
          realmAttributeRepository.deleteByRealmIdAndName(ctx.dsl(), realmId, attributeName + prefix)
        }
      }

      setAttribute(WEBAUTHN_POLICY_RP_ENTITY_NAME, policy.rpEntityName)

      setAttribute(WEBAUTHN_POLICY_SIGNATURE_ALGORITHMS, policy.signatureAlgorithm)

      setAttribute(WEBAUTHN_POLICY_RP_ID, policy.rpId)

      setAttribute(WEBAUTHN_POLICY_ATTESTATION_CONVEYANCE_PREFERENCE, policy.attestationConveyancePreference)

      setAttribute(WEBAUTHN_POLICY_AUTHENTICATOR_ATTACHMENT, policy.authenticatorAttachment)

      setAttribute(WEBAUTHN_POLICY_REQUIRE_RESIDENT_KEY, policy.requireResidentKey)

      setAttribute(WEBAUTHN_POLICY_USER_VERIFICATION_REQUIREMENT, policy.userVerificationRequirement)

      setAttribute(WEBAUTHN_POLICY_CREATE_TIMEOUT, policy.createTimeout)

      setAttribute(WEBAUTHN_POLICY_AVOID_SAME_AUTHENTICATOR_REGISTER, policy.isAvoidSameAuthenticatorRegister)

      setOrRemoveAttribute(WEBAUTHN_POLICY_ACCEPTABLE_AAGUIDS, policy.acceptableAaguids)

      setOrRemoveAttribute(WEBAUTHN_POLICY_EXTRA_ORIGINS, policy.extraOrigins)

      setOrRemoveAttribute(WEBAUTHN_POLICY_PASSKEYS_ENABLED, policy.isPasskeysEnabled)
    }
  }
}
