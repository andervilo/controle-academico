package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record EscolaRequest(
        @NotBlank(message = "Nome da instituição é obrigatório") String nomeInstituicao,
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
