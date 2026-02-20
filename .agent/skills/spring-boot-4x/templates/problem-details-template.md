# Problem Details Template (application/problem+json)

Fields:
- type: URI que identifica o tipo do erro
- title: resumo curto
- status: HTTP status
- detail: mensagem para humanos (sem dados sensíveis)
- instance: caminho/trace id
- errors: lista de erros de validação (field/message)

Validation example:
errors: [{ "field": "name", "message": "must not be blank" }]
