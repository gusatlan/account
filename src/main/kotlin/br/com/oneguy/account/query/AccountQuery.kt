package br.com.oneguy.account.query

import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.query.BankAccountEventQuery
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

    @QueryMapping(name = "findAccountById")
    fun findAccountById(@Argument customerId: String, @Argument accountId: String): Mono<BankAccountDTO> {
        return accountService.find(
            customerId = customerId,
            accountId = accountId,
            retrieveEvents = false
        ).toMono()
    }

    @SchemaMapping(value = "transactions")
    fun findEvents(@Argument bankAccount: BankAccountDTO): Flux<BankAccountEventQuery> {
        return accountEventService.find(
            customerId = bankAccount.id.customerId,
            accountId = bankAccount.id.accountId
        )
            .map {
                BankAccountEventQuery(
                    id = it.id,
                    type = it.type,
                    date = it.date,
                    value = it.value,
                    bankAccount = bankAccount
                )
            }
    }
}
