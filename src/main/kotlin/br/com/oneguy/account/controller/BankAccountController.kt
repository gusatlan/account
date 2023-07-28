package br.com.oneguy.account.controller

import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.persist.EventTypeEnum
import br.com.oneguy.account.service.BankAccountService
import org.springframework.web.bind.annotation.*

@RestController
class BankAccountController(
    private val bankAccountService: BankAccountService
) {

    @GetMapping("/account")
    fun findBankAccount(
        @RequestParam("customerId", required = false) customerId: String? = null,
        @RequestParam("accountId", required = false) accountId: String? = null,
    ) = bankAccountService.find(customerId, accountId)

    @PostMapping("/account")
    @PutMapping("/account")
    fun saveBankAccount(@RequestBody value: BankAccountDTO) =
        bankAccountService.send(
            value = value,
            type = EventTypeEnum.INSERT
        )

    @DeleteMapping("/account")
    fun removeBankAccount(@RequestBody value: BankAccountDTO) =
        bankAccountService.send(
            value = value,
            type = EventTypeEnum.DELETE
        )
}