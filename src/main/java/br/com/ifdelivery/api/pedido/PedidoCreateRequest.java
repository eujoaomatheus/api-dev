package br.com.ifdelivery.api.pedido;

import br.com.ifdelivery.modelo.pedido.Pedido;
import br.com.ifdelivery.modelo.pedido.enums.MetodosPgtoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCreateRequest{
    private Long clienteId;
    private Long restauranteId;
    @Enumerated(EnumType.STRING)
    private MetodosPgtoEnum formaPgto;
    private String observacao;
    private List<ItemPedidoRequest> itens;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;

    public Pedido build(){
        return Pedido.builder()
                .metodoPagamento(formaPgto)
                .cep(cep)
                .estado(estado)
                .cidade(cidade)
                .bairro(bairro)
                .rua(rua)
                .numero(numero)
                .complemento(complemento)
                .observacao(observacao)
                .build();
    }
}
