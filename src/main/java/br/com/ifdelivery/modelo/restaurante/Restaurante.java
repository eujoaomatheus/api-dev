package br.com.ifdelivery.modelo.restaurante;

import br.com.ifdelivery.modelo.acesso.Usuario;
import br.com.ifdelivery.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "Restaurantes")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurante extends EntidadeAuditavel {


    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @Column
    private String razaoSocial;

    @Column
    private String nomeFantasia;

    @Column(name = "CNPJ", unique = true)
    private String cnpj;

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

    @Column
    private boolean isActive;

    @Column
    private boolean isOpen;

    @Column
    private boolean aceitaPix;

    @Column
    private boolean aceitaCartaoCredito;

    @Column
    private boolean aceitaCartaoDebito;

    @Column
    private boolean aceitaDinheiro;

    @Column
    private boolean aceitaValeRefeicao;

    @Column
    private boolean aceitaValeAlimentacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriasEnum categoriasEnum;

    
    @Column(name = "photo_banner")
    String photoBanner;

    @Column(name = "photo_logo")
    String photoLogo;
}
