package com.yandex.keycloak.ydb.transaction

import jakarta.persistence.EntityManager

class YdbJpaTransaction(
  private val em: EntityManager
) : org.keycloak.models.KeycloakTransaction {

  override fun begin() {
    em.transaction.begin()
  }

  override fun commit() {
    em.transaction.commit()
  }

  override fun rollback() {
    if (em.transaction.isActive) {
      em.transaction.rollback()
    }
  }

  override fun setRollbackOnly() {
    if (em.transaction.isActive) {
      em.transaction.setRollbackOnly()
    }
  }

  override fun getRollbackOnly(): Boolean = em.transaction.rollbackOnly

  override fun isActive(): Boolean = em.transaction.isActive
}
