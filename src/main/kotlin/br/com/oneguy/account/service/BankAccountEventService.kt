package br.com.oneguy.account.service

import br.com.oneguy.account.model.persist.BankAccountEvent
import br.com.oneguy.account.model.persist.id.BankAccountEventId
import br.com.oneguy.account.repository.BankAccountEventRepository
import br.com.oneguy.account.util.cleanCodeText
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Service
class BankAccountEventService(
    private val repository: BankAccountEventRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun find(customerId: String? = null, accountId: String? = null, eventId: String? = null): Flux<BankAccountEvent> {
        return if (customerId != null && accountId != null) {
            repository.findById(
                BankAccountEventId(
                    customerId = customerId,
                    accountId = accountId,
                    eventId = eventId!!
                )
            ).toFlux()
        } else if (customerId != null) {
            repository.findByIdCustomerId(cleanCodeText(customerId).lowercase())
        } else if (accountId != null) {
            repository.findByIdAccountId(cleanCodeText(accountId).lowercase())
        } else if (eventId != null) {
            repository.findByIdEventId(cleanCodeText(eventId).lowercase())
        } else {
            Flux.empty()
        }
            .doOnComplete { logger.info("BankAccountEventService:find: ($customerId, $accountId, $eventId)") }
    }
}