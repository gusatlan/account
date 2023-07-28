package br.com.oneguy.account.repository

import br.com.oneguy.account.model.persist.BankAccount
import br.com.oneguy.account.model.persist.id.BankAccountId
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface BankAccountRepository : ReactiveCrudRepository<BankAccount, BankAccountId> {
    fun findByIdCustomerId(customerId: String): Flux<BankAccount>
    fun findByIdAccountId(accountId: String): Flux<BankAccount>
    fun findByIdCustomerIdAndIdAccountId(customerId: String, accountId: String): Flux<BankAccount>
}
