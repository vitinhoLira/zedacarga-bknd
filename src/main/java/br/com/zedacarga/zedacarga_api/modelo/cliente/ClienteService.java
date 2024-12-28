package br.com.zedacarga.zedacarga_api.modelo.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service

public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private CartaoClienteRepository cartaoClienteRepository;

    //Cliente

    @Transactional
    public Cliente save(Cliente cliente) {
        cliente.setHabilitado(Boolean.TRUE);
        cliente.setVersao(1L);
        cliente.setDataCriacao(LocalDate.now());
        return repository.save(cliente);
    }

    public List<Cliente> listarTodos() {

        return repository.findAll();
    }

    public Cliente obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado) {
        Cliente cliente = repository.findById(id).get();
        cliente.setNome(clienteAlterado.getNome());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setTelefone(clienteAlterado.getTelefone());
        cliente.setEmail(clienteAlterado.getEmail());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setFoto(clienteAlterado.getFoto());

        cliente.setVersao(cliente.getVersao() + 1);
        repository.save(cliente);
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = repository.findById(id).get();
        cliente.setHabilitado(Boolean.FALSE);
        cliente.setVersao(cliente.getVersao() + 1);
        repository.save(cliente);
    }

    //CartaoCliente

    @Transactional
    public CartaoCliente adicionarCartaoCliente(Long clienteId, CartaoCliente cartao) {

        Cliente cliente = this.obterPorID(clienteId);
        
        //Primeiro salva o EnderecoCliente:

        cartao.setCliente(cliente);
        cartao.setHabilitado(Boolean.TRUE);
        cartaoClienteRepository.save(cartao);
        
        //Depois acrescenta o endereço criado ao cliente e atualiza o cliente:

        List<CartaoCliente> listaCartoesCliente = cliente.getCartoes();
        
        if (listaCartoesCliente == null) {
            listaCartoesCliente = new ArrayList<CartaoCliente>();
        }
        
        listaCartoesCliente.add(cartao);
        cliente.setCartoes(listaCartoesCliente);
        repository.save(cliente);
        
        return cartao;
    }

    @Transactional
   public CartaoCliente atualizarCartaoCliente(Long id, CartaoCliente cartaoAlterado) {

    CartaoCliente cartao = cartaoClienteRepository.findById(id).get();
    cartao.setNumeroCartao(cartaoAlterado.getNumeroCartao());
    cartao.setTipoCartao(cartaoAlterado.getTipoCartao());
    cartao.setDataVencimento(cartaoAlterado.getDataVencimento());
    cartao.setCvv(cartaoAlterado.getCvv());

        return cartaoClienteRepository.save(cartao);
    }

    @Transactional
    public void removerCartaoCliente(Long idCartao) {

        CartaoCliente cartao = cartaoClienteRepository.findById(idCartao).get();
        cartao.setHabilitado(Boolean.FALSE);
        cartaoClienteRepository.save(cartao);

        Cliente cliente = this.obterPorID(cartao.getCliente().getId());
        cliente.getCartoes().remove(cartao);
        repository.save(cliente);
    }

}
