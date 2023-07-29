package br.com.oneguy.account.controller

import br.com.oneguy.account.model.dto.BankAccountEventDTO
import br.com.oneguy.account.model.persist.EventTypeEnum
import br.com.oneguy.account.service.BankAccountEventService
import org.springframework.web.bind.annotation.*

@RestController
class BankAccountEventController(
    private val service: BankAccountEventService
) {

    @GetMapping("/account-event")
    fun find(
        @RequestParam("customerId", required = false) customerId: String? = null,
        @RequestParam("accountId", required = false) accountId: String? = null,
        @RequestParam("eventId", required = false) eventId: String? = null,
    ) = service.find(
        customerId = customerId,
        accountId = accountId,
        eventId = eventId
    )

    @PostMapping("/account-event")
    @PutMapping("/account-event")
    fun save(@RequestBody value: BankAccountEventDTO) =
        service.send(
            value = value,
            type = EventTypeEnum.INSERT
        )

    @DeleteMapping("/account-event")
    fun remove(@RequestBody value: BankAccountEventDTO) =
        service.send(
            value = value,
            type = EventTypeEnum.DELETE
        )
}