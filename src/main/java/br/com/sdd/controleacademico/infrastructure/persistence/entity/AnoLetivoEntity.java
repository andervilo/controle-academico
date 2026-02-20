package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ano_letivo")
@SQLDelete(sql = "UPDATE ano_letivo SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnoLetivoEntity {
    @Id
    private UUID id;
    private int ano;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted = false;
}
