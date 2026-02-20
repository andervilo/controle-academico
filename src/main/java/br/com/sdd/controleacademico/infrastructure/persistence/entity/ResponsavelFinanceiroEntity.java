package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "responsavel_financeiro")
@SQLDelete(sql = "UPDATE responsavel_financeiro SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelFinanceiroEntity {
    @Id
    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean deleted = false;
}
