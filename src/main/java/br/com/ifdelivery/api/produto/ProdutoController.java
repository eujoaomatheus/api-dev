package br.com.ifdelivery.api.produto;

import br.com.ifdelivery.api.produto.dto.ProdutoDTO;
import br.com.ifdelivery.modelo.produto.Produto;
import br.com.ifdelivery.modelo.produto.ProdutoService;
import br.com.ifdelivery.modelo.restaurante.RestauranteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin
public class ProdutoController {

    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;

    public ProdutoController(ProdutoService produtoService, RestauranteService restauranteService) {
        this.produtoService = produtoService;
        this.restauranteService = restauranteService;
    }

    @PostMapping("/")
    @Operation(summary = "Criar um novo produto", description = "Endpoint responsavel por criar um novo produto")
    public ResponseEntity<Produto> create(@RequestParam Long restauranteId,
                                          @RequestParam Long categoriaId,
                                          @RequestBody ProdutoRequest request) {
        Produto novoProduto = produtoService.save(request.build(), restauranteId, categoriaId);
        return ResponseEntity.ok(novoProduto);
    }

    @Operation(summary = "Listar todos os produtos", description = "Endpoint responsavel por listar todos os produtos")
    @GetMapping("/cardapio/{restauranteId}")
    public Map<String, List<ProdutoDTO>> obterCardapio(@PathVariable Long restauranteId) {
        return produtoService.listarCardapioRestaurante(restauranteId);
    }

    @Operation(summary = "Atualizar um produto", description = "Endpoint responsavel por atualizar um produto")
    @PutMapping("/")
    public ResponseEntity<Produto> update(@RequestParam Long produtoId,
                                                                      @RequestParam(required = false) Long categoriaId,
                                                                      @RequestBody ProdutoRequest request) {

        Produto produtoAtualizado =   produtoService.update(produtoId, categoriaId, request.build());

        return ResponseEntity.ok(produtoAtualizado);

    }

    @Operation(summary = "Deletar um produto", description = "Endpoint responsavel por deletar um produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
