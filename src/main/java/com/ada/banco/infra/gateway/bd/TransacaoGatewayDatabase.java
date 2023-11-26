package com.ada.banco.infra.gateway.bd;

import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import com.ada.banco.domain.usecase.FazerTransacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoGatewayDatabase {
    private final TransacaoRepository transacaoRepository;
    @Autowired
    public TransacaoGatewayDatabase(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public List<Transacao> obterTransacoesPorContaOrigem(Conta contaOrigem) {
        return transacaoRepository.findByContaOrigem(contaOrigem);
    }

    public void salvar(Transacao transacao) {
        transacaoRepository.save(transacao);

    }
}
