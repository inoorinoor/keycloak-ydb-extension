package com.yandex.keycloak.ydb.realm

import com.yandex.keycloak.ydb.common.AttributePrefixes.INTERNAL_ATTRIBUTE_PREFIX
import com.yandex.keycloak.ydb.common.AttributePrefixes.READONLY_ATTRIBUTE_PREFIX
import com.yandex.keycloak.ydb.realm.domain.Realm
import com.yandex.keycloak.ydb.realm.service.RealmService
import org.keycloak.common.enums.SslRequired
import org.keycloak.component.ComponentModel
import org.keycloak.models.*
import org.keycloak.representations.idm.RealmRepresentation
import java.util.stream.Stream

class YdbRealmAdapter(
  private var realm: Realm,
  private val session: KeycloakSession,
  private val realmService: RealmService,
) : RealmModel {
  override fun getId() = realm.id

  override fun getName(): String = realm.id

  override fun setName(name: String?) {
    if (name != null && name != realm.name) {
      realm = realm.copy(name = name)

      realmService.updateRealm(realm)
    }
  }

  override fun getDisplayName(): String? = getAttribute(DISPLAY_NAME)

  override fun setDisplayName(displayName: String?) = setAttribute(DISPLAY_NAME, displayName)

  override fun getDisplayNameHtml(): String? = getAttribute(DISPLAY_NAME_HTML)

  override fun setDisplayNameHtml(displayNameHtml: String?) = setAttribute(DISPLAY_NAME_HTML, displayNameHtml)

  override fun isEnabled(): Boolean = getAttribute(IS_ENABLED, false)

  override fun setEnabled(enabled: Boolean) = setAttribute(IS_ENABLED, enabled)

  override fun getSslRequired(): SslRequired? = getAttribute(SSL_REQUIRED)?.let { SslRequired.valueOf(it) }

  override fun setSslRequired(sslRequired: SslRequired?) = setAttribute(SSL_REQUIRED, sslRequired?.name)

  override fun isRegistrationAllowed(): Boolean = getAttribute(IS_REGISTRATION_ALLOWED, false)

  override fun setRegistrationAllowed(registrationAllowed: Boolean) =
    setAttribute(IS_REGISTRATION_ALLOWED, registrationAllowed)

  override fun isRegistrationEmailAsUsername(): Boolean = getAttribute(IS_REGISTRATION_EMAIL_AS_USERNAME, false)

  override fun setRegistrationEmailAsUsername(isRegistrationEmailAsUsername: Boolean) =
    setAttribute(IS_REGISTRATION_EMAIL_AS_USERNAME, isRegistrationEmailAsUsername)

  override fun isRememberMe(): Boolean = getAttribute(IS_REMEMBER_ME, false)

  override fun setRememberMe(rememberMe: Boolean) = setAttribute(IS_REMEMBER_ME, rememberMe)

  override fun isEditUsernameAllowed(): Boolean = getAttribute(IS_EDIT_USERNAME_ALLOWED, false)

  override fun setEditUsernameAllowed(isEditUsernameAllowed: Boolean) =
    setAttribute(IS_EDIT_USERNAME_ALLOWED, isEditUsernameAllowed)

  override fun isUserManagedAccessAllowed(): Boolean = getAttribute(IS_USER_MANAGED_ACCESS_ALLOWED, false)

  override fun setUserManagedAccessAllowed(isUserManagedAccessAllowed: Boolean) =
    setAttribute(IS_USER_MANAGED_ACCESS_ALLOWED, isUserManagedAccessAllowed)

  override fun isOrganizationsEnabled(): Boolean = getAttribute(IS_ORGANIZATIONS_ENABLED, false)

  override fun setOrganizationsEnabled(organizationsEnabled: Boolean) =
    setAttribute(IS_ORGANIZATIONS_ENABLED, organizationsEnabled)

  override fun isAdminPermissionsEnabled(): Boolean = getAttribute(IS_ADMIN_PERMISSIONS_ENABLED, false)

  override fun setAdminPermissionsEnabled(adminPermissionsEnabled: Boolean) =
    setAttribute(IS_ADMIN_PERMISSIONS_ENABLED, adminPermissionsEnabled)

  override fun isVerifiableCredentialsEnabled(): Boolean = getAttribute(IS_VERIFIABLE_CREDENTIALS_ENABLED, false)

  override fun setVerifiableCredentialsEnabled(verifiableCredentialsEnabled: Boolean) =
    setAttribute(IS_VERIFIABLE_CREDENTIALS_ENABLED, verifiableCredentialsEnabled)

  override fun setAttribute(name: String?, value: String?) {
    if (name != null && value != null) {
      realmService.setAttribute(realm.id, name, value)
    }
  }

  override fun removeAttribute(name: String?) {
    if (name != null) {
      realmService.removeRealmAttributes(realm.id, name)
    }
  }

  override fun getAttribute(name: String?): String? {
    if(name == null) return null

    return realmService.getAttributeByRealmIdAndName(realm.id, name)
  }

  override fun getAttributes(): Map<String, String> =
    realmService.getAttributesByRealmId(realm.id).mapNotNull {
      val value = it.value ?: return@mapNotNull null

      name to value
    }.toMap()


  override fun isBruteForceProtected(): Boolean = getAttribute(IS_BRUTE_FORCE_PROTECTED, false)

  override fun setBruteForceProtected(bruteForceProtected: Boolean) =
    setAttribute(IS_BRUTE_FORCE_PROTECTED, bruteForceProtected)

  override fun isPermanentLockout(): Boolean = getAttribute(IS_PERMANENT_LOCKOUT, false)

  override fun setPermanentLockout(permanentLockout: Boolean) =
    setAttribute(IS_PERMANENT_LOCKOUT, permanentLockout)

  override fun getMaxTemporaryLockouts(): Int = getAttribute(MAX_TEMPORARY_LOCKOUTS, 0)

  override fun setMaxTemporaryLockouts(maxTemporaryLockouts: Int) =
    setAttribute(MAX_TEMPORARY_LOCKOUTS, maxTemporaryLockouts)

  override fun getBruteForceStrategy(): RealmRepresentation.BruteForceStrategy? {
    val strategy = getAttribute(BRUTE_FORCE_STRATEGY)
    return if (strategy == null) RealmRepresentation.BruteForceStrategy.MULTIPLE
    else RealmRepresentation.BruteForceStrategy.valueOf(strategy)
  }

  override fun setBruteForceStrategy(bruteForceStrategy: RealmRepresentation.BruteForceStrategy?) =
    setAttribute(BRUTE_FORCE_STRATEGY, bruteForceStrategy?.name)

  override fun getMaxFailureWaitSeconds(): Int = getAttribute(MAX_FAILURE_WAIT_SECONDS, 0)

  override fun setMaxFailureWaitSeconds(maxFailureWaitSeconds: Int) =
    setAttribute(MAX_FAILURE_WAIT_SECONDS, maxFailureWaitSeconds)

  override fun getWaitIncrementSeconds(): Int = getAttribute(WAIT_INCREMENT_SECONDS, 0)

  override fun setWaitIncrementSeconds(waitIncrementSeconds: Int) =
    setAttribute(WAIT_INCREMENT_SECONDS, waitIncrementSeconds)

  override fun getMinimumQuickLoginWaitSeconds(): Int = getAttribute(MINIMUM_QUICK_LOGIN_WAIT_SECONDS, 0)

  override fun setMinimumQuickLoginWaitSeconds(minimumQuickLoginWaitSeconds: Int) =
    setAttribute(MINIMUM_QUICK_LOGIN_WAIT_SECONDS, minimumQuickLoginWaitSeconds)

  override fun getQuickLoginCheckMilliSeconds(): Long = getAttribute(QUICK_LOGIN_CHECK_MILLI_SECONDS, 0L)

  override fun setQuickLoginCheckMilliSeconds(quickLoginCheckMilliSeconds: Long) =
    setAttribute(QUICK_LOGIN_CHECK_MILLI_SECONDS, quickLoginCheckMilliSeconds)

  override fun getMaxDeltaTimeSeconds(): Int = getAttribute(MAX_DELTA_TIME_SECONDS, 0)

  override fun setMaxDeltaTimeSeconds(maxDeltaTimeSeconds: Int) =
    setAttribute(MAX_DELTA_TIME_SECONDS, maxDeltaTimeSeconds)

  override fun getFailureFactor(): Int = getAttribute(FAILURE_FACTOR, 0)

  override fun setFailureFactor(failureFactor: Int) =
    setAttribute(FAILURE_FACTOR, failureFactor)

  override fun isVerifyEmail(): Boolean = getAttribute(VERIFY_EMAIL, false)

  override fun setVerifyEmail(verifyEmail: Boolean) =
    setAttribute(VERIFY_EMAIL, verifyEmail)

  override fun isLoginWithEmailAllowed(): Boolean = getAttribute(LOGIN_WITH_EMAIL_ALLOWED, false)

  override fun setLoginWithEmailAllowed(loginWithEmailAllowed: Boolean) =
    setAttribute(LOGIN_WITH_EMAIL_ALLOWED, loginWithEmailAllowed)

  override fun isDuplicateEmailsAllowed(): Boolean = getAttribute(IS_DUPLICATE_EMAILS_ALLOWED, false)

  override fun setDuplicateEmailsAllowed(duplicateEmailsAllowed: Boolean) =
    setAttribute(IS_DUPLICATE_EMAILS_ALLOWED, duplicateEmailsAllowed)

  override fun isResetPasswordAllowed(): Boolean = getAttribute(IS_RESET_PASSWORD_ALLOWED, false)

  override fun setResetPasswordAllowed(resetPasswordAllowed: Boolean) =
    setAttribute(IS_RESET_PASSWORD_ALLOWED, resetPasswordAllowed)

  override fun getDefaultSignatureAlgorithm(): String? = getAttribute(DEFAULT_SIG_ALGORITHM)

  override fun setDefaultSignatureAlgorithm(defaultSignatureAlgorithm: String?) =
    setAttribute(DEFAULT_SIG_ALGORITHM, defaultSignatureAlgorithm)

  override fun isRevokeRefreshToken(): Boolean = getAttribute(IS_REVOKE_REFRESH_TOKEN, false)

  override fun setRevokeRefreshToken(revokeRefreshToken: Boolean) =
    setAttribute(IS_REVOKE_REFRESH_TOKEN, revokeRefreshToken)

  override fun getRefreshTokenMaxReuse(): Int = getAttribute(REFRESH_TOKEN_MAX_REUSE, 0)

  override fun setRefreshTokenMaxReuse(refreshTokenMaxReuse: Int) =
    setAttribute(REFRESH_TOKEN_MAX_REUSE, refreshTokenMaxReuse)

  override fun getSsoSessionIdleTimeout(): Int = getAttribute(SSO_SESSION_IDLE_TIMEOUT, 0)

  override fun setSsoSessionIdleTimeout(ssoSessionIdleTimeout: Int) =
    setAttribute(SSO_SESSION_IDLE_TIMEOUT, ssoSessionIdleTimeout)

  override fun getSsoSessionMaxLifespan(): Int = getAttribute(SSO_SESSION_MAX_LIFESPAN, 0)

  override fun setSsoSessionMaxLifespan(ssoSessionMaxLifespan: Int) =
    setAttribute(SSO_SESSION_MAX_LIFESPAN, ssoSessionMaxLifespan)

  override fun getSsoSessionIdleTimeoutRememberMe(): Int = getAttribute(SSO_SESSION_IDLE_TIMEOUT_REMEMBER_ME, 0)

  override fun setSsoSessionIdleTimeoutRememberMe(ssoSessionIdleTimeoutRememberMe: Int) =
    setAttribute(SSO_SESSION_IDLE_TIMEOUT_REMEMBER_ME, ssoSessionIdleTimeoutRememberMe)

  override fun getSsoSessionMaxLifespanRememberMe(): Int = getAttribute(SSO_SESSION_MAX_LIFESPAN_REMEMBER_ME, 0)

  override fun setSsoSessionMaxLifespanRememberMe(ssoSessionMaxLifespanRememberMe: Int) =
    setAttribute(SSO_SESSION_MAX_LIFESPAN_REMEMBER_ME, ssoSessionMaxLifespanRememberMe)

  override fun getOfflineSessionIdleTimeout(): Int = getAttribute(OFFLINE_SESSION_IDLE_TIMEOUT, 0)

  override fun setOfflineSessionIdleTimeout(offlineSessionIdleTimeout: Int) =
    setAttribute(OFFLINE_SESSION_IDLE_TIMEOUT, offlineSessionIdleTimeout)

  override fun getAccessTokenLifespan(): Int = getAttribute(ACCESS_TOKEN_LIFESPAN, 0)

  override fun setAccessTokenLifespan(accessTokenLifespan: Int) =
    setAttribute(ACCESS_TOKEN_LIFESPAN, accessTokenLifespan)

  override fun isOfflineSessionMaxLifespanEnabled(): Boolean =
    getAttribute(IS_OFFLINE_SESSION_MAX_LIFESPAN_ENABLED, false)

  override fun setOfflineSessionMaxLifespanEnabled(offlineSessionMaxLifespanEnabled: Boolean) =
    setAttribute(IS_OFFLINE_SESSION_MAX_LIFESPAN_ENABLED, offlineSessionMaxLifespanEnabled)

  override fun getOfflineSessionMaxLifespan(): Int = getAttribute(OFFLINE_SESSION_MAX_LIFESPAN, 0)

  override fun setOfflineSessionMaxLifespan(offlineSessionMaxLifespan: Int) =
    setAttribute(OFFLINE_SESSION_MAX_LIFESPAN, offlineSessionMaxLifespan)

  override fun getClientSessionIdleTimeout(): Int = getAttribute(CLIENT_SESSION_IDLE_TIMEOUT, 0)

  override fun setClientSessionIdleTimeout(clientSessionIdleTimeout: Int) =
    setAttribute(CLIENT_SESSION_IDLE_TIMEOUT, clientSessionIdleTimeout)

  override fun getClientSessionMaxLifespan(): Int = getAttribute(CLIENT_SESSION_MAX_LIFESPAN, 0)

  override fun setClientSessionMaxLifespan(clientSessionMaxLifespan: Int) =
    setAttribute(CLIENT_SESSION_MAX_LIFESPAN, clientSessionMaxLifespan)

  override fun getClientOfflineSessionIdleTimeout(): Int = getAttribute(CLIENT_OFFLINE_SESSION_IDLE_TIMEOUT, 0)

  override fun setClientOfflineSessionIdleTimeout(clientOfflineSessionIdleTimeout: Int) =
    setAttribute(CLIENT_OFFLINE_SESSION_IDLE_TIMEOUT, clientOfflineSessionIdleTimeout)

  override fun getClientOfflineSessionMaxLifespan(): Int = getAttribute(CLIENT_OFFLINE_SESSION_MAX_LIFESPAN, 0)

  override fun setClientOfflineSessionMaxLifespan(clientOfflineSessionMaxLifespan: Int) =
    setAttribute(CLIENT_OFFLINE_SESSION_MAX_LIFESPAN, clientOfflineSessionMaxLifespan)

  override fun getAccessTokenLifespanForImplicitFlow(): Int = getAttribute(ACCESS_TOKEN_LIFESPAN_FOR_IMPLICIT_FLOW, 0)

  override fun setAccessTokenLifespanForImplicitFlow(accessTokenLifespanForImplicitFlow: Int) =
    setAttribute(ACCESS_TOKEN_LIFESPAN_FOR_IMPLICIT_FLOW, accessTokenLifespanForImplicitFlow)

  override fun getAccessCodeLifespan(): Int = getAttribute(ACCESS_CODE_LIFESPAN, 0)

  override fun setAccessCodeLifespan(accessCodeLifespan: Int) =
    setAttribute(ACCESS_CODE_LIFESPAN, accessCodeLifespan)

  override fun getAccessCodeLifespanUserAction(): Int = getAttribute(ACCESS_CODE_LIFESPAN_USER_ACTION, 0)

  override fun setAccessCodeLifespanUserAction(accessCodeLifespanUserAction: Int) =
    setAttribute(ACCESS_CODE_LIFESPAN_USER_ACTION, accessCodeLifespanUserAction)

  override fun getOAuth2DeviceConfig(): OAuth2DeviceConfig = OAuth2DeviceConfig(this)

  override fun getCibaPolicy(): CibaConfig = CibaConfig(this)

  override fun getParPolicy(): ParConfig = ParConfig(this)

  override fun getUserActionTokenLifespans(): Map<String, Int> =
    realmService.getAttributesByRealmId(realm.id).filter {
      it.name.startsWith("$ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN.")
    }.mapNotNull {
      val value = it.value?.toInt() ?: return@mapNotNull null

      it.name.removePrefix("$ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN.") to value
    }.toMap()

  override fun getAccessCodeLifespanLogin(): Int = getAttribute(ACCESS_CODE_LIFESPAN_LOGIN, 0)

  override fun setAccessCodeLifespanLogin(accessCodeLifespanLogin: Int) =
    setAttribute(ACCESS_CODE_LIFESPAN_LOGIN, accessCodeLifespanLogin)

  override fun getActionTokenGeneratedByAdminLifespan(): Int = getAttribute(ACTION_TOKEN_GENERATED_BY_ADMIN_LIFESPAN, 0)

  override fun setActionTokenGeneratedByAdminLifespan(actionTokenGeneratedByAdminLifespan: Int) =
    setAttribute(ACTION_TOKEN_GENERATED_BY_ADMIN_LIFESPAN, actionTokenGeneratedByAdminLifespan)

  override fun getActionTokenGeneratedByUserLifespan(): Int =
    getAttribute(ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN, getAccessCodeLifespanUserAction())

  override fun setActionTokenGeneratedByUserLifespan(actionTokenGeneratedByUserLifespan: Int) =
    setAttribute(ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN, actionTokenGeneratedByUserLifespan)

  override fun getActionTokenGeneratedByUserLifespan(actionTokenType: String?): Int {
    val attributeName = "$ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN.$actionTokenType"

    if (actionTokenType == null || getAttribute(attributeName) == null) {
      return getActionTokenGeneratedByUserLifespan()
    }
    return getAttribute(
      attributeName,
      getAccessCodeLifespanUserAction()
    )
  }

  override fun setActionTokenGeneratedByUserLifespan(actionTokenType: String?, seconds: Int?) {
    if (!actionTokenType.isNullOrEmpty() && seconds != null) {
      setAttribute("$ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN.$actionTokenType", seconds.toString())
    }
  }

  override fun getRequiredCredentialsStream(): Stream<RequiredCredentialModel> {
    TODO()
  }

  override fun addRequiredCredential(credentialType: String?) {
    TODO("Not yet implemented")
  }

  override fun getPasswordPolicy(): PasswordPolicy? = PasswordPolicy.parse(session, getAttribute(PASSWORD_POLICY))

  override fun setPasswordPolicy(passwordPolicy: PasswordPolicy?) =
    setAttribute(PASSWORD_POLICY, passwordPolicy?.toString())

  override fun getOTPPolicy(): OTPPolicy? {
    TODO("Not yet implemented")
  }

  override fun setOTPPolicy(otpPolicy: OTPPolicy?) {
    TODO("Not yet implemented")
  }

  override fun getWebAuthnPolicy(): WebAuthnPolicy? {
    TODO("Not yet implemented")
  }

  override fun setWebAuthnPolicy(webAuthnPolicy: WebAuthnPolicy?) {
    TODO("Not yet implemented")
  }

  override fun getWebAuthnPolicyPasswordless(): WebAuthnPolicy? {
    TODO("Not yet implemented")
  }

  override fun setWebAuthnPolicyPasswordless(webAuthnPolicy: WebAuthnPolicy?) {
    TODO("Not yet implemented")
  }

  override fun getRoleById(roleId: String?): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun getDefaultGroupsStream(): Stream<GroupModel?>? {
    TODO("Not yet implemented")
  }

  override fun addDefaultGroup(groupModel: GroupModel?) {
    TODO("Not yet implemented")
  }

  override fun removeDefaultGroup(groupModel: GroupModel?) {
    TODO("Not yet implemented")
  }

  override fun getClientsStream(): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun getClientsStream(firstResult: Int?, maxResults: Int?): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun getClientsCount(): Long? {
    TODO("Not yet implemented")
  }

  override fun getAlwaysDisplayInConsoleClientsStream(): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun addClient(clientId: String?): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun addClient(id: String?, clientId: String?): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun removeClient(id: String?): Boolean {
    TODO("Not yet implemented")
  }

  override fun getClientById(id: String?): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun getClientByClientId(clientId: String?): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun searchClientByClientIdStream(
    clientId: String?,
    firstResult: Int?,
    maxResults: Int?
  ): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun searchClientByAttributes(
    attributes: Map<String?, String?>?,
    firstResult: Int?,
    maxResults: Int?
  ): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun searchClientByAuthenticationFlowBindingOverrides(
    overrides: Map<String?, String?>?,
    firstResult: Int?,
    maxResults: Int?
  ): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun updateRequiredCredentials(credentialTypes: Set<String?>?) {
    TODO("Not yet implemented")
  }

  override fun getBrowserSecurityHeaders(): Map<String?, String?>? {
    TODO("Not yet implemented")
  }

  override fun setBrowserSecurityHeaders(browserSecurityHeaders: Map<String?, String?>?) {
    TODO("Not yet implemented")
  }

  override fun getSmtpConfig(): Map<String?, String?>? {
    TODO("Not yet implemented")
  }

  override fun setSmtpConfig(smtpConfig: Map<String?, String?>?) {
    TODO("Not yet implemented")
  }

  override fun getBrowserFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setBrowserFlow(browserFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getRegistrationFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setRegistrationFlow(registrationFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getDirectGrantFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setDirectGrantFlow(directGrantFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getResetCredentialsFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setResetCredentialsFlow(resetCredentialsFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getClientAuthenticationFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setClientAuthenticationFlow(clientAuthenticationFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getDockerAuthenticationFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setDockerAuthenticationFlow(dockerAuthenticationFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getFirstBrokerLoginFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setFirstBrokerLoginFlow(firstBrokerLoginFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationFlowsStream(): Stream<AuthenticationFlowModel?>? {
    TODO("Not yet implemented")
  }

  override fun getFlowByAlias(alias: String?): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun addAuthenticationFlow(authenticationFlow: AuthenticationFlowModel?): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationFlowById(id: String?): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun removeAuthenticationFlow(authenticationFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun updateAuthenticationFlow(authenticationFlow: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationExecutionsStream(flowId: String?): Stream<AuthenticationExecutionModel?>? {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationExecutionById(id: String?): AuthenticationExecutionModel? {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationExecutionByFlowId(flowId: String?): AuthenticationExecutionModel? {
    TODO("Not yet implemented")
  }

  override fun addAuthenticatorExecution(authenticationExecution: AuthenticationExecutionModel?): AuthenticationExecutionModel? {
    TODO("Not yet implemented")
  }

  override fun updateAuthenticatorExecution(authenticationExecution: AuthenticationExecutionModel?) {
    TODO("Not yet implemented")
  }

  override fun removeAuthenticatorExecution(authenticationExecution: AuthenticationExecutionModel?) {
    TODO("Not yet implemented")
  }

  override fun getAuthenticatorConfigsStream(): Stream<AuthenticatorConfigModel?>? {
    TODO("Not yet implemented")
  }

  override fun addAuthenticatorConfig(authenticatorConfig: AuthenticatorConfigModel?): AuthenticatorConfigModel? {
    TODO("Not yet implemented")
  }

  override fun updateAuthenticatorConfig(authenticatorConfig: AuthenticatorConfigModel?) {
    TODO("Not yet implemented")
  }

  override fun removeAuthenticatorConfig(authenticatorConfig: AuthenticatorConfigModel?) {
    TODO("Not yet implemented")
  }

  override fun getAuthenticatorConfigById(id: String?): AuthenticatorConfigModel? {
    TODO("Not yet implemented")
  }

  override fun getAuthenticatorConfigByAlias(alias: String?): AuthenticatorConfigModel? {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionConfigById(id: String?): RequiredActionConfigModel? {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionConfigByAlias(alias: String?): RequiredActionConfigModel? {
    TODO("Not yet implemented")
  }

  override fun removeRequiredActionProviderConfig(requiredActionConfig: RequiredActionConfigModel?) {
    TODO("Not yet implemented")
  }

  override fun updateRequiredActionConfig(requiredActionConfig: RequiredActionConfigModel?) {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionConfigsStream(): Stream<RequiredActionConfigModel?>? {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionProvidersStream(): Stream<RequiredActionProviderModel?>? {
    TODO("Not yet implemented")
  }

  override fun addRequiredActionProvider(requiredActionProvider: RequiredActionProviderModel?): RequiredActionProviderModel? {
    TODO("Not yet implemented")
  }

  override fun updateRequiredActionProvider(requiredActionProvider: RequiredActionProviderModel?) {
    TODO("Not yet implemented")
  }

  override fun removeRequiredActionProvider(requiredActionProvider: RequiredActionProviderModel?) {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionProviderById(id: String?): RequiredActionProviderModel? {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionProviderByAlias(alias: String?): RequiredActionProviderModel? {
    TODO("Not yet implemented")
  }

  override fun getIdentityProvidersStream(): Stream<IdentityProviderModel?>? {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderByAlias(alias: String?): IdentityProviderModel? {
    TODO("Not yet implemented")
  }

  override fun addIdentityProvider(identityProvider: IdentityProviderModel?) {
    TODO("Not yet implemented")
  }

  override fun removeIdentityProviderByAlias(alias: String?) {
    TODO("Not yet implemented")
  }

  override fun updateIdentityProvider(identityProvider: IdentityProviderModel?) {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderMappersStream(): Stream<IdentityProviderMapperModel?>? {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderMappersByAliasStream(brokerAlias: String?): Stream<IdentityProviderMapperModel?>? {
    TODO("Not yet implemented")
  }

  override fun addIdentityProviderMapper(identityProviderMapper: IdentityProviderMapperModel?): IdentityProviderMapperModel? {
    TODO("Not yet implemented")
  }

  override fun removeIdentityProviderMapper(identityProviderMapper: IdentityProviderMapperModel?) {
    TODO("Not yet implemented")
  }

  override fun updateIdentityProviderMapper(identityProviderMapper: IdentityProviderMapperModel?) {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderMapperById(id: String?): IdentityProviderMapperModel? {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderMapperByName(brokerAlias: String?, name: String?): IdentityProviderMapperModel? {
    TODO("Not yet implemented")
  }

  override fun addComponentModel(component: ComponentModel?): ComponentModel? {
    TODO("Not yet implemented")
  }

  override fun importComponentModel(component: ComponentModel?): ComponentModel? {
    TODO("Not yet implemented")
  }

  override fun updateComponent(component: ComponentModel?) {
    TODO("Not yet implemented")
  }

  override fun removeComponent(component: ComponentModel?) {
    TODO("Not yet implemented")
  }

  override fun removeComponents(parentId: String?) {
    TODO("Not yet implemented")
  }

  override fun getComponentsStream(parentId: String?, providerType: String?): Stream<ComponentModel?>? {
    TODO("Not yet implemented")
  }

  override fun getComponentsStream(parentId: String?): Stream<ComponentModel?>? {
    TODO("Not yet implemented")
  }

  override fun getComponentsStream(): Stream<ComponentModel?>? {
    TODO("Not yet implemented")
  }

  override fun getComponent(id: String?): ComponentModel? {
    TODO("Not yet implemented")
  }

  override fun getLoginTheme(): String? = getAttribute(LOGIN_THEME)

  override fun setLoginTheme(loginTheme: String?) = setAttribute(LOGIN_THEME, loginTheme)

  override fun getAccountTheme(): String? = getAttribute(ACCOUNT_THEME)

  override fun setAccountTheme(accountTheme: String?) = setAttribute(ACCOUNT_THEME, accountTheme)

  override fun getAdminTheme(): String? = getAttribute(ADMIN_THEME)

  override fun setAdminTheme(adminTheme: String?) = setAttribute(ADMIN_THEME, adminTheme)

  override fun getEmailTheme(): String? = getAttribute(EMAIL_THEME)

  override fun setEmailTheme(emailTheme: String?) = setAttribute(EMAIL_THEME, emailTheme)

  override fun getNotBefore(): Int = getAttribute(NOT_BEFORE, 0)

  override fun setNotBefore(notBefore: Int) = setAttribute(NOT_BEFORE, notBefore)

  override fun isEventsEnabled(): Boolean = getAttribute(IS_EVENTS_ENABLED, false)

  override fun setEventsEnabled(eventsEnabled: Boolean) = setAttribute(IS_EVENTS_ENABLED, eventsEnabled)

  override fun getEventsExpiration(): Long = getAttribute(EVENTS_EXPIRATION, 0L)

  override fun setEventsExpiration(eventsExpiration: Long) = setAttribute(EVENTS_EXPIRATION, eventsExpiration)

  override fun getEventsListenersStream(): Stream<String?>? {
    TODO("Not yet implemented")
  }

  override fun setEventsListeners(eventsListeners: Set<String?>?) {
    TODO("Not yet implemented")
  }

  override fun getEnabledEventTypesStream(): Stream<String?>? {
    TODO("Not yet implemented")
  }

  override fun setEnabledEventTypes(enabledEventTypes: Set<String?>?) {
    TODO("Not yet implemented")
  }

  override fun isAdminEventsEnabled(): Boolean = getAttribute(IS_ADMIN_EVENTS_ENABLED, false)

  override fun setAdminEventsEnabled(adminEventsEnabled: Boolean) =
    setAttribute(IS_ADMIN_EVENTS_ENABLED, adminEventsEnabled)

  override fun isAdminEventsDetailsEnabled(): Boolean = getAttribute(IS_ADMIN_EVENTS_DETAILS_ENABLED, false)

  override fun setAdminEventsDetailsEnabled(adminEventsDetailsEnabled: Boolean) =
    setAttribute(IS_ADMIN_EVENTS_DETAILS_ENABLED, adminEventsDetailsEnabled)

  override fun getMasterAdminClient(): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun setMasterAdminClient(masterAdminClient: ClientModel?) {
    TODO("Not yet implemented")
  }

  override fun getDefaultRole(): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun setDefaultRole(defaultRole: RoleModel?) {
    TODO("Not yet implemented")
  }

  override fun getAdminPermissionsClient(): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun setAdminPermissionsClient(adminPermissionsClient: ClientModel?) {
    TODO("Not yet implemented")
  }

  override fun isIdentityFederationEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isInternationalizationEnabled(): Boolean = getAttribute(IS_INTERNATIONALIZATION_ENABLED, false)

  override fun setInternationalizationEnabled(internationalizationEnabled: Boolean) =
    setAttribute(IS_INTERNATIONALIZATION_ENABLED, internationalizationEnabled)

  override fun getSupportedLocalesStream(): Stream<String?>? {
    TODO("Not yet implemented")
  }

  override fun setSupportedLocales(supportedLocales: Set<String?>?) {
    TODO("Not yet implemented")
  }

  override fun getDefaultLocale(): String? = getAttribute(DEFAULT_LOCALE)

  override fun setDefaultLocale(defaultLocale: String?) = setAttribute(DEFAULT_LOCALE, defaultLocale)

  override fun createGroup(id: String?, name: String?, toParent: GroupModel?): GroupModel? {
    TODO("Not yet implemented")
  }

  override fun getGroupById(id: String?): GroupModel? {
    TODO("Not yet implemented")
  }

  override fun getGroupsStream(): Stream<GroupModel?>? {
    TODO("Not yet implemented")
  }

  override fun getGroupsCount(onlyTopGroups: Boolean?): Long? {
    TODO("Not yet implemented")
  }

  override fun getGroupsCountByNameContaining(search: String?): Long? {
    TODO("Not yet implemented")
  }

  override fun getTopLevelGroupsStream(): Stream<GroupModel?>? {
    TODO("Not yet implemented")
  }

  override fun getTopLevelGroupsStream(first: Int?, max: Int?): Stream<GroupModel?>? {
    TODO("Not yet implemented")
  }

  override fun removeGroup(groupModel: GroupModel?): Boolean {
    TODO("Not yet implemented")
  }

  override fun moveGroup(groupModel: GroupModel?, toParent: GroupModel?) {
    TODO("Not yet implemented")
  }

  override fun getClientScopesStream(): Stream<ClientScopeModel?>? {
    TODO("Not yet implemented")
  }

  override fun addClientScope(name: String?): ClientScopeModel? {
    TODO("Not yet implemented")
  }

  override fun addClientScope(id: String?, name: String?): ClientScopeModel? {
    TODO("Not yet implemented")
  }

  override fun removeClientScope(id: String?): Boolean {
    TODO("Not yet implemented")
  }

  override fun getClientScopeById(id: String?): ClientScopeModel? {
    TODO("Not yet implemented")
  }

  override fun addDefaultClientScope(clientScope: ClientScopeModel?, defaultScope: Boolean) {
    TODO("Not yet implemented")
  }

  override fun removeDefaultClientScope(clientScope: ClientScopeModel?) {
    TODO("Not yet implemented")
  }

  override fun createOrUpdateRealmLocalizationTexts(locale: String?, localizationTexts: Map<String?, String?>?) {
    TODO("Not yet implemented")
  }

  override fun removeRealmLocalizationTexts(locale: String?): Boolean {
    TODO("Not yet implemented")
  }

  override fun getRealmLocalizationTexts(): Map<String?, Map<String?, String?>?>? {
    TODO("Not yet implemented")
  }

  override fun getRealmLocalizationTextsByLocale(locale: String?): Map<String?, String?>? {
    TODO("Not yet implemented")
  }

  override fun getDefaultClientScopesStream(defaultScope: Boolean): Stream<ClientScopeModel?>? {
    TODO("Not yet implemented")
  }

  override fun createClientInitialAccessModel(expiration: Int, count: Int): ClientInitialAccessModel? {
    TODO("Not yet implemented")
  }

  override fun getClientInitialAccessModel(id: String?): ClientInitialAccessModel? {
    TODO("Not yet implemented")
  }

  override fun removeClientInitialAccessModel(id: String?) {
    TODO("Not yet implemented")
  }

  override fun getClientInitialAccesses(): Stream<ClientInitialAccessModel?>? {
    TODO("Not yet implemented")
  }

  override fun decreaseRemainingCount(clientInitialAccess: ClientInitialAccessModel?) {
    TODO("Not yet implemented")
  }

  override fun getRole(name: String?): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun addRole(name: String?): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun addRole(id: String?, name: String?): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun removeRole(roleModel: RoleModel?): Boolean {
    TODO("Not yet implemented")
  }

  override fun getRolesStream(): Stream<RoleModel?>? {
    TODO("Not yet implemented")
  }

  override fun getRolesStream(firstResult: Int?, maxResults: Int?): Stream<RoleModel?>? {
    TODO("Not yet implemented")
  }

  override fun searchForRolesStream(search: String?, first: Int?, max: Int?): Stream<RoleModel?>? {
    TODO("Not yet implemented")
  }

  private fun setAttribute(name: String?, values: List<String>?) {
    if (name == null || values == null || name.startsWith(READONLY_ATTRIBUTE_PREFIX)) {
      return
    }

    realmService.updateRealmAttributes(realm.id, name, values)
  }

  private companion object {
    const val DISPLAY_NAME: String = INTERNAL_ATTRIBUTE_PREFIX + "displayName"
    const val DISPLAY_NAME_HTML: String = INTERNAL_ATTRIBUTE_PREFIX + "displayNameHtml"
    const val IS_ENABLED: String = INTERNAL_ATTRIBUTE_PREFIX + "enabled"

    const val SSL_REQUIRED: String = INTERNAL_ATTRIBUTE_PREFIX + "sslRequired"
    const val IS_REGISTRATION_ALLOWED: String = INTERNAL_ATTRIBUTE_PREFIX + "isRegistrationAllowed"
    const val IS_REGISTRATION_EMAIL_AS_USERNAME: String = INTERNAL_ATTRIBUTE_PREFIX + "isRegistrationEmailAsUsername"
    const val IS_REMEMBER_ME: String = INTERNAL_ATTRIBUTE_PREFIX + "isRememberMe"
    const val IS_EDIT_USERNAME_ALLOWED: String = INTERNAL_ATTRIBUTE_PREFIX + "isEditUsernameAllowed"
    const val IS_USER_MANAGED_ACCESS_ALLOWED: String = INTERNAL_ATTRIBUTE_PREFIX + "isUserManagedAccessAllowed"
    const val IS_ORGANIZATIONS_ENABLED: String = INTERNAL_ATTRIBUTE_PREFIX + "organizationsEnabled"
    const val IS_ADMIN_PERMISSIONS_ENABLED: String = INTERNAL_ATTRIBUTE_PREFIX + "adminPermissionsEnabled"
    const val IS_VERIFIABLE_CREDENTIALS_ENABLED: String = INTERNAL_ATTRIBUTE_PREFIX + "verifiableCredentialsEnabled"
    const val IS_BRUTE_FORCE_PROTECTED: String = INTERNAL_ATTRIBUTE_PREFIX + "isBruteForceProtected"
    const val IS_PERMANENT_LOCKOUT: String = INTERNAL_ATTRIBUTE_PREFIX + "isPermanentLockout"
    const val MAX_TEMPORARY_LOCKOUTS: String = INTERNAL_ATTRIBUTE_PREFIX + "maxTemporaryLockouts"
    const val BRUTE_FORCE_STRATEGY: String = INTERNAL_ATTRIBUTE_PREFIX + "bruteForceStrategy"
    const val MAX_FAILURE_WAIT_SECONDS: String = INTERNAL_ATTRIBUTE_PREFIX + "maxFailureWaitSeconds"
    const val WAIT_INCREMENT_SECONDS: String = INTERNAL_ATTRIBUTE_PREFIX + "waitIncrementSeconds"
    const val MINIMUM_QUICK_LOGIN_WAIT_SECONDS: String = INTERNAL_ATTRIBUTE_PREFIX + "minimumQuickLoginWaitSeconds"
    const val QUICK_LOGIN_CHECK_MILLI_SECONDS: String = INTERNAL_ATTRIBUTE_PREFIX + "quickLoginCheckMilliSeconds"
    const val MAX_DELTA_TIME_SECONDS: String = INTERNAL_ATTRIBUTE_PREFIX + "maxDeltaTimeSeconds"
    const val FAILURE_FACTOR: String = INTERNAL_ATTRIBUTE_PREFIX + "failureFactor"
    const val VERIFY_EMAIL: String = INTERNAL_ATTRIBUTE_PREFIX + "verifyEmail"
    const val LOGIN_WITH_EMAIL_ALLOWED: String = INTERNAL_ATTRIBUTE_PREFIX + "loginWithEmailAllowed"
    const val IS_DUPLICATE_EMAILS_ALLOWED: String = INTERNAL_ATTRIBUTE_PREFIX + "isDuplicateEmailsAllowed"
    const val IS_RESET_PASSWORD_ALLOWED: String = INTERNAL_ATTRIBUTE_PREFIX + "isResetPasswordAllowed"
    const val DEFAULT_SIG_ALGORITHM: String = INTERNAL_ATTRIBUTE_PREFIX + "defaultSigAlgorithm"
    const val IS_REVOKE_REFRESH_TOKEN: String = INTERNAL_ATTRIBUTE_PREFIX + "isRevokeRefreshToken"
    const val REFRESH_TOKEN_MAX_REUSE: String = INTERNAL_ATTRIBUTE_PREFIX + "refreshTokenMaxReuse"
    const val SSO_SESSION_IDLE_TIMEOUT: String = INTERNAL_ATTRIBUTE_PREFIX + "ssoSessionIdleTimeout"
    const val SSO_SESSION_MAX_LIFESPAN: String = INTERNAL_ATTRIBUTE_PREFIX + "ssoSessionMaxLifespan"
    const val SSO_SESSION_IDLE_TIMEOUT_REMEMBER_ME: String =
      INTERNAL_ATTRIBUTE_PREFIX + "ssoSessionIdleTimeoutRememberMe"
    const val SSO_SESSION_MAX_LIFESPAN_REMEMBER_ME: String =
      INTERNAL_ATTRIBUTE_PREFIX + "ssoSessionMaxLifespanRememberMe"
    const val OFFLINE_SESSION_IDLE_TIMEOUT: String = INTERNAL_ATTRIBUTE_PREFIX + "offlineSessionIdleTimeout"
    const val ACCESS_TOKEN_LIFESPAN: String = INTERNAL_ATTRIBUTE_PREFIX + "accessTokenLifespan"
    const val IS_OFFLINE_SESSION_MAX_LIFESPAN_ENABLED: String =
      INTERNAL_ATTRIBUTE_PREFIX + "isOfflineSessionMaxLifespanEnabled"
    const val OFFLINE_SESSION_MAX_LIFESPAN: String = INTERNAL_ATTRIBUTE_PREFIX + "offlineSessionMaxLifespan"
    const val CLIENT_SESSION_IDLE_TIMEOUT: String = INTERNAL_ATTRIBUTE_PREFIX + "clientSessionIdleTimeout"
    const val CLIENT_SESSION_MAX_LIFESPAN: String = INTERNAL_ATTRIBUTE_PREFIX + "clientSessionMaxLifespan"
    const val CLIENT_OFFLINE_SESSION_IDLE_TIMEOUT: String =
      INTERNAL_ATTRIBUTE_PREFIX + "clientOfflineSessionIdleTimeout"
    const val CLIENT_OFFLINE_SESSION_MAX_LIFESPAN: String =
      INTERNAL_ATTRIBUTE_PREFIX + "clientOfflineSessionMaxLifespan"
    const val ACCESS_TOKEN_LIFESPAN_FOR_IMPLICIT_FLOW: String =
      INTERNAL_ATTRIBUTE_PREFIX + "accessTokenLifespanForImplicitFlow"
    const val ACCESS_CODE_LIFESPAN: String = INTERNAL_ATTRIBUTE_PREFIX + "accessCodeLifespan"
    const val ACCESS_CODE_LIFESPAN_USER_ACTION: String = INTERNAL_ATTRIBUTE_PREFIX + "accessCodeLifespanUserAction"
    const val ACCESS_CODE_LIFESPAN_LOGIN: String = INTERNAL_ATTRIBUTE_PREFIX + "accessCodeLifespanLogin"
    const val ACTION_TOKEN_GENERATED_BY_ADMIN_LIFESPAN: String =
      INTERNAL_ATTRIBUTE_PREFIX + "actionTokenGeneratedByAdminLifespan"
    const val ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN: String =
      INTERNAL_ATTRIBUTE_PREFIX + "actionTokenGeneratedByUserLifespan"
    const val PASSWORD_POLICY: String = INTERNAL_ATTRIBUTE_PREFIX + "passwordPolicy"
    const val REQUIRED_CREDENTIALS: String = INTERNAL_ATTRIBUTE_PREFIX + "requiredCredentials"
    const val OTP_POLICY: String = INTERNAL_ATTRIBUTE_PREFIX + "otpPolicy"
    const val WEB_AUTHN_POLICY: String = INTERNAL_ATTRIBUTE_PREFIX + "webAuthnPolicy"
    const val WEB_AUTHN_POLICY_PASSWORDLESS: String = INTERNAL_ATTRIBUTE_PREFIX + "webAuthnPolicyPasswordless"
    const val BROWSER_SECURITY_HEADERS: String = INTERNAL_ATTRIBUTE_PREFIX + "browserSecurityHeaders"
    const val SMTP_CONFIG: String = INTERNAL_ATTRIBUTE_PREFIX + "smtpConfig"
    const val BROWSER_FLOW: String = INTERNAL_ATTRIBUTE_PREFIX + "browserFlow"
    const val REGISTRATION_FLOW: String = INTERNAL_ATTRIBUTE_PREFIX + "registrationFlow"
    const val DIRECT_GRANT_FLOW: String = INTERNAL_ATTRIBUTE_PREFIX + "directGrantFlow"
    const val RESET_CREDENTIALS_FLOW: String = INTERNAL_ATTRIBUTE_PREFIX + "resetCredentialsFlow"
    const val CLIENT_AUTHENTICATION_FLOW: String = INTERNAL_ATTRIBUTE_PREFIX + "clientAuthenticationFlow"
    const val DOCKER_AUTHENTICATION_FLOW: String = INTERNAL_ATTRIBUTE_PREFIX + "dockerAuthenticationFlow"
    const val FIRST_BROKER_LOGIN_FLOW: String = INTERNAL_ATTRIBUTE_PREFIX + "firstBrokerLoginFlow"
    const val AUTHENTICATION_FLOWS: String = INTERNAL_ATTRIBUTE_PREFIX + "authenticationFlows"
    const val AUTHENTICATION_EXECUTION_MODELS: String = INTERNAL_ATTRIBUTE_PREFIX + "authenticationExecutionModels"
    const val AUTHENTICATOR_CONFIG_MODELS: String = INTERNAL_ATTRIBUTE_PREFIX + "authenticatorConfigModels"
    const val REQUIRED_ACTION_CONFIG_MODELS: String = INTERNAL_ATTRIBUTE_PREFIX + "requiredActionConfigModels"
    const val REQUIRED_ACTION_PROVIDER_MODELS: String = INTERNAL_ATTRIBUTE_PREFIX + "requiredActionProviderModels"
    const val IDENTITY_PROVIDERS: String = INTERNAL_ATTRIBUTE_PREFIX + "identityProviders"
    const val IDENTITY_PROVIDER_MAPPERS: String = INTERNAL_ATTRIBUTE_PREFIX + "identityProviderMappers"
    const val COMPONENTS: String = INTERNAL_ATTRIBUTE_PREFIX + "components"
    const val LOGIN_THEME: String = INTERNAL_ATTRIBUTE_PREFIX + "loginTheme"
    const val ACCOUNT_THEME: String = INTERNAL_ATTRIBUTE_PREFIX + "accountTheme"
    const val ADMIN_THEME: String = INTERNAL_ATTRIBUTE_PREFIX + "adminTheme"
    const val EMAIL_THEME: String = INTERNAL_ATTRIBUTE_PREFIX + "emailTheme"
    const val NOT_BEFORE: String = INTERNAL_ATTRIBUTE_PREFIX + "notBefore"
    const val IS_EVENTS_ENABLED: String = INTERNAL_ATTRIBUTE_PREFIX + "isEventsEnabled"
    const val EVENTS_EXPIRATION: String = INTERNAL_ATTRIBUTE_PREFIX + "eventsExpiration"
    const val EVENT_LISTENERS: String = INTERNAL_ATTRIBUTE_PREFIX + "eventListeners"
    const val ENABLED_EVENT_TYPES: String = INTERNAL_ATTRIBUTE_PREFIX + "enabledEventTypes"
    const val IS_ADMIN_EVENTS_ENABLED: String = INTERNAL_ATTRIBUTE_PREFIX + "isAdminEventsEnabled"
    const val IS_ADMIN_EVENTS_DETAILS_ENABLED: String = INTERNAL_ATTRIBUTE_PREFIX + "isAdminEventsDetailsEnabled"
    const val MASTER_ADMIN_CLIENT_ID: String = INTERNAL_ATTRIBUTE_PREFIX + "masterAdminClientId"
    const val IS_INTERNATIONALIZATION_ENABLED: String = INTERNAL_ATTRIBUTE_PREFIX + "isInternationalizationEnabled"
    const val SUPPORTED_LOCALES: String = INTERNAL_ATTRIBUTE_PREFIX + "supportedLocales"
    const val DEFAULT_LOCALE: String = INTERNAL_ATTRIBUTE_PREFIX + "defaultLocale"
    const val LOCALIZATION_TEXTS: String = INTERNAL_ATTRIBUTE_PREFIX + "localizationTexts"
    const val DEFAULT_GROUP_IDS: String = INTERNAL_ATTRIBUTE_PREFIX + "defaultGroupIds"
    const val DEFAULT_ROLE_ID: String = INTERNAL_ATTRIBUTE_PREFIX + "defaultRoleId"
    const val DEFAULT_CLIENT_SCOPE_ID: String = INTERNAL_ATTRIBUTE_PREFIX + "defaultClientScopeId"
    const val OPTIONAL_CLIENT_SCOPE_ID: String = INTERNAL_ATTRIBUTE_PREFIX + "optionalClientScopeId"
  }
}
