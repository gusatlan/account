package br.com.oneguy.account.model.query

import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.dto.BankAccountEventDTO
import br.com.oneguy.account.model.dto.id.BankAccountEventIdDTO
import br.com.oneguy.account.model.persist.EventTransactionTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime

class BankAccountEventQuery(
    id: BankAccountEventIdDTO,
    type: EventTransactionTypeEnum,
    date: LocalDateTime,
    value: BigDecimal,
    val bankAccount: BankAccountDTO
) : BankAccountEventDTO(
    id = id,
    type = type,
    date = date,
    value = value
) {
}