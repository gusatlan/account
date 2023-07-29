package br.com.oneguy.account.mapper

import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.dto.BankAccountEventDTO
import br.com.oneguy.account.model.dto.id.BankAccountEventIdDTO
import br.com.oneguy.account.model.dto.id.BankAccountIdDTO
import br.com.oneguy.account.model.persist.BankAccount
import br.com.oneguy.account.model.persist.BankAccountEvent
import br.com.oneguy.account.model.persist.id.BankAccountEventId
import br.com.oneguy.account.model.persist.id.BankAccountId

fun BankAccountId.transform(): BankAccountIdDTO {
    return BankAccountIdDTO(
        customerId = customerId,
        accountId = accountId
    )
}

fun BankAccountIdDTO.transform(): BankAccountId {
    return BankAccountId(
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

fun BankAccount.transform(events: Collection<BankAccountEventDTO>): BankAccountDTO {
    return BankAccountDTO(
        id = id.transform(),
        since = since,
        expiredAt = expiredAt,
        transactions = events
    )
}

fun BankAccountDTO.transform(): BankAccount {
    return BankAccount(
        id = id.transform(),
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

fun BankAccountEventDTO.transform(): BankAccountEvent {
    return BankAccountEvent(
        id = id.transform(),
        type = type,
        date = date,
        value = value
    )
}




