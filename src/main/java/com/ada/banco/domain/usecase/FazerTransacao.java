package com.ada.banco.domain.usecase;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.gateway.EmailGateway;
import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import com.ada.banco.infra.gateway.bd.ContaGatewayDatabase;
import com.ada.banco.infra.gateway.bd.TransacaoGatewayDatabase;

import java.math.BigDecimal;

public class FazerTransacao {
    private ContaGatewayDatabase contaGatewayDatabase;
    private ContaGateway contaGateway;
    private EmailGateway emailGateway;
    private TransacaoGateway transacaoGateway;
    private TransacaoGatewayDatabase transacaoGatewayDatabase;

    public FazerTransacao(ContaGateway contaGateway, EmailGateway emailGateway, TransacaoGateway transacaoGateway, TransacaoGatewayDatabase transacaoGatewayDatabase) {
        this.contaGateway = contaGateway;
        this.emailGateway = emailGateway;
        this.transacaoGateway = transacaoGateway;
        this.transacaoGatewayDatabase = transacaoGatewayDatabase;
    }

    public void transferir(String conta1, String conta2, double i) throws Exception {
        Conta contaOrigem = contaGateway.buscarPorCpf(conta1);
        Conta contaDestino = contaGateway.buscarPorCpf(conta2);

        if(contaOrigem.getSaldo().doubleValue()<i){
            throw new Exception("Saldo Insuficiente");
        }


        BigDecimal novoSaldo =contaDestino.getSaldo().add(BigDecimal.valueOf(i));
        contaDestino.setSaldo(novoSaldo);
        contaOrigem.setSaldo(contaOrigem.getSaldo().add(BigDecimal.valueOf(i).negate()));
        System.out.println("Transferencia realizada com sucesso");//remover
        Transacao transacao = new Transacao(contaOrigem,contaDestino,BigDecimal.valueOf(i),Transacao.TipoTransacao.TRANSFERENCIA);
        transacaoGatewayDatabase.salvar(transacao);
    }
    public void depositar(String contaCpf, BigDecimal valor) throws Exception {
        Conta conta = contaGateway.buscarPorCpf(contaCpf);
        if (valor.compareTo(BigDecimal.ZERO) == -1 || valor.compareTo(BigDecimal.ZERO) == 0){
            throw new Exception("Não foi possível depositar.");
        }

        valor = conta.getSaldo().add(valor);
        conta.setSaldo(valor);

        Transacao transacao = new Transacao(conta,null,valor, Transacao.TipoTransacao.DEPOSITO);
        transacaoGatewayDatabase.salvar(transacao);

    }
    public void sacar(String contaCpf  , BigDecimal valor) throws Exception {
        Conta conta = contaGateway.buscarPorCpf(contaCpf);
        if (valor.compareTo(BigDecimal.ZERO) == -1 ||
                valor.compareTo(BigDecimal.ZERO) == 0 ||
                valor.compareTo(conta.getSaldo()) == 1){
            throw new Exception("Não foi possível sacar.");
        }

        valor = conta.getSaldo().subtract(valor);
        conta.setSaldo(valor);

        Transacao transacao = new Transacao(conta,null,valor,Transacao.TipoTransacao.SAQUE);
        transacaoGatewayDatabase.salvar(transacao);

    }
}
