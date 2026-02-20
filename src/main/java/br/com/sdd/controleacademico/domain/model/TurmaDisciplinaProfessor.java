package br.com.sdd.controleacademico.domain.model;

import java.time.LocalTime;
import java.util.UUID;

public class TurmaDisciplinaProfessor {
    private UUID id;
    private UUID turmaId;
    private UUID disciplinaId;
    private UUID professorId;
    private UUID anoLetivoId;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public TurmaDisciplinaProfessor(UUID id, UUID turmaId, UUID disciplinaId, UUID professorId,
            UUID anoLetivoId, DiaSemana diaSemana,
            LocalTime horaInicio, LocalTime horaFim) {
        this.id = id;
        this.turmaId = turmaId;
        this.disciplinaId = disciplinaId;
        this.professorId = professorId;
        this.anoLetivoId = anoLetivoId;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        validate();
    }

    public static TurmaDisciplinaProfessor criar(UUID turmaId, UUID disciplinaId, UUID professorId,
            UUID anoLetivoId, DiaSemana diaSemana,
            LocalTime horaInicio, LocalTime horaFim) {
        return new TurmaDisciplinaProfessor(UUID.randomUUID(), turmaId, disciplinaId, professorId,
                anoLetivoId, diaSemana, horaInicio, horaFim);
    }

    private void validate() {
        if (turmaId == null)
            throw new IllegalArgumentException("Turma é obrigatória");
        if (disciplinaId == null)
            throw new IllegalArgumentException("Disciplina é obrigatória");
        if (professorId == null)
            throw new IllegalArgumentException("Professor é obrigatório");
        if (anoLetivoId == null)
            throw new IllegalArgumentException("Ano Letivo é obrigatório");
        if (diaSemana == null)
            throw new IllegalArgumentException("Dia da semana é obrigatório");
        if (horaInicio == null)
            throw new IllegalArgumentException("Hora de início é obrigatória");
        if (horaFim == null)
            throw new IllegalArgumentException("Hora de fim é obrigatória");
        if (!horaFim.isAfter(horaInicio))
            throw new IllegalArgumentException("Hora de fim deve ser após hora de início");
    }

    public boolean conflitaCom(DiaSemana dia, LocalTime inicio, LocalTime fim) {
        return this.diaSemana == dia && this.horaInicio.isBefore(fim) && inicio.isBefore(this.horaFim);
    }

    public UUID getId() {
        return id;
    }

    public UUID getTurmaId() {
        return turmaId;
    }

    public UUID getDisciplinaId() {
        return disciplinaId;
    }

    public UUID getProfessorId() {
        return professorId;
    }

    public UUID getAnoLetivoId() {
        return anoLetivoId;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }
}
