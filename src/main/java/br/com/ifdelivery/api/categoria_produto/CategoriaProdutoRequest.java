package br.com.ifdelivery.api.categoria_produto;

import br.com.ifdelivery.modelo.categoria_produto.CategoriaProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProdutoRequest {

    private String nome;
    private String descricao;

    public CategoriaProduto build() {
        return CategoriaProduto.builder()
                .nome(nome)
                .descricao(descricao)
                .build();
    }
}
