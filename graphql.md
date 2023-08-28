# Queries


## Procurar conta pelo ID
```
query {
  findAccountById(
    bankAccountId: {
      customerId: "1",
      accountId: "1"
    }
  ) {
    id {
      customerId,
      accountId
    },
    since,
    expiredAt,
    transactions {
      id {
        customerId,
        accountId,
        eventId
      },
      type,
      date,
      value
    }
  }
}
```


## Retornar todas as contas
```
query {
findAccountAll
   {
    id {
      customerId
      accountId
    },
    since
    expiredAt
    transactions {
      items {
        date
        type
        value
        id {
          eventId
          accountId
          customerId
        }
      }
    }
  }
}
```

# Mutations


## Conta

### Adicionar conta


```
mutation{
  addBankAccount(
    customerId: "22"
    accountId: "23"
  ){
    id {
      customerId
      accountId
    }
    since
    expiredAt
  }
}
```


### Atualizar conta
```
mutation{
  updateBankAccount(
    customerId: "22"
    accountId: "22"
    expiredAt: "2023-08-28T09:00:00.0"
  ){
    id {
      customerId
      accountId
    }
    since
    expiredAt
  }
}
```

### Excluir conta

```
mutation{
  deleteBankAccount(
    customerId: "22"
    accountId: "22"
  ){
    id {
      customerId
      accountId
    }
    since
    expiredAt
  }
}

```


# Transações

### Adicionar transação

```
mutation{
  addBankAccountEvent(
    customerId: "22"
    accountId: "23"
    eventId: "2223"
    type: WITHDRAWN
    date: "2023-08-28T09:30:00.0"
    value: 0.11
  ){
    id{
      customerId
      accountId
      eventId
    }
    type
    date
    value
  }
}
```

### Atualizar transação

```
mutation{
  updateBankAccountEvent(
    customerId: "22"
    accountId: "23"
    eventId: "2223"
    type: WITHDRAWN
    date: "2023-08-28T09:30:00.0"
    value: 0.10
  ){
    id{
      customerId
      accountId
      eventId
    }
    type
    date
    value
  }
}
```

### Excluir transação

```
mutation{
  deleteBankAccountEvent(
    customerId: "22"
    accountId: "23"
    eventId: "2223"
  ){
    id{
      customerId
      accountId
      eventId
    }
    type
    date
    value
  }
}
```
