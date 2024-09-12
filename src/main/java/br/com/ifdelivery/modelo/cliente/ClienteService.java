package br.com.ifdelivery.modelo.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import br.com.ifdelivery.modelo.acesso.UsuarioService;
import br.com.ifdelivery.modelo.endereco.EnderecoCliente;
import br.com.ifdelivery.modelo.endereco.EnderecoClienteRepository;
import br.com.ifdelivery.modelo.mensagens.EmailService;
import br.com.ifdelivery.util.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;


    @Transactional
    public Cliente save(Cliente cliente) {

        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int randomNumber = random.nextInt(9);
            sb.append(randomNumber);
        }

        usuarioService.save(cliente.getUsuario());

        cliente.setHabilitado(Boolean.TRUE);
        cliente.setVersao(1L);
        cliente.setDataCriacao(LocalDate.now());
        cliente.setCodigoAuth(sb.toString());
        try {
            emailService.enviarEmailConfirmacaoCadastroCliente(cliente);
            return repository.save(cliente);
        } catch (Exception e) {
            throw new UserException(UserException.MSG_EMAIL_ALREADY_EXISTS);
        }
    }

    public List<Cliente> listarTodos() {

        return repository.findAll();
    }

    @SuppressWarnings("null")
    public Cliente obterPorID(Long clienteId) {

        return repository.findById(clienteId).get();
    }

    @Transactional
    public void update(Long clienteId, Cliente clienteAlterado) {



        @SuppressWarnings("null")
        Cliente cliente = repository.findById(clienteId).get();
//        cliente.setEmail(clienteAlterado.getEmail());
//        cliente.setPassword(clienteAlterado.getPassword());
        cliente.setDesconto(clienteAlterado.getDesconto());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setTelefone(clienteAlterado.getTelefone());
        cliente.setCpf(clienteAlterado.getCpf());



        cliente.setVersao(cliente.getVersao() + 1);
        try {
            repository.save(cliente);
        } catch (Exception e) {
            throw new UserException(UserException.MSG_EMAIL_ALREADY_EXISTS);
        }
    }

    @Transactional
    public void delete(Long clienteId) {

        @SuppressWarnings("null")
        Cliente cliente = repository.findById(clienteId).get();
        cliente.setHabilitado(Boolean.FALSE);
        cliente.setVersao(cliente.getVersao() + 1);
        
        repository.save(cliente);
    }

    @Transactional
    public EnderecoCliente adicionarEnderecoCliente(Long clienteId, EnderecoCliente endereco) {
 
        Cliente cliente = repository.findById(clienteId).get();
       
        //Primeiro salva o EnderecoCliente:
 
        endereco.setCliente(cliente);
        endereco.setHabilitado(Boolean.TRUE);
        enderecoClienteRepository.save(endereco);
       
        //Depois acrescenta o endereço criado ao cliente e atualiza o cliente:
 
        List<EnderecoCliente> listaEnderecoCliente = cliente.getEnderecos();
       
        if (listaEnderecoCliente == null) {
            listaEnderecoCliente = new ArrayList<EnderecoCliente>();
        }
       
        listaEnderecoCliente.add(endereco);
        cliente.setEnderecos(listaEnderecoCliente);
        cliente.setVersao(cliente.getVersao() + 1);
        repository.save(cliente);
       
        return endereco;
    }

    @Transactional
   public EnderecoCliente atualizarEnderecoCliente(Long clienteId, EnderecoCliente enderecoAlterado) {

       EnderecoCliente endereco = enderecoClienteRepository.findById(clienteId).get();
       endereco.setCep(enderecoAlterado.getCep());
       endereco.setEstado(enderecoAlterado.getEstado());
       endereco.setCidade(enderecoAlterado.getCidade());
       endereco.setBairro(enderecoAlterado.getBairro());
       endereco.setRua(enderecoAlterado.getRua());
       endereco.setNumero(enderecoAlterado.getNumero());
       endereco.setComplemento(enderecoAlterado.getComplemento());
       endereco.setTipo(enderecoAlterado.getTipo());

       return enderecoClienteRepository.save(endereco);
   }

   @Transactional
    public void removerEnderecoCliente(Long clienteId) {

    EnderecoCliente endereco = enderecoClienteRepository.findById(clienteId).get();
    endereco.setHabilitado(Boolean.FALSE);
    enderecoClienteRepository.save(endereco);

    Cliente cliente = this.obterPorID(endereco.getCliente().getId());
    cliente.getEnderecos().remove(endereco);
           cliente.setVersao(cliente.getVersao() + 1);
    repository.save(cliente);
}

    public Cliente obterPorUsuarioId(Long usuarioId) throws Exception{

            Cliente cliente =  repository.findByUsuarioId(usuarioId);
            if (cliente == null) {
                throw new Exception("Cliente não encontrado, com usuário id: " + usuarioId);
            }
            else return cliente;
        }


    public Cliente obterPorClienteId(Long id) throws Exception {

            Optional<Cliente> cliente =  repository.findById(id);
            if(cliente.isEmpty()) {
                throw new Exception("Usuario não encontrado, com usuário id: " + id);
            }
            else return cliente.get();

        }

 
}
