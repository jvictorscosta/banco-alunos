package com.ada.banco.infra.controller;

import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.usecase.CriarNovaConta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private CriarNovaConta criarNovaConta;

    public ContaController(CriarNovaConta criarNovaConta) {
        this.criarNovaConta = criarNovaConta;
    }

    @PostMapping
    public ResponseEntity criarConta(@RequestBody Conta conta) throws Exception {
        Conta novaConta;
        try {
            novaConta = criarNovaConta.execute(conta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }
}
