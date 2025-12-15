package com.yandex.keycloak.ydb.realm

import com.yandex.keycloak.ydb.realm.domain.Realm
import com.yandex.keycloak.ydb.realm.domain.RealmAttribute
import com.yandex.keycloak.ydb.realm.domain.RealmRequiredCredential
import jooq.generated.default_schema.tables.pojos.RealmAttributes
import jooq.generated.default_schema.tables.pojos.RealmRequiredCredentials
import jooq.generated.default_schema.tables.pojos.Realms
import org.keycloak.models.RequiredCredentialModel

object RealmMapper {
  fun Realms.toDomain(): Realm = Realm(
    id = id,
    name = name,
    otpPolicyType = otpPolicyType,
    otpPolicyAlgorithm = otpPolicyAlgorithm,
    otpPolicyInitialCounter = otpPolicyInitialCounter,
    otpPolicyDigits = otpPolicyDigits,
    otpPolicyLookAheadWindow = otpPolicyLookAheadWindow,
    otpPolicyPeriod = otpPolicyPeriod,
    otpPolicyIsCodeReusable = otpPolicyIsCodeReusable,
  )

  fun Realm.toPojo(): Realms = let {
    Realms().apply {
      id = it.id
      name = it.name
      otpPolicyType = it.otpPolicyType
      otpPolicyAlgorithm = it.otpPolicyAlgorithm
      otpPolicyInitialCounter = it.otpPolicyInitialCounter
      otpPolicyDigits = it.otpPolicyDigits
      otpPolicyLookAheadWindow = it.otpPolicyLookAheadWindow
      otpPolicyPeriod = it.otpPolicyPeriod
      otpPolicyIsCodeReusable = it.otpPolicyIsCodeReusable
    }
  }

  fun RealmAttributes.toDomain(): RealmAttribute = RealmAttribute(
    id = id,
    name = name,
    realmId = realmId,
    value = value,
  )

  fun RealmRequiredCredentials.toDomain(): RequiredCredentialModel = let {
    RequiredCredentialModel().apply {
      this.formLabel = it.formLabel
      this.type = it.type
      this.isSecret = it.secret
      this.isInput = it.input
    }
  }

  fun RequiredCredentialModel.toDomain(id: String, realmId: String): RealmRequiredCredential = RealmRequiredCredential(
    id = id,
    type = type,
    formLabel = formLabel,
    input = isInput,
    secret = isSecret,
    realmId = realmId,
  )

  fun RealmRequiredCredential.toPojo(): RealmRequiredCredentials = let {
    RealmRequiredCredentials().apply {
      id = it.id
      type = it.type
      formLabel = it.formLabel
      input = it.input
      secret = it.secret
      realmId = it.realmId
    }
  }
}
