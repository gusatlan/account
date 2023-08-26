package br.com.oneguy.account.query

import br.com.oneguy.account.mapper.transformQuery
import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.dto.BankAccountEventDTO
import br.com.oneguy.account.model.dto.id.BankAccountIdDTO
import br.com.oneguy.account.model.query.BankAccountQuery
import br.com.oneguy.account.model.query.BankAccountTransactionsQuery
import br.com.oneguy.account.service.BankAccountEventService
import br.com.oneguy.account.service.BankAccountService
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

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

    @SchemaMapping(typeName = "transactions")
    fun findEvents(@Argument bankAccount: BankAccountQuery): Mono<BankAccountTransactionsQuery> {
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
}
