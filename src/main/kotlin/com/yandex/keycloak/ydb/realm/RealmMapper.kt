package com.yandex.keycloak.ydb.realm

import com.yandex.keycloak.ydb.realm.domain.Realm
import com.yandex.keycloak.ydb.realm.domain.RealmAttribute
import jooq.generated.default_schema.tables.pojos.RealmAttributes
import jooq.generated.default_schema.tables.pojos.Realms

object RealmMapper {
  fun Realms.toDomain(): Realm = Realm(id, name)

  fun Realm.toPojo(): Realms = Realms(id, name)

  fun RealmAttributes.toDomain(): RealmAttribute = RealmAttribute(
    id = id,
    name = name,
    realmId = realmId,
    value = value,
  )
}