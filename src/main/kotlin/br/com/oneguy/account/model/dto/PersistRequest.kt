package br.com.oneguy.account.model.dto

import br.com.oneguy.account.model.persist.EventTypeEnum


data class PersistRequest<T:Any>(
    val type: EventTypeEnum,
    val entity: T
)