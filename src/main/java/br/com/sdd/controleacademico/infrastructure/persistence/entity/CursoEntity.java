package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "curso")
@SQLDelete(sql = "UPDATE curso SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoEntity {
    @Id
    private UUID id;
    private String nome;
    private String codigo;
    private String descricao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted = false;
}
