package br.com.oneguy.account.model.persist

import br.com.oneguy.account.model.persist.id.BankAccountId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "accounts")
data class BankAccount(
    @field:Id val id: BankAccountId,
    val since: LocalDateTime = LocalDateTime.now(),
    val expiredAt: LocalDateTime? = null
)