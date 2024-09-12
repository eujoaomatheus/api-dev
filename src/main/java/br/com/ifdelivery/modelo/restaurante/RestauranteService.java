package br.com.ifdelivery.modelo.restaurante;

import br.com.ifdelivery.util.exception.RestauranteException;
import br.com.ifdelivery.modelo.acesso.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioService usuarioService;

    public RestauranteService(RestauranteRepository restauranteRepository, UsuarioService usuarioService) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Restaurante save(Restaurante restaurante) {
        try {
            usuarioService.save(restaurante.getUsuario());
            restaurante.setHabilitado(Boolean.TRUE);
            restaurante.setVersao(1L);
            restaurante.setDataCriacao(LocalDate.now());
            return restauranteRepository.save(restaurante);
        } catch (Exception e) {
            throw new RestauranteException(RestauranteException.MSG_ERROR_SAVING + ": " + e.getMessage());
        }
    }
    @Transactional
    public List<Restaurante> listarTodos() {
        try {
            return restauranteRepository.findAll();
        } catch (Exception e) {
            throw new RestauranteException(RestauranteException.MSG_ERROR_LISTING + ": " + e.getMessage());
        }
    }


    @Transactional
    public void update(long id, Restaurante restauranteAlterado) {
        try {
            Restaurante restaurante = restauranteRepository.findById(id)
                    .orElseThrow(() -> new RestauranteException(RestauranteException.MSG_RESTAURANTE_NOT_FOUND + " com id: " + id));
            restaurante.setNomeFantasia(restauranteAlterado.getNomeFantasia());
            restaurante.setRazaoSocial(restauranteAlterado.getRazaoSocial());
            restaurante.setCnpj(restauranteAlterado.getCnpj());
            restaurante.setPhotoBanner(restaurante.getPhotoBanner());
            restaurante.setPhotoLogo(restaurante.getPhotoLogo());
            restaurante.setCidade(restauranteAlterado.getCidade());
            restaurante.setCategoriasEnum(restauranteAlterado.getCategoriasEnum());
            restaurante.setAceitaCartaoCredito(restauranteAlterado.isAceitaCartaoCredito());
            restaurante.setAceitaCartaoDebito(restauranteAlterado.isAceitaCartaoDebito());
            restaurante.setAceitaDinheiro(restauranteAlterado.isAceitaDinheiro());
            restaurante.setAceitaPix(restauranteAlterado.isAceitaPix());
            restaurante.setAceitaValeAlimentacao(restauranteAlterado.isAceitaValeAlimentacao());
            restaurante.setAceitaValeRefeicao(restauranteAlterado.isAceitaValeRefeicao());
            restaurante.setCep(restauranteAlterado.getCep());
            restaurante.setEstado(restauranteAlterado.getEstado());
            restaurante.setBairro(restauranteAlterado.getBairro());
            restaurante.setRua(restauranteAlterado.getRua());
            restaurante.setNumero(restauranteAlterado.getNumero());
            restaurante.setComplemento(restauranteAlterado.getComplemento());
            restauranteRepository.save(restaurante);
        } catch (Exception e) {
            throw new RestauranteException(RestauranteException.MSG_ERROR_UPDATING + ": " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            Restaurante restaurante = restauranteRepository.findById(id)
                    .orElseThrow(() -> new RestauranteException(RestauranteException.MSG_RESTAURANTE_NOT_FOUND + " com id: " + id));
            restaurante.setHabilitado(Boolean.FALSE);
            restaurante.setVersao(restaurante.getVersao() + 1);
            restauranteRepository.save(restaurante);
        } catch (Exception e) {
            throw new RestauranteException(RestauranteException.MSG_ERROR_DELETING + ": " + e.getMessage());
        }
    }
    @Transactional
    public Restaurante obterPorUsuarioId(Long usuarioId) {

            Restaurante restaurante =  restauranteRepository.findByUsuarioId(usuarioId);
            if (restaurante == null) {
                throw new RestauranteException(RestauranteException.MSG_RESTAURANTE_NOT_FOUND + " com usuarioId: " + usuarioId);
            }
            else return restaurante;
        }

    @Transactional
    public Restaurante obterPorRestauranteId(Long id) {

            Optional<Restaurante> restaurante =  restauranteRepository.findById(id);
            if(restaurante.isEmpty()) {
                throw new RestauranteException(RestauranteException.MSG_RESTAURANTE_NOT_FOUND + " com restauranteId: " + id);
            }
            else return restaurante.get();

        }
}
