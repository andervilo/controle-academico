package br.com.sdd.controleacademico.domain.exception;

public class RegraDeNegocioException extends RuntimeException {
    public RegraDeNegocioException(String message) {
        super(message);
    }
}
