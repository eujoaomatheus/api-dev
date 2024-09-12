package br.com.ifdelivery.api.categoria_produto.dto;

import br.com.ifdelivery.api.produto.dto.ProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private List<ProdutoDTO> produtos = new ArrayList<>();

    public CategoriaProdutoDTO(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
}