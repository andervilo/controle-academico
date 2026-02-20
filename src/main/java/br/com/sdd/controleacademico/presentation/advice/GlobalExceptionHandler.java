package br.com.sdd.controleacademico.presentation.advice;

import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegraDeNegocioException.class)
    ProblemDetail handleRegraDeNegocio(RegraDeNegocioException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(422);
        problemDetail.setTitle("Regra de Negócio Violada");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("https://controle-academico.com/erro-negocio"));
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleGeral(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setTitle("Erro Interno do Servidor");
        problemDetail.setDetail("Ocorreu um erro inesperado. Contate o suporte.");
        return problemDetail;
    }
}
