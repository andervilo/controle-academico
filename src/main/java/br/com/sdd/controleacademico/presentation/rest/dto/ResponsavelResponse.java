package br.com.sdd.controleacademico.presentation.rest.dto;

import java.util.UUID;

public record ResponsavelResponse(
        UUID id,
        String nome,
        String cpf,
        String email,
        String telefone) {
}
