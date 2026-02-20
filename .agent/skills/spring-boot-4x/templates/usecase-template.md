# Use Case Template

## Name
<UseCaseName>

## Responsibility
Orquestrar <ação> garantindo <invariantes>.

## Inputs
- <Input DTO>

## Outputs
- <Output DTO>

## Steps
1. Validar pré-condições de negócio
2. Carregar agregados necessários via ports
3. Executar regra/invariante no domínio
4. Persistir alterações via ports
5. Emitir evento (se aplicável)
6. Retornar resposta

## Errors
- NotFoundException
- BusinessRuleViolationException (409)
- ForbiddenException (403)
