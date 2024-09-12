package br.com.ifdelivery.api.restaurante;

import br.com.ifdelivery.modelo.restaurante.Restaurante;
import br.com.ifdelivery.modelo.restaurante.RestauranteService;
import br.com.ifdelivery.util.exception.RestauranteException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurante")
@CrossOrigin
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Operation(summary = "Cadastrar um restaurante", description = "Endpoint responsável por cadastrar um restaurante")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid RestauranteRequest request) {

        try {
            Restaurante restaurante = restauranteService.save(request.build());
            return new ResponseEntity<>(restaurante, HttpStatus.CREATED);
        } catch (RestauranteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Listar todos os restaurantes", description = "Endpoint responsável por listar todos os restaurantes")
    @GetMapping
    public List<Restaurante> listarTodos() {
        return restauranteService.listarTodos();
    }

    @Operation(summary = "Obter um restaurante por ID", description = "Endpoint responsável por obter um restaurante por ID")
    @GetMapping("/")
    public ResponseEntity<?> obterPorID(@RequestParam(required = false) Long restauranteId,
                                                  @RequestParam(required = false) Long usuarioId) {
        try {
            if (restauranteId != null) {
                Restaurante restaurante = restauranteService.obterPorRestauranteId(restauranteId);
                return ResponseEntity.ok(restaurante);
            } else if (usuarioId != null) {
                Restaurante restaurante = restauranteService.obterPorUsuarioId(usuarioId);
                return ResponseEntity.ok(restaurante);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (RestauranteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Atualizar um restaurante", description = "Endpoint responsável por atualizar um restaurante")
    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> update(@PathVariable("id") Long id, @RequestBody RestauranteRequest request) {

        restauranteService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletar um restaurante", description = "Endpoint responsável por deletar um restaurante")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        restauranteService.delete(id);
        return ResponseEntity.ok().build();
    }
}
