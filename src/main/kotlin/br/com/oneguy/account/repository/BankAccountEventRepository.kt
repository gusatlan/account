package br.com.oneguy.account.repository

import br.com.oneguy.account.model.persist.BankAccountEvent
import br.com.oneguy.account.model.persist.id.BankAccountEventId
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

interface BankAccountEventRepository : ReactiveCrudRepository<BankAccountEvent, BankAccountEventId> {

    fun findByIdEventId(eventId: String): Flux<BankAccountEvent>
    fun findByIdCustomerId(customerId: String): Flux<BankAccountEvent>
    fun findByIdAccountId(accountId: String): Flux<BankAccountEvent>
    fun findByIdCustomerIdAndIdAccountId(customerId: String, accountId:String): Flux<BankAccountEvent>
    fun findByIdCustomerIdAndIdAccountIdAndIdEventId(customerId: String, accountId:String, eventId: String): Flux<BankAccountEvent>
}