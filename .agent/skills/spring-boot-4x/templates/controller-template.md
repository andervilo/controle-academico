# Controller Template

- Recebe request DTO + @Valid
- Autorização (@PreAuthorize)
- Mapeia request → command/input
- Chama use case
- Mapeia output → response DTO
- Retorna status HTTP correto
