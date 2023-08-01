package br.com.oneguy.account.model

import br.com.oneguy.account.model.dto.BankAccountDTO
import br.com.oneguy.account.model.dto.PersistRequestBankAccountDTO
import br.com.oneguy.account.model.dto.id.BankAccountIdDTO
import br.com.oneguy.account.model.persist.BankAccount
import br.com.oneguy.account.model.persist.EventTypeEnum
import br.com.oneguy.account.model.persist.id.BankAccountId
import br.com.oneguy.account.util.mapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class BankAccountTest {

    @Test
    fun shouldMarshallUnmarshallBankAccount() {
        val obj1 = BankAccount(
            id = BankAccountId(
                customerId = "abc",
                accountId = "abc-def"
            ),
            since = LocalDateTime.of(2023, 7, 31, 0, 0)
        )

        val json = mapper.writeValueAsString(obj1)
        val unmarshall = mapper.readValue(json, BankAccount::class.java)

        Assertions.assertEquals(obj1.id, unmarshall.id)
    }

    @Test
    fun shouldMarshallUnmarshallPersistRequest() {
        val obj1 = PersistRequestBankAccountDTO(
            type = EventTypeEnum.INSERT,
            entity = BankAccountDTO(
                id = BankAccountIdDTO(
                    customerId = "abc",
                    accountId = "abc-def"
                ),
                since = LocalDateTime.of(2023, 7, 31, 0, 0)
            )
        )

        val json = mapper.writeValueAsString(obj1)
        val unmarshall = mapper.readValue(json, PersistRequestBankAccountDTO::class.java)

        Assertions.assertEquals(obj1.entity, unmarshall.entity)
    }

}