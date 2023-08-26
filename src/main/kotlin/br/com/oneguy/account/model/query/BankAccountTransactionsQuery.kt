package br.com.oneguy.account.model.query

import java.math.BigDecimal

class BankAccountTransactionsQuery(
    val items: Collection<BankAccountEventQuery>? = null
) {
    val balance = computeBalance()

    private fun computeBalance(): BigDecimal {
        return items
            ?.stream()
            ?.sorted()
            ?.map(BankAccountEventQuery::computeValue)
            ?.reduce { a, b -> a.add(b) }
            ?.orElse(BigDecimal.ZERO)
            ?: BigDecimal.ZERO
    }
}