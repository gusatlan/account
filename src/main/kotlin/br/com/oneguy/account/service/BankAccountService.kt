package br.com.oneguy.account.service

import br.com.oneguy.account.mapper.transform
import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.dto.PersistRequestBankAccountDTO
import br.com.oneguy.account.model.persist.BankAccount
import br.com.oneguy.account.model.persist.EventTypeEnum
import br.com.oneguy.account.repository.BankAccountRepository
import br.com.oneguy.account.util.cleanCodeText
import br.com.oneguy.account.util.mapper
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BankAccountService(
    private val repository: BankAccountRepository,
    private val bankAccountEventService: BankAccountEventService,
    private val bridge: StreamBridge
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        //        private const val TOPIC: String = "upsertBankAccountPersist-out-0"
        private const val TOPIC: String = "account-upsert-bank-account"
    }

    fun find(customerId: String? = null, accountId: String? = null): Flux<BankAccountDTO> {
        val items = if (customerId != null && accountId != null) {
            repository.findByIdCustomerIdAndIdAccountId(
                customerId = cleanCodeText(customerId).lowercase(),
                accountId = cleanCodeText(accountId).lowercase()
            )
        } else if (customerId != null) {
            repository.findByIdCustomerId(cleanCodeText(customerId).lowercase())
        } else if (accountId != null) {
            repository.findByIdAccountId(cleanCodeText(accountId).lowercase())
        } else {
            Flux.empty()
        }

        return items.flatMap(this::applyEvents)
            .doOnComplete {
                logger.info("BankAccountService:find: ($customerId, $accountId)")
            }
    }

    private fun applyEvents(bankAccount: BankAccount): Mono<BankAccountDTO> {
        return bankAccountEventService.find(
            customerId = bankAccount.id.customerId,
            accountId = bankAccount.id.accountId
        ).collectList()
            .map { events ->
                bankAccount.transform(events)
            }

    }

    fun send(value: BankAccountDTO, type: EventTypeEnum) {
        val item =
            PersistRequestBankAccountDTO(
                type = type,
                entity = value
            )

        logger.info("BankAccountService:send $item")
        bridge.send(TOPIC, item)
    }
}