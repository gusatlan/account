package br.com.oneguy.account.model.dto

import br.com.oneguy.account.model.persist.EventTypeEnum


class PersistRequestBankAccountDTO(
    type: EventTypeEnum = EventTypeEnum.INSERT,
    entity: BankAccountDTO = BankAccountDTO()
) : PersistRequest<BankAccountDTO>(type, entity)