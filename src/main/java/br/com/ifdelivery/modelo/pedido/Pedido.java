package br.com.ifdelivery.modelo.pedido;

import br.com.ifdelivery.modelo.cliente.Cliente;
import br.com.ifdelivery.modelo.item_pedido.ItemPedido;
import br.com.ifdelivery.modelo.pedido.enums.MetodosPgtoEnum;
import br.com.ifdelivery.modelo.pedido.enums.StatusEntregaEnum;
import br.com.ifdelivery.modelo.pedido.enums.StatusPgtoEnum;
import br.com.ifdelivery.modelo.restaurante.Restaurante;
import br.com.ifdelivery.util.entity.EntidadeAuditavel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Pedidos")
@EntityListeners(AuditingEntityListener.class)
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido extends EntidadeAuditavel {

//    @Column
//    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private MetodosPgtoEnum metodoPagamento;

    @Column
    private Double valorTotal;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPgtoEnum statusPgto;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusEntregaEnum statusEntrega;


    @Column(name = "CEP")
    private String cep;


    @Column
    private String estado;


    @Column
    private String cidade;


    @Column
    private String bairro;


    @Column
    private String rua;


    @Column
    private String numero;


    @Column
    private String complemento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    @CreatedDate
    private LocalDateTime dataDoPedido;

    @Column
    private String observacao;

    @PostPersist
    @PostUpdate
    private void calcularValorTotal() {
        if (itens != null) {
            this.valorTotal = itens.stream()
                    .mapToDouble(ItemPedido::getSubtotal)
                    .sum();
        }
    }
}
