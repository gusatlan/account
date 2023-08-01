package br.com.oneguy.account.model.dto

import br.com.oneguy.account.model.persist.EventTypeEnum


abstract class PersistRequest<T:Any>(
    val type: EventTypeEnum = EventTypeEnum.INSERT,
    val entity: T
)