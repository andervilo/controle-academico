package br.com.sdd.controleacademico.presentation.rest.dto;

import java.util.UUID;

public record EscolaResponse(
        UUID id,
        String nomeInstituicao,
        String cnpj,
        String inep,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String uf,
        String cep,
        String telefone,
        String emailContato,
        String logotipoBase64) {
}
