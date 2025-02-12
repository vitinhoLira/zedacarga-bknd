package br.com.zedacarga.zedacarga_api.modelo.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service

public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private CartaoClienteRepository cartaoClienteRepository;

    // Cliente

    @Transactional
    public Cliente save(Cliente cliente) {
        // Cria o cliente HTTP
        OkHttpClient client = new OkHttpClient();

        // Define o tipo de mídia (JSON)
        MediaType mediaType = MediaType.parse("application/json");

        // Cria o corpo da requisição com dados do cliente
        String json = "{\"name\":\"" + cliente.getNome() + "\",\"cpfCnpj\":\"" + cliente.getCpf()
                + "\",\"mobilePhone\":\"" + cliente.getTelefone() + "\" }";
        RequestBody body = RequestBody.create(json, mediaType);

        // Constrói a requisição
        Request request = new Request.Builder()
                .url("https://sandbox.asaas.com/api/v3/customers")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("access_token",
                        "$aact_MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OmFkZTA5OTJhLWI3MzMtNDQwNy1hY2MwLTZhYjFkZTgyNTM2Yzo6JGFhY2hfNDg3NmM0M2UtYTU0MS00OGVlLWExZmQtZGRjMTI4YzI0NTRl") // Substitua
                                                                                                                                                                                          // com
                                                                                                                                                                                          // o
                                                                                                                                                                                          // token
                                                                                                                                                                                          // real
                .build();

        try {
            // Executa a requisição e captura a resposta
            Response response = client.newCall(request).execute();

            // Trata a resposta, se for bem-sucedida
            if (response.isSuccessful()) {
                // Extrai o ID do cliente da resposta JSON
                String responseBody = response.body().string();

                // A resposta contém um JSON com o campo "id"
                int idStartIndex = responseBody.indexOf("\"id\":\"") + 6;
                int idEndIndex = responseBody.indexOf("\"", idStartIndex);
                String asaasId = responseBody.substring(idStartIndex, idEndIndex);

                // Define o ID do cliente no objeto Cliente
                cliente.setAsaasId(asaasId);
            } else {
                // Caso a requisição falhe, lança uma exceção
                throw new RuntimeException("Erro ao criar cliente no Asaas: " + response.message());
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao se comunicar com a API do Asaas", e);
        }

        // Define dados adicionais do cliente
        cliente.setHabilitado(Boolean.TRUE);
        cliente.setVersao(1L);
        cliente.setDataCriacao(LocalDate.now());

        // Salva o cliente no banco de dados
        return repository.save(cliente);
    }

    public List<Cliente> listarTodos() {

        return repository.findAll();
    }

    public Cliente obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public Cliente update(Long id, Cliente clienteAlterado) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
        cliente.setNome(clienteAlterado.getNome());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setTelefone(clienteAlterado.getTelefone());
        cliente.setEmail(clienteAlterado.getEmail());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setFoto(clienteAlterado.getFoto());
        cliente.setCep(clienteAlterado.getCep());
        cliente.setResidenciaNumero(clienteAlterado.getResidenciaNumero());

        cliente.setVersao(cliente.getVersao() + 1);
        repository.save(cliente);
        return cliente;
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = repository.findById(id).get();
        cliente.setHabilitado(Boolean.FALSE);
        cliente.setVersao(cliente.getVersao() + 1);
        repository.save(cliente);
    }

    // CartaoCliente

    public List<CartaoCliente> listarCartoesPorCliente(Long clienteId) {
        Cliente cliente = this.obterPorID(clienteId);
        if (cliente == null) {
            throw new RuntimeException("Cliente com ID " + clienteId + " não encontrado.");
        }
        return cliente.getCartoes();
    }

    public CartaoCliente obterCartaoPorId(Long id) {
        return cartaoClienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão com ID " + id + " não encontrado"));
    }

    @Transactional
    public CartaoCliente adicionarCartaoCliente(Long clienteId, CartaoCliente cartao) {

        Cliente cliente = this.obterPorID(clienteId);

        // Primeiro salva o EnderecoCliente:

        cartao.setCliente(cliente);
        cartao.setHabilitado(Boolean.TRUE);
        cartaoClienteRepository.save(cartao);

        // Depois acrescenta o endereço criado ao cliente e atualiza o cliente:

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
