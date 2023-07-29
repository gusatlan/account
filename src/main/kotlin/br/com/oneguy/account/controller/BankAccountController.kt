package br.com.oneguy.account.controller

import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.persist.EventTypeEnum
import br.com.oneguy.account.service.BankAccountService
import org.springframework.web.bind.annotation.*

@RestController
class BankAccountController(
    private val service: BankAccountService
) {

    @GetMapping("/account")
    fun find(
        @RequestParam("customerId", required = false) customerId: String? = null,
        @RequestParam("accountId", required = false) accountId: String? = null,
    ) = service.find(customerId, accountId)

    @PostMapping("/account")
    @PutMapping("/account")
    fun save(@RequestBody value: BankAccountDTO) =
        service.send(
            value = value,
            type = EventTypeEnum.INSERT
        )

    @DeleteMapping("/account")
    fun remove(@RequestBody value: BankAccountDTO) =
        service.send(
            value = value,
            type = EventTypeEnum.DELETE
        )
}