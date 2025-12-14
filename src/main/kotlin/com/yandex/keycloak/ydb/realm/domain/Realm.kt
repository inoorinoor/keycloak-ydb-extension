package com.yandex.keycloak.ydb.realm.domain

data class Realm(
  val id: String,
  val name: String,

  /**
   * decided to store otp policy data here as it is in postgres
   * cassandra had this fields in attributes, but they saved them there as serialized json
   *
   * I don't know why, but otpPolicyIsCodeReusable in postgres is stored in realm_attributes.
   * I saved it in realm model just to data was more consistent
   */
  val otpPolicyType: String = OTP_POLICY_TYPE,
  val otpPolicyAlgorithm: String = OTP_POLICY_ALGORITHM,
  val otpPolicyInitialCounter: Int = OTP_POLICY_INITIAL_COUNTER,
  val otpPolicyDigits: Int = OTP_POLICY_DIGITS,
  val otpPolicyLookAheadWindow: Int = OTP_POLICY_LOOK_AHEAD_WINDOW,
  val otpPolicyPeriod: Int = OTP_POLICY_PERIOD,
  val otpPolicyIsCodeReusable: Boolean = OTP_POLICY_IS_CODE_REUSABLE
) {
  private companion object {
    const val OTP_POLICY_INITIAL_COUNTER = 0
    const val OTP_POLICY_LOOK_AHEAD_WINDOW = 1
    const val OTP_POLICY_PERIOD = 30
    const val OTP_POLICY_DIGITS = 6
    const val OTP_POLICY_ALGORITHM = "HmacSHA1"
    const val OTP_POLICY_TYPE = "totp"
    const val OTP_POLICY_IS_CODE_REUSABLE = false
  }
}
