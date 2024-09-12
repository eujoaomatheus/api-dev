package br.com.ifdelivery.modelo.pedido;

import br.com.ifdelivery.api.pedido.ItemPedidoRequest;
import br.com.ifdelivery.modelo.cliente.Cliente;
import br.com.ifdelivery.modelo.cliente.ClienteRepository;
import br.com.ifdelivery.modelo.item_pedido.ItemPedido;
import br.com.ifdelivery.modelo.item_pedido.ItemPedidoRepository;
import br.com.ifdelivery.modelo.pedido.enums.StatusEntregaEnum;
import br.com.ifdelivery.modelo.pedido.enums.StatusPgtoEnum;
import br.com.ifdelivery.modelo.produto.Produto;
import br.com.ifdelivery.modelo.produto.ProdutoRepository;
import br.com.ifdelivery.modelo.restaurante.Restaurante;
import br.com.ifdelivery.modelo.restaurante.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository,ProdutoRepository produtoRepository,ClienteRepository clienteRepository, RestauranteRepository restauranteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Pedido criarPedido(Long clienteId, Long restauranteId, Pedido pedido, List<ItemPedidoRequest> itens) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setMetodoPagamento(pedido.getMetodoPagamento());
        pedido.setHabilitado(Boolean.TRUE);
        pedido.setVersao(1L);
        pedido.setDataCriacao(LocalDate.now());
        pedido.setStatusEntrega(StatusEntregaEnum.PENDENTE);
        pedido.setStatusPgto(StatusPgtoEnum.PENDENTE);
        pedido.setDataDoPedido(LocalDateTime.now());

        double valorTotal = 0;

        // Inicialize a lista de itens antes de adicionar
        List<ItemPedido> itensPedido = new ArrayList<>();

        for (ItemPedidoRequest itemPedidoRequest : itens) {
            ItemPedido item = salvarItemPedido(pedido, itemPedidoRequest);
            itensPedido.add(item);
            valorTotal += item.getSubtotal(); // Atualize o valor total
        }

        pedido.setItens(itensPedido); // Defina a lista de itens no pedido
        pedido.setValorTotal(valorTotal); // Defina o valor total

        // Salve o pedido, os itens serão salvos em cascata
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoSalvo; // Retorne o pedido salvo
    }

    private ItemPedido salvarItemPedido(Pedido pedido, ItemPedidoRequest itemPedidoRequest) {
        Produto produto = produtoRepository.findById(itemPedidoRequest.getProdutoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido); // Associe o pedido ao item
        item.setProduto(produto);
        item.setQuantidade(itemPedidoRequest.getQuantidade());
        item.setPrecoUnitario(produto.getValorUnitario());
        item.setHabilitado(Boolean.TRUE);
        item.setVersao(1L);
        item.setDataCriacao(LocalDate.now());

        return item;
    }

    //listar pedidos por cliente
    @Transactional
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    //listar pedidos por restaurante
    @Transactional
    public List<Pedido> listarPorRestaurante(Long restauranteId) {
        return pedidoRepository.findByRestauranteId(restauranteId);
    }
    @Transactional
    public Pedido atualizar(long id, Pedido pedidoAlterado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if (pedidoAlterado.getStatusEntrega() != null) {
            pedido.setStatusEntrega(pedidoAlterado.getStatusEntrega());
        }
        if (pedidoAlterado.getStatusPgto() != null) {
            pedido.setStatusPgto(pedidoAlterado.getStatusPgto());
        }

        return pedidoRepository.save(pedido);
    }
    @Transactional
    public void deletar(Long id) {

        Pedido pedido = pedidoRepository.findById(id).get();
        pedido.setHabilitado(Boolean.FALSE);
        pedido.setVersao(pedido.getVersao() + 1);

        pedidoRepository.save(pedido);
    }
    @Transactional
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
    @Transactional
    public Iterable<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }
}
