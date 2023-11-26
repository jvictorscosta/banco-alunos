package com.ada.banco.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;
    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;
    private LocalDateTime dataDaSolicitacao;
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    public Transacao(Conta contaOrigem, Conta contaDestino, BigDecimal valor,TipoTransacao tipo ) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.dataDaSolicitacao = LocalDateTime.now();
        this.valor = valor;
        this.tipo=tipo;

    }

    public Transacao() {

    }
    public enum TipoTransacao {
        DEPOSITO,
        TRANSFERENCIA,
        SAQUE
    }
}
