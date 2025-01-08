package br.com.zedacarga.zedacarga_api.modelo.viagem;

import jakarta.transaction.Transactional;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.zedacarga.zedacarga_api.modelo.cliente.CartaoCliente;
import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
import br.com.zedacarga.zedacarga_api.modelo.cliente.ClienteService;
import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.MotoristaService;

@Service
public class ViagemService {
   @Autowired
    private ViagemRepository repository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MotoristaService motoristaService;

    @Transactional
    public Viagem save(Viagem viagem, Long clienteId, Long cartaoClienteId) {
        CartaoCliente cartao = clienteService.obterCartaoPorId(cartaoClienteId);
        Cliente cliente = clienteService.obterPorID(clienteId);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado com o ID: " + clienteId);
        }

        if (cartao == null) {
            throw new IllegalArgumentException("Cartão não encontrado com o ID: " + cartaoClienteId);
        }

        viagem.setCartaoCliente(cartao);
        viagem.setCliente(cliente);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        String json = "{"
                + "\"billingType\":\"" + cartao.getTipoCartao() + "\","
                + "\"customer\":\"" + cliente.getAsaasId() + "\","
                + "\"value\":" + viagem.getValor() + ","
                + "\"dueDate\":\"" + viagem.getDataVencimentoCobranca() + "\","
                + "\"description\":\"Origem: " + viagem.getOrigem() + " Destino: " + viagem.getDestino() + "\","
                + "\"callback\":{"
                + "\"successUrl\":\"https://www.google.com.br\","
                + "\"autoRedirect\":true"
                + "}"
                + "}";

        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url("https://sandbox.asaas.com/api/v3/payments")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("access_token",
                        "$aact_MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OmZmNmE5MTFjLTc0NWUtNDU0OC04YTM2LTI2ZDM2NWY0MjFhZDo6JGFhY2hfMTk0OWRiZWItNTViNy00MjIzLTg1ZTItY2JlMDc5ZDU0YmVj")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode responseJson = mapper.readTree(response.body().string());
                viagem.setNumeroProtocolo(responseJson.get("id").asText());
                viagem.setPgtoStatus(responseJson.get("status").asText());
            } else {
                throw new RuntimeException(
                        "Erro ao criar cobrança da viagm no Asaas: " + response.code() + " - " + response.body().string());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao se comunicar com a API do Asaas", e);

        }

        viagem.setHabilitado(Boolean.TRUE);
        viagem.setVersao(1L);
        viagem.setDataCriacao(LocalDate.now());

        return repository.save(viagem);
    }

    @Transactional
    public List<Viagem> listarTodos() {

        return repository.findAll();
    }

    @Transactional
    public Viagem obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Viagem viagemAlterado) {
        Viagem viagem = repository.findById(id).get();
        viagem.setOrigem(viagemAlterado.getOrigem());
        viagem.setDestino(viagemAlterado.getDestino());
        viagem.setValor(viagemAlterado.getValor());
        viagem.setStatusViagem(viagemAlterado.getStatusViagem());

        viagem.setVersao(viagem.getVersao() + 1);
        repository.save(viagem);
    }

    @Transactional
    public void delete(Long id) {
        Viagem viagem = repository.findById(id).get();
        viagem.setHabilitado(Boolean.FALSE);
        viagem.setVersao(viagem.getVersao() + 1);
        repository.save(viagem);
    }

    // Pagamento

    @Transactional
    public Pagamento pagar(Pagamento pagamento, Long viagemId) {
    
        Viagem viagem = obterPorID(viagemId);
        CartaoCliente cartao = viagem.getCartaoCliente();
        Cliente cliente = viagem.getCliente();
        
        // Obtendo o mês e ano de vencimento do cartão
        YearMonth vencimento = cartao.getDataVencimento();
        int mes = vencimento.getMonthValue();
        int ano = vencimento.getYear();
    
        // Criando o corpo do JSON para o pagamento
        String json = String.format(
            "{\"creditCard\":{\"holderName\":\"%s\",\"number\":\"%s\",\"ccv\":\"%s\",\"expiryMonth\":\"%d\",\"expiryYear\":\"%d\"},"
            + "\"creditCardHolderInfo\":{\"name\":\"%s\",\"email\":\"%s\",\"cpfCnpj\":\"%s\",\"postalCode\":\"%s\",\"mobilePhone\":\"%s\",\"addressNumber\":\"%s\"},"
            + "\"creditCardToken\":null}",
            cliente.getNome(),
            cartao.getNumeroCartao(),
            cartao.getCvv(),
            mes,
            ano,
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getCpf(),
            cliente.getCep(),
            cliente.getTelefone(),
            cliente.getResidenciaNumero()
        );
    
        // Criando o corpo do RequestBody com o JSON gerado
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);
    
        // Construindo a requisição HTTP
        Request request = new Request.Builder()
                .url("https://sandbox.asaas.com/api/v3/payments/"+viagem.getNumeroProtocolo()+"/payWithCreditCard")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("access_token", "$aact_MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OmZmNmE5MTFjLTc0NWUtNDU0OC04YTM2LTI2ZDM2NWY0MjFhZDo6JGFhY2hfMTk0OWRiZWItNTViNy00MjIzLTg1ZTItY2JlMDc5ZDU0YmVj")
                .build();
    
        try {
            // Executando a requisição HTTP
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Parse da resposta JSON
                ObjectMapper mapper = new ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode responseJson = mapper.readTree(response.body().string());
    
                // Salvando os dados do pagamento e confirmando a viagem
                pagamento.setComprovante(responseJson.get("invoiceUrl").asText());
                pagamento.setProtocoloId(responseJson.get("id").asText());
                viagem.setPgtoStatus("Done");
            } else {
                // Lançando exceção caso a resposta não seja bem-sucedida
                throw new RuntimeException(
                        "Erro ao realizar o pagamento no Asaas: " + response.code() + " - " + response.body().string());
            }
        } catch (Exception e) {
            // Tratamento de exceções
            throw new RuntimeException("Erro ao se comunicar com a API do Asaas", e);
        }

        pagamentoRepository.save(pagamento);
    
        return pagamento;
    }

}