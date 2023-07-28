package br.com.oneguy.account.model.dto.id

import br.com.oneguy.account.util.cleanCodeText

class BankAccountIdDTO(
    customerId: String,
    accountId: String
) {

    val customerId = cleanCodeText(customerId).lowercase()
    val accountId = cleanCodeText(accountId).lowercase()

    override fun hashCode() = customerId.hashCode() or accountId.hashCode()
    override fun equals(other: Any?) =
        other != null && other is BankAccountIdDTO && customerId == other.customerId && accountId == other.accountId

    override fun toString() = """{"customerId": "$customerId", "accountId": "$accountId"}"""
}