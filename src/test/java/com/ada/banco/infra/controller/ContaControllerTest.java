package com.ada.banco.infra.controller;

import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.usecase.CriarNovaConta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContaController.class)
public class ContaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
@MockBean
    private CriarNovaConta criarNovaConta;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }
    @Test
    public void deveRetornarCreatedAoCriarConta() throws Exception{
        //Given
        Conta conta = new Conta(1L, 3L, BigDecimal.ZERO, "Pedro", "222222222");
        String contaJson = objectMapper.writeValueAsString(conta);
        //when
        given(criarNovaConta.execute(any(Conta.class))).willReturn(conta);
        //Then
        mockMvc.perform(post("/contas").contentType("application/json").content(contaJson)).andExpect(status().isCreated()
        );
    }
    @Test
    public void deveRetornarBadRequestCasoDadosInvalidos()throws Exception{
        //Given
        Conta conta = new Conta(null, 3L, BigDecimal.ZERO, "Pedro", "222222222");
        String contaJson = objectMapper.writeValueAsString(conta);
        //when
        given(criarNovaConta.execute(any(Conta.class))).willThrow(new Exception("Erro ao criar conta"));
        mockMvc.perform(post("/contas")
                        .contentType("application/json")
                        .content(contaJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro ao criar conta"));
    }
}
