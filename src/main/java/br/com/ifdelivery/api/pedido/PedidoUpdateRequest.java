package br.com.ifdelivery.api.pedido;

import br.com.ifdelivery.modelo.pedido.Pedido;
import br.com.ifdelivery.modelo.pedido.enums.StatusEntregaEnum;
import br.com.ifdelivery.modelo.pedido.enums.StatusPgtoEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoUpdateRequest {
    @Enumerated(EnumType.STRING)
    private StatusPgtoEnum statusPgto;

    @Enumerated(EnumType.STRING)
    private StatusEntregaEnum statusEntrega;

    public Pedido build(){
        if (statusPgto == null && statusEntrega == null) {
            throw new IllegalArgumentException("Pelo menos um dos campos statusPgto ou statusEntrega deve ser fornecido");
        }
        return Pedido.builder()
                .statusPgto(statusPgto)
                .statusEntrega(statusEntrega)
                .build();
    }
}