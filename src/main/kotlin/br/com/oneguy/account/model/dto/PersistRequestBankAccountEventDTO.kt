package br.com.oneguy.account.model.dto

import br.com.oneguy.account.model.persist.EventTypeEnum


class PersistRequestBankAccountEventDTO(
    type: EventTypeEnum = EventTypeEnum.INSERT,
    entity: BankAccountEventDTO = BankAccountEventDTO()
) : PersistRequest<BankAccountEventDTO>(type, entity)