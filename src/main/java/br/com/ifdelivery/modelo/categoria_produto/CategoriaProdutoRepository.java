package br.com.ifdelivery.modelo.categoria_produto;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    List<CategoriaProduto> findByRestauranteId(Long restauranteId);



    @Query("SELECT c.id, c.nome, c.descricao, p.id, p.codigo, p.titulo, p.descricao, p.imagem, p.valorUnitario " +
            "FROM CategoriaProduto c " +
            "LEFT JOIN c.produtos p " +
            "WHERE c.restaurante.id = :restauranteId " +
            "AND c.habilitado = true")
    List<Object[]> findCategoriaProdutoDTOWithProdutosByRestauranteId(@Param("restauranteId") Long restauranteId);

}
