package br.com.sdd.controleacademico.domain.model;

import java.util.List;

public record PaginationResult<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int size,
        int number) {
}
