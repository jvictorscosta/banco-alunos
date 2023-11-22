package com.ada.banco.domain.usecase;

import com.ada.banco.domain.model.Conta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class RealizarDepositoTest {

    @InjectMocks
    private RealizarDeposito realizarDeposito;

    @Test
    public void deveDepositarComSucesso() {
        Conta conta = new Conta(1L, 2L, 3L, BigDecimal.ZERO, "Pedro", "123456789");
        Conta contaAtualizada = realizarDeposito.execute(conta, BigDecimal.TEN);

        Assertions.assertEquals(BigDecimal.TEN, contaAtualizada.getSaldo());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1.5", "100.15"})
    public void deveDepositarComSucesso_2(BigDecimal valor) {
        Conta conta = new Conta(1L, 2L, 3L, BigDecimal.ZERO, "Pedro", "123456789");
        Conta contaAtualizada = realizarDeposito.execute(conta, valor);

        Assertions.assertEquals(valor, contaAtualizada.getSaldo());
    }
}
