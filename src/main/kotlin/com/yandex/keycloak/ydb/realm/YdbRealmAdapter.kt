package com.yandex.keycloak.ydb.realm

import com.yandex.keycloak.ydb.common.AttributePrefixes.INTERNAL_ATTRIBUTE_PREFIX
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

  override fun getName(): String = realm.name

  override fun setName(name: String?) {
    if (name != null && name != realm.name) {
      realm = realm.copy(name = name)

      realmService.updateRealm(realm)
    }
  }

  override fun getDisplayName(): String? = getAttribute(DISPLAY_NAME)

  override fun setDisplayName(displayName: String?) = setAttribute(DISPLAY_NAME, displayName)

  override fun getDisplayNameHtml(): String? = getAttribute(DISPLAY_NAME_HTML);

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

  override fun setOrganizationsEnabled(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun isAdminPermissionsEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setAdminPermissionsEnabled(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun isVerifiableCredentialsEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setVerifiableCredentialsEnabled(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun setAttribute(p0: String?, p1: String?) {
    TODO("Not yet implemented")
  }

  override fun removeAttribute(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun getAttribute(p0: String?): String? {
    TODO("Not yet implemented")
  }

  override fun getAttributes(): Map<String?, String?>? {
    TODO("Not yet implemented")
  }

  override fun isBruteForceProtected(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setBruteForceProtected(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun isPermanentLockout(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setPermanentLockout(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun getMaxTemporaryLockouts(): Int {
    TODO("Not yet implemented")
  }

  override fun setMaxTemporaryLockouts(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getBruteForceStrategy(): RealmRepresentation.BruteForceStrategy? {
    TODO("Not yet implemented")
  }

  override fun setBruteForceStrategy(p0: RealmRepresentation.BruteForceStrategy?) {
    TODO("Not yet implemented")
  }

  override fun getMaxFailureWaitSeconds(): Int {
    TODO("Not yet implemented")
  }

  override fun setMaxFailureWaitSeconds(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getWaitIncrementSeconds(): Int {
    TODO("Not yet implemented")
  }

  override fun setWaitIncrementSeconds(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getMinimumQuickLoginWaitSeconds(): Int {
    TODO("Not yet implemented")
  }

  override fun setMinimumQuickLoginWaitSeconds(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getQuickLoginCheckMilliSeconds(): Long {
    TODO("Not yet implemented")
  }

  override fun setQuickLoginCheckMilliSeconds(p0: Long) {
    TODO("Not yet implemented")
  }

  override fun getMaxDeltaTimeSeconds(): Int {
    TODO("Not yet implemented")
  }

  override fun setMaxDeltaTimeSeconds(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getFailureFactor(): Int {
    TODO("Not yet implemented")
  }

  override fun setFailureFactor(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun isVerifyEmail(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setVerifyEmail(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun isLoginWithEmailAllowed(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setLoginWithEmailAllowed(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun isDuplicateEmailsAllowed(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setDuplicateEmailsAllowed(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun isResetPasswordAllowed(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setResetPasswordAllowed(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun getDefaultSignatureAlgorithm(): String? {
    TODO("Not yet implemented")
  }

  override fun setDefaultSignatureAlgorithm(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun isRevokeRefreshToken(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setRevokeRefreshToken(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun getRefreshTokenMaxReuse(): Int {
    TODO("Not yet implemented")
  }

  override fun setRefreshTokenMaxReuse(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getSsoSessionIdleTimeout(): Int {
    TODO("Not yet implemented")
  }

  override fun setSsoSessionIdleTimeout(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getSsoSessionMaxLifespan(): Int {
    TODO("Not yet implemented")
  }

  override fun setSsoSessionMaxLifespan(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getSsoSessionIdleTimeoutRememberMe(): Int {
    TODO("Not yet implemented")
  }

  override fun setSsoSessionIdleTimeoutRememberMe(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getSsoSessionMaxLifespanRememberMe(): Int {
    TODO("Not yet implemented")
  }

  override fun setSsoSessionMaxLifespanRememberMe(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getOfflineSessionIdleTimeout(): Int {
    TODO("Not yet implemented")
  }

  override fun setOfflineSessionIdleTimeout(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getAccessTokenLifespan(): Int {
    TODO("Not yet implemented")
  }

  override fun isOfflineSessionMaxLifespanEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setOfflineSessionMaxLifespanEnabled(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun getOfflineSessionMaxLifespan(): Int {
    TODO("Not yet implemented")
  }

  override fun setOfflineSessionMaxLifespan(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getClientSessionIdleTimeout(): Int {
    TODO("Not yet implemented")
  }

  override fun setClientSessionIdleTimeout(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getClientSessionMaxLifespan(): Int {
    TODO("Not yet implemented")
  }

  override fun setClientSessionMaxLifespan(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getClientOfflineSessionIdleTimeout(): Int {
    TODO("Not yet implemented")
  }

  override fun setClientOfflineSessionIdleTimeout(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getClientOfflineSessionMaxLifespan(): Int {
    TODO("Not yet implemented")
  }

  override fun setClientOfflineSessionMaxLifespan(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun setAccessTokenLifespan(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getAccessTokenLifespanForImplicitFlow(): Int {
    TODO("Not yet implemented")
  }

  override fun setAccessTokenLifespanForImplicitFlow(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getAccessCodeLifespan(): Int {
    TODO("Not yet implemented")
  }

  override fun setAccessCodeLifespan(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getAccessCodeLifespanUserAction(): Int {
    TODO("Not yet implemented")
  }

  override fun setAccessCodeLifespanUserAction(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getOAuth2DeviceConfig(): OAuth2DeviceConfig? {
    TODO("Not yet implemented")
  }

  override fun getCibaPolicy(): CibaConfig? {
    TODO("Not yet implemented")
  }

  override fun getParPolicy(): ParConfig? {
    TODO("Not yet implemented")
  }

  override fun getUserActionTokenLifespans(): Map<String?, Int?>? {
    TODO("Not yet implemented")
  }

  override fun getAccessCodeLifespanLogin(): Int {
    TODO("Not yet implemented")
  }

  override fun setAccessCodeLifespanLogin(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getActionTokenGeneratedByAdminLifespan(): Int {
    TODO("Not yet implemented")
  }

  override fun setActionTokenGeneratedByAdminLifespan(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getActionTokenGeneratedByUserLifespan(): Int {
    TODO("Not yet implemented")
  }

  override fun setActionTokenGeneratedByUserLifespan(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun getActionTokenGeneratedByUserLifespan(p0: String?): Int {
    TODO("Not yet implemented")
  }

  override fun setActionTokenGeneratedByUserLifespan(p0: String?, p1: Int?) {
    TODO("Not yet implemented")
  }

  override fun getRequiredCredentialsStream(): Stream<RequiredCredentialModel?>? {
    TODO("Not yet implemented")
  }

  override fun addRequiredCredential(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun getPasswordPolicy(): PasswordPolicy? {
    TODO("Not yet implemented")
  }

  override fun setPasswordPolicy(p0: PasswordPolicy?) {
    TODO("Not yet implemented")
  }

  override fun getOTPPolicy(): OTPPolicy? {
    TODO("Not yet implemented")
  }

  override fun setOTPPolicy(p0: OTPPolicy?) {
    TODO("Not yet implemented")
  }

  override fun getWebAuthnPolicy(): WebAuthnPolicy? {
    TODO("Not yet implemented")
  }

  override fun setWebAuthnPolicy(p0: WebAuthnPolicy?) {
    TODO("Not yet implemented")
  }

  override fun getWebAuthnPolicyPasswordless(): WebAuthnPolicy? {
    TODO("Not yet implemented")
  }

  override fun setWebAuthnPolicyPasswordless(p0: WebAuthnPolicy?) {
    TODO("Not yet implemented")
  }

  override fun getRoleById(p0: String?): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun getDefaultGroupsStream(): Stream<GroupModel?>? {
    TODO("Not yet implemented")
  }

  override fun addDefaultGroup(p0: GroupModel?) {
    TODO("Not yet implemented")
  }

  override fun removeDefaultGroup(p0: GroupModel?) {
    TODO("Not yet implemented")
  }

  override fun getClientsStream(): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun getClientsStream(
    p0: Int?,
    p1: Int?
  ): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun getClientsCount(): Long? {
    TODO("Not yet implemented")
  }

  override fun getAlwaysDisplayInConsoleClientsStream(): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun addClient(p0: String?): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun addClient(p0: String?, p1: String?): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun removeClient(p0: String?): Boolean {
    TODO("Not yet implemented")
  }

  override fun getClientById(p0: String?): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun getClientByClientId(p0: String?): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun searchClientByClientIdStream(
    p0: String?,
    p1: Int?,
    p2: Int?
  ): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun searchClientByAttributes(
    p0: Map<String?, String?>?,
    p1: Int?,
    p2: Int?
  ): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun searchClientByAuthenticationFlowBindingOverrides(
    p0: Map<String?, String?>?,
    p1: Int?,
    p2: Int?
  ): Stream<ClientModel?>? {
    TODO("Not yet implemented")
  }

  override fun updateRequiredCredentials(p0: Set<String?>?) {
    TODO("Not yet implemented")
  }

  override fun getBrowserSecurityHeaders(): Map<String?, String?>? {
    TODO("Not yet implemented")
  }

  override fun setBrowserSecurityHeaders(p0: Map<String?, String?>?) {
    TODO("Not yet implemented")
  }

  override fun getSmtpConfig(): Map<String?, String?>? {
    TODO("Not yet implemented")
  }

  override fun setSmtpConfig(p0: Map<String?, String?>?) {
    TODO("Not yet implemented")
  }

  override fun getBrowserFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setBrowserFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getRegistrationFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setRegistrationFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getDirectGrantFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setDirectGrantFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getResetCredentialsFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setResetCredentialsFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getClientAuthenticationFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setClientAuthenticationFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getDockerAuthenticationFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setDockerAuthenticationFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getFirstBrokerLoginFlow(): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun setFirstBrokerLoginFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationFlowsStream(): Stream<AuthenticationFlowModel?>? {
    TODO("Not yet implemented")
  }

  override fun getFlowByAlias(p0: String?): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun addAuthenticationFlow(p0: AuthenticationFlowModel?): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationFlowById(p0: String?): AuthenticationFlowModel? {
    TODO("Not yet implemented")
  }

  override fun removeAuthenticationFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun updateAuthenticationFlow(p0: AuthenticationFlowModel?) {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationExecutionsStream(p0: String?): Stream<AuthenticationExecutionModel?>? {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationExecutionById(p0: String?): AuthenticationExecutionModel? {
    TODO("Not yet implemented")
  }

  override fun getAuthenticationExecutionByFlowId(p0: String?): AuthenticationExecutionModel? {
    TODO("Not yet implemented")
  }

  override fun addAuthenticatorExecution(p0: AuthenticationExecutionModel?): AuthenticationExecutionModel? {
    TODO("Not yet implemented")
  }

  override fun updateAuthenticatorExecution(p0: AuthenticationExecutionModel?) {
    TODO("Not yet implemented")
  }

  override fun removeAuthenticatorExecution(p0: AuthenticationExecutionModel?) {
    TODO("Not yet implemented")
  }

  override fun getAuthenticatorConfigsStream(): Stream<AuthenticatorConfigModel?>? {
    TODO("Not yet implemented")
  }

  override fun addAuthenticatorConfig(p0: AuthenticatorConfigModel?): AuthenticatorConfigModel? {
    TODO("Not yet implemented")
  }

  override fun updateAuthenticatorConfig(p0: AuthenticatorConfigModel?) {
    TODO("Not yet implemented")
  }

  override fun removeAuthenticatorConfig(p0: AuthenticatorConfigModel?) {
    TODO("Not yet implemented")
  }

  override fun getAuthenticatorConfigById(p0: String?): AuthenticatorConfigModel? {
    TODO("Not yet implemented")
  }

  override fun getAuthenticatorConfigByAlias(p0: String?): AuthenticatorConfigModel? {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionConfigById(p0: String?): RequiredActionConfigModel? {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionConfigByAlias(p0: String?): RequiredActionConfigModel? {
    TODO("Not yet implemented")
  }

  override fun removeRequiredActionProviderConfig(p0: RequiredActionConfigModel?) {
    TODO("Not yet implemented")
  }

  override fun updateRequiredActionConfig(p0: RequiredActionConfigModel?) {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionConfigsStream(): Stream<RequiredActionConfigModel?>? {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionProvidersStream(): Stream<RequiredActionProviderModel?>? {
    TODO("Not yet implemented")
  }

  override fun addRequiredActionProvider(p0: RequiredActionProviderModel?): RequiredActionProviderModel? {
    TODO("Not yet implemented")
  }

  override fun updateRequiredActionProvider(p0: RequiredActionProviderModel?) {
    TODO("Not yet implemented")
  }

  override fun removeRequiredActionProvider(p0: RequiredActionProviderModel?) {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionProviderById(p0: String?): RequiredActionProviderModel? {
    TODO("Not yet implemented")
  }

  override fun getRequiredActionProviderByAlias(p0: String?): RequiredActionProviderModel? {
    TODO("Not yet implemented")
  }

  override fun getIdentityProvidersStream(): Stream<IdentityProviderModel?>? {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderByAlias(p0: String?): IdentityProviderModel? {
    TODO("Not yet implemented")
  }

  override fun addIdentityProvider(p0: IdentityProviderModel?) {
    TODO("Not yet implemented")
  }

  override fun removeIdentityProviderByAlias(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun updateIdentityProvider(p0: IdentityProviderModel?) {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderMappersStream(): Stream<IdentityProviderMapperModel?>? {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderMappersByAliasStream(p0: String?): Stream<IdentityProviderMapperModel?>? {
    TODO("Not yet implemented")
  }

  override fun addIdentityProviderMapper(p0: IdentityProviderMapperModel?): IdentityProviderMapperModel? {
    TODO("Not yet implemented")
  }

  override fun removeIdentityProviderMapper(p0: IdentityProviderMapperModel?) {
    TODO("Not yet implemented")
  }

  override fun updateIdentityProviderMapper(p0: IdentityProviderMapperModel?) {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderMapperById(p0: String?): IdentityProviderMapperModel? {
    TODO("Not yet implemented")
  }

  override fun getIdentityProviderMapperByName(
    p0: String?,
    p1: String?
  ): IdentityProviderMapperModel? {
    TODO("Not yet implemented")
  }

  override fun addComponentModel(p0: ComponentModel?): ComponentModel? {
    TODO("Not yet implemented")
  }

  override fun importComponentModel(p0: ComponentModel?): ComponentModel? {
    TODO("Not yet implemented")
  }

  override fun updateComponent(p0: ComponentModel?) {
    TODO("Not yet implemented")
  }

  override fun removeComponent(p0: ComponentModel?) {
    TODO("Not yet implemented")
  }

  override fun removeComponents(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun getComponentsStream(
    p0: String?,
    p1: String?
  ): Stream<ComponentModel?>? {
    TODO("Not yet implemented")
  }

  override fun getComponentsStream(p0: String?): Stream<ComponentModel?>? {
    TODO("Not yet implemented")
  }

  override fun getComponentsStream(): Stream<ComponentModel?>? {
    TODO("Not yet implemented")
  }

  override fun getComponent(p0: String?): ComponentModel? {
    TODO("Not yet implemented")
  }

  override fun getLoginTheme(): String? {
    TODO("Not yet implemented")
  }

  override fun setLoginTheme(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun getAccountTheme(): String? {
    TODO("Not yet implemented")
  }

  override fun setAccountTheme(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun getAdminTheme(): String? {
    TODO("Not yet implemented")
  }

  override fun setAdminTheme(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun getEmailTheme(): String? {
    TODO("Not yet implemented")
  }

  override fun setEmailTheme(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun getNotBefore(): Int {
    TODO("Not yet implemented")
  }

  override fun setNotBefore(p0: Int) {
    TODO("Not yet implemented")
  }

  override fun isEventsEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setEventsEnabled(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun getEventsExpiration(): Long {
    TODO("Not yet implemented")
  }

  override fun setEventsExpiration(p0: Long) {
    TODO("Not yet implemented")
  }

  override fun getEventsListenersStream(): Stream<String?>? {
    TODO("Not yet implemented")
  }

  override fun setEventsListeners(p0: Set<String?>?) {
    TODO("Not yet implemented")
  }

  override fun getEnabledEventTypesStream(): Stream<String?>? {
    TODO("Not yet implemented")
  }

  override fun setEnabledEventTypes(p0: Set<String?>?) {
    TODO("Not yet implemented")
  }

  override fun isAdminEventsEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setAdminEventsEnabled(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun isAdminEventsDetailsEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setAdminEventsDetailsEnabled(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun getMasterAdminClient(): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun setMasterAdminClient(p0: ClientModel?) {
    TODO("Not yet implemented")
  }

  override fun getDefaultRole(): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun setDefaultRole(p0: RoleModel?) {
    TODO("Not yet implemented")
  }

  override fun getAdminPermissionsClient(): ClientModel? {
    TODO("Not yet implemented")
  }

  override fun setAdminPermissionsClient(p0: ClientModel?) {
    TODO("Not yet implemented")
  }

  override fun isIdentityFederationEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isInternationalizationEnabled(): Boolean {
    TODO("Not yet implemented")
  }

  override fun setInternationalizationEnabled(p0: Boolean) {
    TODO("Not yet implemented")
  }

  override fun getSupportedLocalesStream(): Stream<String?>? {
    TODO("Not yet implemented")
  }

  override fun setSupportedLocales(p0: Set<String?>?) {
    TODO("Not yet implemented")
  }

  override fun getDefaultLocale(): String? {
    TODO("Not yet implemented")
  }

  override fun setDefaultLocale(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun createGroup(
    p0: String?,
    p1: String?,
    p2: GroupModel?
  ): GroupModel? {
    TODO("Not yet implemented")
  }

  override fun getGroupById(p0: String?): GroupModel? {
    TODO("Not yet implemented")
  }

  override fun getGroupsStream(): Stream<GroupModel?>? {
    TODO("Not yet implemented")
  }

  override fun getGroupsCount(p0: Boolean?): Long? {
    TODO("Not yet implemented")
  }

  override fun getGroupsCountByNameContaining(p0: String?): Long? {
    TODO("Not yet implemented")
  }

  override fun getTopLevelGroupsStream(): Stream<GroupModel?>? {
    TODO("Not yet implemented")
  }

  override fun getTopLevelGroupsStream(
    p0: Int?,
    p1: Int?
  ): Stream<GroupModel?>? {
    TODO("Not yet implemented")
  }

  override fun removeGroup(p0: GroupModel?): Boolean {
    TODO("Not yet implemented")
  }

  override fun moveGroup(p0: GroupModel?, p1: GroupModel?) {
    TODO("Not yet implemented")
  }

  override fun getClientScopesStream(): Stream<ClientScopeModel?>? {
    TODO("Not yet implemented")
  }

  override fun addClientScope(p0: String?): ClientScopeModel? {
    TODO("Not yet implemented")
  }

  override fun addClientScope(p0: String?, p1: String?): ClientScopeModel? {
    TODO("Not yet implemented")
  }

  override fun removeClientScope(p0: String?): Boolean {
    TODO("Not yet implemented")
  }

  override fun getClientScopeById(p0: String?): ClientScopeModel? {
    TODO("Not yet implemented")
  }

  override fun addDefaultClientScope(p0: ClientScopeModel?, p1: Boolean) {
    TODO("Not yet implemented")
  }

  override fun removeDefaultClientScope(p0: ClientScopeModel?) {
    TODO("Not yet implemented")
  }

  override fun createOrUpdateRealmLocalizationTexts(
    p0: String?,
    p1: Map<String?, String?>?
  ) {
    TODO("Not yet implemented")
  }

  override fun removeRealmLocalizationTexts(p0: String?): Boolean {
    TODO("Not yet implemented")
  }

  override fun getRealmLocalizationTexts(): Map<String?, Map<String?, String?>?>? {
    TODO("Not yet implemented")
  }

  override fun getRealmLocalizationTextsByLocale(p0: String?): Map<String?, String?>? {
    TODO("Not yet implemented")
  }

  override fun getDefaultClientScopesStream(p0: Boolean): Stream<ClientScopeModel?>? {
    TODO("Not yet implemented")
  }

  override fun createClientInitialAccessModel(
    p0: Int,
    p1: Int
  ): ClientInitialAccessModel? {
    TODO("Not yet implemented")
  }

  override fun getClientInitialAccessModel(p0: String?): ClientInitialAccessModel? {
    TODO("Not yet implemented")
  }

  override fun removeClientInitialAccessModel(p0: String?) {
    TODO("Not yet implemented")
  }

  override fun getClientInitialAccesses(): Stream<ClientInitialAccessModel?>? {
    TODO("Not yet implemented")
  }

  override fun decreaseRemainingCount(p0: ClientInitialAccessModel?) {
    TODO("Not yet implemented")
  }

  override fun getRole(p0: String?): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun addRole(p0: String?): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun addRole(p0: String?, p1: String?): RoleModel? {
    TODO("Not yet implemented")
  }

  override fun removeRole(p0: RoleModel?): Boolean {
    TODO("Not yet implemented")
  }

  override fun getRolesStream(): Stream<RoleModel?>? {
    TODO("Not yet implemented")
  }

  override fun getRolesStream(
    p0: Int?,
    p1: Int?
  ): Stream<RoleModel?>? {
    TODO("Not yet implemented")
  }

  override fun searchForRolesStream(
    p0: String?,
    p1: Int?,
    p2: Int?
  ): Stream<RoleModel?>? {
    TODO("Not yet implemented")
  }

  companion object {
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