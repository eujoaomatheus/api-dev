package br.com.ifdelivery.api.cliente;

import java.time.LocalDate;
import java.util.Arrays;

import br.com.ifdelivery.modelo.acesso.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifdelivery.modelo.cliente.Cliente;
import br.com.ifdelivery.util.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    private String nome;

    @NotBlank(message = "O e-mail é de preenchimento obrigatório")
    @Email
    private String email;

    @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String password;

    private Integer desconto;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String telefone;

    private String cpf;

    private String cep;

    private String estado;

    private String cidade;

    private String bairro;

    private String rua;

    private String numero;
   
    private String complemento;

    private String tipo;

    private  String photo;

    /*private List<EnderecoCliente> enderecos;*/


    @Enumerated(EnumType.STRING)
    private Role role;

    public Usuario buildUsuario() {
        return Usuario.builder()
                .username(email)
                .password(password)
                .roles(Arrays.asList(Usuario.ROLE_CLIENTE))
                .build();
    }

    /*public EnderecoCliente buildEnderecoCliente(){
        return EnderecoCliente.builder()
                .cep(cep)
                .estado(estado)
                .cidade(cidade)
                .bairro(bairro)
                .rua(rua)
                .numero(numero)
                .complemento(complemento)
                .tipo(tipo)
                .build();
    }*/

    public Cliente build() {
        return Cliente.builder()

            .usuario(buildUsuario())
            .nome(nome)
            .desconto(desconto)
            .dataNascimento(dataNascimento)
            .telefone(telefone)
            .cpf(cpf)
            .photo(photo)
            .build();
    }
}
