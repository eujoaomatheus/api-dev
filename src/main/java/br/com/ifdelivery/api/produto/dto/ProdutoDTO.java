package br.com.ifdelivery.api.produto.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String codigo;
    private String titulo;
    private String descricao;
    private String imagem;
    private Double valorUnitario;
    @JsonIgnore
    private String categoriaNome;

    public ProdutoDTO(Long id, String codigo, String titulo, String descricao, String imagem, Double valorUnitario) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
        this.valorUnitario = valorUnitario;
    }
}