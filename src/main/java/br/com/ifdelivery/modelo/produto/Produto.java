package br.com.ifdelivery.modelo.produto;


import br.com.ifdelivery.modelo.categoria_produto.CategoriaProduto;
import br.com.ifdelivery.modelo.restaurante.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import br.com.ifdelivery.util.entity.EntidadeAuditavel;

@Entity
@Table(name = "Produtos")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto extends EntidadeAuditavel {

    @Column
    private String codigo;
    @Column
    private String titulo;

    @Column
    private String descricao;
    @Column
    private String imagem;
    @Column
    private Double valorUnitario;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;


    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_produto_id")
    private CategoriaProduto categoriaProduto;

}
