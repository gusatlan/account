package br.com.oneguy.account.mapper

import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.dto.BankAccountEventDTO
import br.com.oneguy.account.model.dto.id.BankAccountEventIdDTO
import br.com.oneguy.account.model.dto.id.BankAccountIdDTO
import br.com.oneguy.account.model.persist.BankAccount
import br.com.oneguy.account.model.persist.BankAccountEvent
import br.com.oneguy.account.model.persist.id.BankAccountEventId
import br.com.oneguy.account.model.persist.id.BankAccountId
import br.com.oneguy.account.model.query.BankAccountEventIdQuery
import br.com.oneguy.account.model.query.BankAccountEventQuery
import br.com.oneguy.account.model.query.BankAccountQuery
import br.com.oneguy.account.model.query.BankAccountTransactionsQuery

fun BankAccountId.transform(): BankAccountIdDTO {
    return BankAccountIdDTO(
        customerId = customerId,
        accountId = accountId
    )
}

fun BankAccountEventId.transform(): BankAccountEventIdDTO {
    return BankAccountEventIdDTO(
        customerId = customerId,
        accountId = accountId,
        eventId = eventId
    )
}

fun BankAccountEventIdDTO.transform(): BankAccountEventId {
    return BankAccountEventId(
        customerId = customerId,
        accountId = accountId,
        eventId = eventId
    )
}

fun BankAccountEventIdDTO.transformQuery(): BankAccountEventIdQuery {
    return BankAccountEventIdQuery(
        customerId = customerId,
        accountId = accountId,
        eventId = eventId
    )
}

fun BankAccount.transform(events: Collection<BankAccountEventDTO> = emptySet()): BankAccountDTO {
    return BankAccountDTO(
        id = id.transform(),
        since = since,
        expiredAt = expiredAt,
        transactions = events
    )
}

fun BankAccountDTO.transformQuery(): BankAccountQuery {
    return BankAccountQuery(
        id = id,
        since = since,
        expiredAt = expiredAt
    )
}

fun BankAccountEvent.transform(): BankAccountEventDTO {
    return BankAccountEventDTO(
        id = id.transform(),
        type = type,
        date = date,
        value = value
    )
}

fun BankAccountEventDTO.transformQuery(): BankAccountEventQuery {
    return BankAccountEventQuery(
        id = id.transformQuery(),
        type = type,
        date = date,
        value = value
    )
}
