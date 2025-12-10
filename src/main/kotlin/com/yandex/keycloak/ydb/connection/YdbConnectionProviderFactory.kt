package com.yandex.keycloak.ydb.connection

import org.keycloak.provider.ProviderFactory

interface YdbConnectionProviderFactory<T : YdbConnectionProvider> : ProviderFactory<T>
