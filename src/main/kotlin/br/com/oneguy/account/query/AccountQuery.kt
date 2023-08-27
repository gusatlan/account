package br.com.oneguy.account.query

import br.com.oneguy.account.mapper.transform
import br.com.oneguy.account.mapper.transformQuery
import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.dto.BankAccountEventDTO
import br.com.oneguy.account.model.dto.id.BankAccountIdDTO
import br.com.oneguy.account.model.persist.EventTransactionTypeEnum
import br.com.oneguy.account.model.persist.EventTypeEnum
import br.com.oneguy.account.model.query.BankAccountEventIdQuery
import br.com.oneguy.account.model.query.BankAccountEventQuery
import br.com.oneguy.account.model.query.BankAccountQuery
import br.com.oneguy.account.model.query.BankAccountTransactionsQuery
import br.com.oneguy.account.service.BankAccountEventService
import br.com.oneguy.account.service.BankAccountService
import br.com.oneguy.account.util.toLocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime

@Controller
class AccountQuery(
    private val accountService: BankAccountService,
    private val accountEventService: BankAccountEventService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    @QueryMapping
    fun findAccountAll(): Flux<BankAccountQuery> =
        accountService
            .find(retrieveEvents = false)
            .map(BankAccountDTO::transformQuery)
            .doOnNext {
                logger.info("findAccount: $it")
            }

    @QueryMapping
    fun findAccountById(@Argument bankAccountId: BankAccountIdDTO): Mono<BankAccountQuery> {
        return accountService.find(
            customerId = bankAccountId.customerId,
            accountId = bankAccountId.accountId,
            retrieveEvents = false
        )
            .toMono()
            .map(BankAccountDTO::transformQuery)
            .doOnNext {
                logger.info("findAccountById [${bankAccountId}]: $it")
            }
    }

    @SchemaMapping(typeName = "BankAccount", field = "transactions")
    fun transactions(bankAccount: BankAccountQuery): Mono<BankAccountTransactionsQuery> {
        return findEvents(bankAccount)
    }

    private fun findEvents(bankAccount: BankAccountQuery): Mono<BankAccountTransactionsQuery> {
        return accountEventService.find(
            customerId = bankAccount.id.customerId,
            accountId = bankAccount.id.accountId
        )
            .doOnNext {
                logger.debug("findEvents event $it")
            }
            .map(BankAccountEventDTO::transformQuery)
            .collectList()
            .flatMap { events ->
                Mono.just(BankAccountTransactionsQuery(events))
            }
            .doOnNext {
                logger.info("findEvents [${bankAccount.id}]: $it")
            }
            .doOnError {
                logger.error("findEvents [${bankAccount.id}]: $it")
            }
    }

    @MutationMapping(name = "upsertBankAccount")
    fun upsertBankAccount(
        customerId: String,
        accountId: String,
        since: String?,
        expiredAt: String?
    ): BankAccountQuery {
        return upsertBankAccount(
            bankAccount = BankAccountQuery(
                id = BankAccountIdDTO(
                    customerId = customerId,
                    accountId = accountId
                ),
                since = since?.toLocalDateTime() ?: LocalDateTime.now(),
                expiredAt = expiredAt?.toLocalDateTime()
            )
        )
    }

    private fun upsertBankAccount(
        bankAccount: BankAccountQuery,
        eventType: EventTypeEnum = EventTypeEnum.INSERT
    ): BankAccountQuery {
        accountService.send(
            value = bankAccount.transform(),
            type = eventType
        )
        return bankAccount
    }

    @MutationMapping(name = "upsertBankAccountEvent")
    fun upsertBankAccountEvent(
        customerId: String,
        accountId: String,
        eventId: String,
        type: EventTransactionTypeEnum,
        date: String,
        value: Double
    ): Mono<BankAccountEventQuery> {
        return upsertBankAccountEvent(
            bankAccountEvent = BankAccountEventQuery(
                id = BankAccountEventIdQuery(
                    customerId = customerId,
                    accountId = accountId,
                    eventId = eventId
                ),
                type = type,
                date = date.toLocalDateTime(),
                value = value.toBigDecimal()
            )
        )
    }

    private fun upsertBankAccountEvent(
        bankAccountEvent: BankAccountEventQuery,
        eventType: EventTypeEnum = EventTypeEnum.INSERT
    ): Mono<BankAccountEventQuery> {
        accountEventService.send(
            value = bankAccountEvent.transform(),
            type = eventType
        )
        return Mono.just(bankAccountEvent)
    }
}

