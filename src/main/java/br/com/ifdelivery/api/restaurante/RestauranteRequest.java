package br.com.ifdelivery.api.restaurante;

import br.com.ifdelivery.modelo.acesso.Usuario;
import br.com.ifdelivery.modelo.restaurante.CategoriasEnum;
import br.com.ifdelivery.modelo.restaurante.Restaurante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteRequest {
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;
    private boolean isActive;
    private boolean isOpen;
    private boolean aceitaPix;
    private boolean aceitaCartaoCredito;
    private boolean aceitaCartaoDebito;
    private boolean aceitaDinheiro;
    private boolean aceitaValeRefeicao;
    private boolean aceitaValeAlimentacao;
    private String photoBanner;
    private String photoLogo;

  //  @Enumerated(EnumType.STRING)
 //   private CategoriasEnum categoria;

    private String categoria;

    @NotBlank(message = "O e-mail é de preenchimento obrigatório")
    @Email
    private String email;

    @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String password;



    public Usuario buildUsuario() {
        return Usuario.builder()
                .username(email)
                .password(password)
                .roles(Arrays.asList(Usuario.ROLE_RESTAURANTE))
                .build();
    }


    public Restaurante build() {
        return Restaurante.builder()
                .usuario(buildUsuario())
                .razaoSocial(razaoSocial)
                .nomeFantasia(nomeFantasia)
                .cnpj(cnpj)
                .cep(cep)
                .estado(estado)
                .cidade(cidade)
                .bairro(bairro)
                .rua(rua)
                .numero(numero)
                .complemento(complemento)
                .isActive(isActive)
                .isOpen(isOpen)
                .aceitaPix(aceitaPix)
                .aceitaCartaoCredito(aceitaCartaoCredito)
                .aceitaCartaoDebito(aceitaCartaoDebito)
                .aceitaDinheiro(aceitaDinheiro)
                .aceitaValeRefeicao(aceitaValeRefeicao)
                .aceitaValeAlimentacao(aceitaValeAlimentacao)
                .photoLogo(photoLogo)
                .photoBanner(photoBanner)
                .categoriasEnum(CategoriasEnum.fromDescricao(categoria))
                .build();
    }

}
