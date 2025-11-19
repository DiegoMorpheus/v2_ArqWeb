package com.example.v2_ArqWeb;

import com.example.v2_ArqWeb.domain.Aluno;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarAlunoComDadosValidos() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria Silva");
        aluno.setEmail("maria@teste.com");
        aluno.setMatricula("2025001");

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno)))
                .andExpect(status().isCreated()) // mais específico
                .andExpect(jsonPath("$.nome").value("Maria Silva"))
                .andExpect(jsonPath("$.email").value("maria@teste.com"));
    }

    @Test
    void deveRetornarErroQuandoEmailInvalido() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João");
        aluno.setEmail("joao-sem-arroba");
        aluno.setMatricula("2025002");

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno)))
                .andExpect(status().isBadRequest());
                // ajuste conforme o formato real do JSON de erro
    }

    @Test
    void deveRetornarErroQuandoAlunoNaoEncontrado() throws Exception {
        mockMvc.perform(get("/alunos/999"))
                .andExpect(status().isNotFound());
    }
}
