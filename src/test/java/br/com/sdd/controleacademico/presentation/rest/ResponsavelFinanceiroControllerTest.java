package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.TestcontainersConfiguration;
import br.com.sdd.controleacademico.presentation.rest.dto.ResponsavelFinanceiroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ResponsavelFinanceiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarResponsavelFinanceiroComSucesso() throws Exception {
        var request = new ResponsavelFinanceiroRequest(
                "João da Silva",
                "12345678909", // CPF válido (mocked validation or standard 11 digits format check depends on
                               // lib, usually needs valid algo)
                // Actually @CPF requires valid checksum. Let's use a generator or a known valid
                // CPF.
                // 12345678909 is invalid.
                // Valid CPF example: 52998224725
                "joao@email.com",
                "11999999999");

        // Note: @CPF validation might fail if I don't use a valid number.
        // I will use a simple valid CPF generator approach or just a known valid one.
        // Known valid: 60232468045
        String validCpf = "60232468045";
        request = new ResponsavelFinanceiroRequest(
                "João da Silva",
                validCpf,
                "joao@email.com",
                "11999999999");

        mockMvc.perform(post("/api/v1/responsaveis-financeiros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.cpf").value(validCpf));
    }

    @Test
    void deveRetornarErroQuandoCpfInvalido() throws Exception {
        var request = new ResponsavelFinanceiroRequest(
                "João",
                "123", // Inválido
                "joao@email.com",
                "11999999999");

        mockMvc.perform(post("/api/v1/responsaveis-financeiros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
