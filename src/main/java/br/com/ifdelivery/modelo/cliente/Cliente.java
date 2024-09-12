package br.com.ifdelivery.modelo.cliente;

import br.com.ifdelivery.modelo.acesso.Usuario;
import br.com.ifdelivery.modelo.endereco.EnderecoCliente;
import br.com.ifdelivery.util.entity.EntidadeAuditavel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "Cliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel{

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<EnderecoCliente> enderecos;

    @Column
    private String nome;

    @Column
    private Integer desconto;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column
    private LocalDate dataNascimento;

    @Column
    private String telefone;

    @Column(unique = true)
    private String cpf;

    @Column
    private String codigoAuth;

    @Column
    String photo;

}
