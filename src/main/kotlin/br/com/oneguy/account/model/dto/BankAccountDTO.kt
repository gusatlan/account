package br.com.oneguy.account.model.dto

import br.com.oneguy.account.model.dto.id.BankAccountIdDTO
import java.math.BigDecimal
import java.time.LocalDateTime

data class BankAccountDTO(
    val id: BankAccountIdDTO,
    val since: LocalDateTime = LocalDateTime.now(),
    val expiredAt: LocalDateTime? = null,
    val transactions: Collection<BankAccountEventDTO> = emptySet()
) {

    val balance = computeBalance()

    private fun computeBalance(): BigDecimal {
        return transactions
            .stream()
            .sorted()
            .map(BankAccountEventDTO::computeValue)
            .reduce { a, b -> a.add(b) }
            .orElse(BigDecimal.ZERO)
    }
}