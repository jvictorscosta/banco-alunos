package com.ada.banco.domain.usecase;

import com.ada.banco.domain.model.Conta;

import java.math.BigDecimal;

public class RealizarDeposito {
    public Conta execute(Conta conta, BigDecimal valor) {
        // busca a conta para garantir que existe
        // adiciona o novo saldo
        // salva no banco
        // retorna com valor atualizado
        conta.depositar(valor);

        return conta;
    }
}
