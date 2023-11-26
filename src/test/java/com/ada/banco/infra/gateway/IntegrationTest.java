package com.ada.banco.infra.gateway;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.gateway.EmailGateway;
import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.usecase.CriarNovaConta;
import com.ada.banco.domain.usecase.FazerTransacao;
import com.ada.banco.infra.controller.ContaController;
import com.ada.banco.infra.gateway.bd.ContaGatewayDatabase;
import com.ada.banco.infra.gateway.bd.ContaRepository;
import com.ada.banco.infra.gateway.bd.TransacaoGatewayDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IntegrationTest {
    @Mock
    ContaGateway contaGateway;
    @Mock
    EmailGateway emailGateway;
    @Mock
    TransacaoGateway transacaoGateway;
    @Mock
    TransacaoGatewayDatabase transacaoGatewayDatabase;
    @Mock
    ContaRepository contaRepository;
    @InjectMocks
    ContaGatewayDatabase contaGatewayDatabase;
   @Test
    public void deveChamarListaDeTransacao() throws Exception {
       //Given

       Conta conta = new Conta(1L, 3L, BigDecimal.ZERO, "Pedro", "222222222");
       CriarNovaConta criarNovaConta= new CriarNovaConta(contaGateway,emailGateway);
       criarNovaConta.execute(conta);
       Conta conta2 = new Conta(2L, 4L, BigDecimal.ZERO, "Joao", "1111111111");
       criarNovaConta.execute(conta2);
       when(contaGateway.buscarPorCpf("222222222")).thenReturn(conta);
       when(contaGateway.buscarPorCpf("1111111111")).thenReturn(conta2);

       FazerTransacao fazerTransacao=new FazerTransacao(contaGateway,emailGateway,transacaoGateway,transacaoGatewayDatabase);
       fazerTransacao.depositar("222222222",BigDecimal.valueOf(200));
       fazerTransacao.transferir("222222222","1111111111",200);

       System.out.println(transacaoGatewayDatabase.obterTransacoesPorContaOrigem(contaGatewayDatabase.buscarPorCpf("222222222")));
       Assertions.assertNotNull(conta.getSaldo());


   }
}
