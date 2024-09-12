package br.com.ifdelivery.api.pedido;

import br.com.ifdelivery.modelo.item_pedido.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoRequest {

    private Long produtoId;

    private Integer quantidade;

    public ItemPedido build(){
        return ItemPedido.builder()
                .quantidade(quantidade)
                .build();
    }
}
