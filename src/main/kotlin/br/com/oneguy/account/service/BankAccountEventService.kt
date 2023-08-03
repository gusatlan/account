package br.com.oneguy.account.service

import br.com.oneguy.account.mapper.transform
import br.com.oneguy.account.model.dto.BankAccountEventDTO
import br.com.oneguy.account.model.dto.PersistRequestBankAccountEventDTO
import br.com.oneguy.account.model.persist.BankAccountEvent
import br.com.oneguy.account.model.persist.EventTypeEnum
import br.com.oneguy.account.repository.BankAccountEventRepository
import br.com.oneguy.account.util.cleanCodeText
import br.com.oneguy.account.util.mapper
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BankAccountEventService(
    private val repository: BankAccountEventRepository,
    private val bridge: StreamBridge
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val TOPIC: String = "account-upsert-bank-account-event"
    }

    fun find(
        customerId: String? = null,
        accountId: String? = null,
        eventId: String? = null
    ): Flux<BankAccountEventDTO> {
        val items = if (customerId != null && accountId != null && eventId != null) {
            repository.findByIdCustomerIdAndIdAccountIdAndIdEventId(
                customerId = customerId,
                accountId = accountId,
                eventId = eventId

            )
        } else if (customerId != null && accountId != null) {
            repository.findByIdCustomerIdAndIdAccountId(
                customerId = customerId,
                accountId = accountId
            )
        } else if (customerId != null) {
            repository.findByIdCustomerId(cleanCodeText(customerId).lowercase())
        } else if (accountId != null) {
            repository.findByIdAccountId(cleanCodeText(accountId).lowercase())
        } else if (eventId != null) {
            repository.findByIdEventId(cleanCodeText(eventId).lowercase())
        } else {
            Flux.empty()
        }

        return items
            .map(BankAccountEvent::transform)
            .doOnComplete {
                logger.info("BankAccountEventService:find: ($customerId, $accountId, $eventId)")
            }
    }

    fun send(value: BankAccountEventDTO, type: EventTypeEnum) {
        val item = mapper.writeValueAsString(
            PersistRequestBankAccountEventDTO(
                type = type,
                entity = value
            )
        )

        logger.info("BankAccountEventService:send $item")
        bridge.send(TOPIC, item)
    }
}