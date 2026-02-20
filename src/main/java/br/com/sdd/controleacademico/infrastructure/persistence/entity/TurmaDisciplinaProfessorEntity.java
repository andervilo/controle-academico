package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "turma_disciplina_professor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurmaDisciplinaProfessorEntity {

    @Id
    private UUID id;

    @Column(name = "turma_id", nullable = false)
    private UUID turmaId;

    @Column(name = "disciplina_id", nullable = false)
    private UUID disciplinaId;

    @Column(name = "professor_id", nullable = false)
    private UUID professorId;

    @Column(name = "ano_letivo_id", nullable = false)
    private UUID anoLetivoId;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;
}
