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

    fun findByDateBetween(begin: LocalDateTime, end: LocalDateTime) : Flux<BankAccountEvent>
}