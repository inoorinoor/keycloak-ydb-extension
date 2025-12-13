package com.yandex.keycloak.ydb.realm

import com.yandex.keycloak.ydb.realm.domain.Realm
import jooq.generated.default_schema.tables.pojos.Realms

object RealmMapper {
  fun Realms.toDomain(): Realm = Realm(id, name)

  fun Realm.toPojo(): Realms = Realms(id, name)
}