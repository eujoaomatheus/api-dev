package br.com.ifdelivery.modelo.categoria_produto;

import br.com.ifdelivery.api.categoria_produto.dto.CategoriaProdutoDTO;
import br.com.ifdelivery.api.produto.dto.ProdutoDTO;
import br.com.ifdelivery.modelo.restaurante.Restaurante;
import br.com.ifdelivery.modelo.restaurante.RestauranteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CategoriaProdutoService {

        private final CategoriaProdutoRepository categoriaProdutoRepository;
        private final RestauranteService restauranteService;

        public CategoriaProdutoService(CategoriaProdutoRepository categoriaProdutoRepository, RestauranteService restauranteService) {
            this.categoriaProdutoRepository = categoriaProdutoRepository;
            this.restauranteService = restauranteService;
        }
        @Transactional
        public CategoriaProduto save(CategoriaProduto categoriaProduto, Long restauranteId) {

            Restaurante restaurante = restauranteService.obterPorRestauranteId(restauranteId);
            if (restaurante == null) {
                throw new RuntimeException("Restaurante não encontrado");
            }
            categoriaProduto.setRestaurante(restaurante);
            categoriaProduto.setHabilitado(Boolean.TRUE);
            categoriaProduto.setVersao(1L);
            categoriaProduto.setDataCriacao(LocalDate.now());
            return categoriaProdutoRepository.save(categoriaProduto);
        }

        public CategoriaProduto obterPorID(Long id) {
            return categoriaProdutoRepository.findById(id).orElse(null);
        }

    @Transactional
        public List<CategoriaProduto> listarPorRestaurante(Long restauranteId) {
            return categoriaProdutoRepository.findByRestauranteId(restauranteId);
        }

    @Transactional
        public void delete(Long id) {

            Optional<CategoriaProduto> optionalCategoriaProduto = categoriaProdutoRepository.findById(id);

            if (optionalCategoriaProduto.isPresent()) {
                CategoriaProduto categoriaProduto = optionalCategoriaProduto.get();
                categoriaProduto.setHabilitado(Boolean.FALSE);
                categoriaProduto.setVersao(categoriaProduto.getVersao() + 1);
                categoriaProdutoRepository.save(categoriaProduto);
            } else {
                throw new EntityNotFoundException("CategoriaProduto não encontrada com  id: " + id);
            }
        }
        @Transactional
        public CategoriaProduto update(Long id, CategoriaProduto categoriaProdutoAlterada) {

            Optional<CategoriaProduto> optionalCategoriaProduto = categoriaProdutoRepository.findById(id);

            if (optionalCategoriaProduto.isPresent()) {
                CategoriaProduto categoriaProduto = optionalCategoriaProduto.get();
                categoriaProduto.setNome(categoriaProdutoAlterada.getNome());
                categoriaProduto.setDescricao(categoriaProdutoAlterada.getDescricao());
                categoriaProdutoRepository.save(categoriaProduto);
                return categoriaProduto;
            } else {
                throw new EntityNotFoundException("CategoriaProduto não encontrada com  id: " + id);
            }
        }
    @Transactional
    public List<CategoriaProdutoDTO> obterCardapioGestorRestaurante(Long restauranteId) {
        List<Object[]> results = categoriaProdutoRepository.findCategoriaProdutoDTOWithProdutosByRestauranteId(restauranteId);
        Map<Long, CategoriaProdutoDTO> categoriaMap = new HashMap<>();

        for (Object[] result : results) {
            Long categoriaId = (Long) result[0];
            String categoriaNome = (String) result[1];
            String categoriaDescricao = (String) result[2];

            CategoriaProdutoDTO categoria = categoriaMap.computeIfAbsent(categoriaId, id -> new CategoriaProdutoDTO(id, categoriaNome, categoriaDescricao, new ArrayList<>()));

            Long produtoId = (Long) result[3];
            if (produtoId != null) {
                String produtoCodigo = (String) result[4];
                String produtoTitulo = (String) result[5];
                String produtoDescricao = (String) result[6];
                String produtoImagem = (String) result[7];
                Double produtoValorUnitario = (Double) result[8];

                ProdutoDTO produto = new ProdutoDTO(produtoId, produtoCodigo, produtoTitulo, produtoDescricao, produtoImagem, produtoValorUnitario);
                categoria.getProdutos().add(produto);
            }
        }

        return new ArrayList<>(categoriaMap.values());
    }
}
