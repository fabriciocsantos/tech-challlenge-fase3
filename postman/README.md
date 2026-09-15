# Collection do Postman

Arquivo: `tech-challenge-fase3.postman_collection.json`

## Como importar

1. Abra o Postman (ou Insomnia, que também importa collections v2.1).
2. `Import` → selecione o arquivo `tech-challenge-fase3.postman_collection.json`.
3. Não precisa importar nenhum environment separado — as URLs (`agendamento_url`,
   `notificacao_url`) já vêm como variáveis da própria collection, com os
   valores padrão de execução local (`localhost:8080` e `localhost:8082`).

## Antes de rodar

Suba a infraestrutura e os dois serviços conforme o `README.md` da raiz do
repositório (`docker compose up -d`, depois `./mvnw spring-boot:run` em cada
serviço). A collection assume um banco recém-criado pelo seed padrão, onde
`medico` tem id `1`, `enfermeiro` id `2` e `paciente` id `3`.

## Ordem sugerida

A pasta **Agendamento Service (GraphQL)** está organizada assim:

1. **Autenticação** — confirma que sem usuário/senha a API responde `401`.
2. **Consultas - Queries** — testa o histórico do paciente (próprio e de
   terceiros) e as consultas futuras.
3. **Consultas - Mutations** — registra e edita consultas com cada perfil.
   A requisição **"Registrar consulta (médico)"** tem um script na aba
   *Tests* que salva o `id` da consulta criada na variável
   `consulta_id` da collection — rode ela antes de **"Editar consulta"**,
   senão a variável fica vazia.

A pasta **Notificacao Service (REST)** testa a listagem de lembretes com e
sem autenticação. Como os lembretes só são criados pelo consumidor Kafka
(sem endpoint manual), rode as mutations do agendamento-service primeiro se
quiser ver dados aparecendo ali.

## O que cada requisição valida

| Requisição | Espera |
|---|---|
| Sem autenticação | `401` |
| Histórico do próprio paciente | `200` com os dados |
| Histórico de outro paciente | erro `FORBIDDEN` |
| Registrar consulta (médico / enfermeiro) | `200`, consulta criada |
| Registrar consulta (paciente) | erro `FORBIDDEN` |
| Editar consulta (médico / enfermeiro) | `200`, consulta atualizada |
| Notificações autenticado | `200` com a lista |
| Notificações sem autenticação | `401` |
