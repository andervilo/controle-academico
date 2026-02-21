package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "config_escola")
@Getter
@Setter
@NoArgsConstructor
public class EscolaEntity {
    @Id
    private UUID id;

    @Column(name = "nome_instituicao", nullable = false)
    private String nomeInstituicao;

    private String cnpj;
    private String inep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String telefone;

    @Column(name = "email_contato")
    private String emailContato;

    @Column(name = "logotipo_base64")
    private String logotipoBase64;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
