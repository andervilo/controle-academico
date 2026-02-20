package br.com.sdd.controleacademico.domain.model;

import java.time.LocalTime;
import java.util.UUID;

public class ProfessorDisponibilidade {
    private UUID id;
    private UUID professorId;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public ProfessorDisponibilidade(UUID id, UUID professorId, DiaSemana diaSemana,
            LocalTime horaInicio, LocalTime horaFim) {
        this.id = id;
        this.professorId = professorId;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        validate();
    }

    public static ProfessorDisponibilidade criar(UUID professorId, DiaSemana diaSemana,
            LocalTime horaInicio, LocalTime horaFim) {
        return new ProfessorDisponibilidade(UUID.randomUUID(), professorId, diaSemana, horaInicio, horaFim);
    }

    private void validate() {
        if (professorId == null)
            throw new IllegalArgumentException("Professor é obrigatório");
        if (diaSemana == null)
            throw new IllegalArgumentException("Dia da semana é obrigatório");
        if (horaInicio == null)
            throw new IllegalArgumentException("Hora de início é obrigatória");
        if (horaFim == null)
            throw new IllegalArgumentException("Hora de fim é obrigatória");
        if (!horaFim.isAfter(horaInicio))
            throw new IllegalArgumentException("Hora de fim deve ser após hora de início");
    }

    public boolean cobreHorario(DiaSemana dia, LocalTime inicio, LocalTime fim) {
        return this.diaSemana == dia && !this.horaInicio.isAfter(inicio) && !this.horaFim.isBefore(fim);
    }

    public UUID getId() {
        return id;
    }

    public UUID getProfessorId() {
        return professorId;
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
