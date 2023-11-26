package com.ada.banco.domain.usecase;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.gateway.EmailGateway;
import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.infra.gateway.bd.TransacaoGatewayDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Iniciar os mocks sem necessidade de iniciar no beforeEach
public class FazerTransacaoTest {

    // mvn repository - Mockito core
    @Mock
    private ContaGateway contaGateway;
    @Mock
    private EmailGateway emailGateway;
    @Mock
    private TransacaoGateway transacaoGateway;
    @Mock
    private TransacaoGatewayDatabase transacaoGatewayDatabase;

    @InjectMocks
    private CriarNovaConta criarNovaConta;
    private FazerTransacao fazerTransacao;
    private Conta contaOrigem, contaDestino;


    @BeforeEach
    public void beforeEach() {
        this.contaOrigem =  new Conta(1L, 3L, BigDecimal.valueOf(200), "Pedro", "222222222");

        this.contaDestino = new Conta(2L, 4L, BigDecimal.ZERO, "Joao", "1111111111");
    }
    @Test
    void deveRealizarTransferencia() throws Exception {
        //Given
        FazerTransacao fazerTransacao1=new FazerTransacao(contaGateway,emailGateway,transacaoGateway, transacaoGatewayDatabase);

        //when
        when(contaGateway.buscarPorCpf("222222222")).thenReturn(contaOrigem);
        when(contaGateway.buscarPorCpf("1111111111")).thenReturn(contaDestino);
        doNothing().when(transacaoGatewayDatabase).salvar(any());

        fazerTransacao1.transferir("222222222","1111111111", 200);
        //then
        Assertions.assertAll(
                () -> Assertions.assertTrue(BigDecimal.ZERO.compareTo(this.contaOrigem.getSaldo()) == 0),
                () -> Assertions.assertTrue(BigDecimal.valueOf(200L).compareTo(this.contaDestino.getSaldo()) == 0)
        );
    }
    @Test
    void deveRealizarSaque() throws Exception {
        //Given
        FazerTransacao fazerTransacao1=new FazerTransacao(contaGateway,emailGateway,transacaoGateway,transacaoGatewayDatabase);

        //when
        when(contaGateway.buscarPorCpf("222222222")).thenReturn(contaOrigem);
        doNothing().when(transacaoGatewayDatabase).salvar(any());
        BigDecimal saldoAntigo = this.contaOrigem.getSaldo();

        fazerTransacao1.sacar("222222222", new BigDecimal(200));

        //then

        Assertions.assertAll(
                () -> Assertions.assertTrue(saldoAntigo.compareTo(BigDecimal.valueOf(200L)) == 0),
                () -> Assertions.assertTrue(BigDecimal.valueOf(0L).compareTo(this.contaOrigem.getSaldo()) == 0)
        );
    }
    @Test
    void deveRealizarDeposito() throws Exception {
        //Given
        FazerTransacao fazerTransacao1=new FazerTransacao(contaGateway,emailGateway,transacaoGateway,transacaoGatewayDatabase);

        //when
        when(contaGateway.buscarPorCpf("222222222")).thenReturn(contaOrigem);
        doNothing().when(transacaoGatewayDatabase).salvar(any());
        BigDecimal saldoAntigo = this.contaOrigem.getSaldo();

        fazerTransacao1.depositar("222222222", new BigDecimal(200));

        //then

        Assertions.assertAll(
                () -> Assertions.assertTrue(saldoAntigo.compareTo(BigDecimal.valueOf(200L)) == 0),
                () -> Assertions.assertEquals(BigDecimal.valueOf(400L), this.contaOrigem.getSaldo())
        );
    }
}