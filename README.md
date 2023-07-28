# account - Micro service para transações de conta (versão "nova")
2023-07-28

Usar http://localhost:8080/swagger-ui.html

## Conceito

Desenvolvido como exemplo de utilização de *event stream*.
Nessa versão o micro service delega a atualização do banco Mongo para o account-stream.
Ficando a cargo somente a recuperação de dados para essa nova API

Para executar é necessário ter o docker na máquina

```
./run
```
