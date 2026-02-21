package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.TestcontainersConfiguration;
import br.com.sdd.controleacademico.presentation.rest.dto.ResponsavelRequest;
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
class ResponsavelControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void deveCriarResponsavelComSucesso() throws Exception {
                var request = new ResponsavelRequest(
                                "João da Silva",
                                "12345678909",
                                "joao@email.com",
                                "11999999999");

                String validCpf = "60232468045";
                request = new ResponsavelRequest(
                                "João da Silva",
                                validCpf,
                                "joao@email.com",
                                "11999999999");

                mockMvc.perform(post("/api/v1/responsaveis")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.nome").value("João da Silva"))
                                .andExpect(jsonPath("$.cpf").value(validCpf));
        }

        @Test
        void deveRetornarErroQuandoCpfInvalido() throws Exception {
                var request = new ResponsavelRequest(
                                "João",
                                "123", // Inválido
                                "joao@email.com",
                                "11999999999");

                mockMvc.perform(post("/api/v1/responsaveis")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest());
        }
}
