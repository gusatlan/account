package br.com.oneguy.account.model.query

import br.com.oneguy.account.model.dto.id.BankAccountIdDTO
import java.time.LocalDateTime

data class BankAccountQuery(
    val id: BankAccountIdDTO = BankAccountIdDTO(),
    val since: LocalDateTime = LocalDateTime.now(),
    val expiredAt: LocalDateTime? = null,
    val transactions: BankAccountTransactionsQuery? = null
)