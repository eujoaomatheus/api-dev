package br.com.ifdelivery.api.produto;

import br.com.ifdelivery.modelo.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {
    @NotNull
    @Size(min = 1, max = 100)
    private String titulo;

    private String descricao;

    private  String imagem;

    @NotNull
    private Double valorUnitario;

    public Produto build() {
        return Produto.builder()
                .titulo(titulo)
                .descricao(descricao)
                .valorUnitario(valorUnitario)
                .imagem(imagem)
                .build();
    }
}