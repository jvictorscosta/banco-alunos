package com.ada.banco.domain.gateway;

import com.ada.banco.domain.model.Transacao;

import java.math.BigDecimal;

public interface TransacaoGateway {
    void transferir(String conta1, String conta2, BigDecimal valor);
    void depositar();
    void sacar();
    void salvar(Transacao transacao);
}
