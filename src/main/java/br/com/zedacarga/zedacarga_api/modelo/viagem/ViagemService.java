package br.com.zedacarga.zedacarga_api.modelo.viagem;

import jakarta.transaction.Transactional;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zedacarga.zedacarga_api.api.websocket.MotoristaWebSocket;
import br.com.zedacarga.zedacarga_api.modelo.cliente.CartaoCliente;
import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
import br.com.zedacarga.zedacarga_api.modelo.cliente.ClienteService;
import br.com.zedacarga.zedacarga_api.modelo.motorista.ContaBancariaMotorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.MotoristaService;
import br.com.zedacarga.zedacarga_api.modelo.viagem.ViagemStatusEnum.StatusViagem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ViagemService {
    @Autowired
    private ViagemRepository repository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MotoristaWebSocket motoristaWebSocket;

    @Autowired
    private MotoristaService motoristaService;

    private static final Logger log = LoggerFactory.getLogger(ViagemService.class);

    @Transactional
    public Viagem save(Viagem viagem, Long clienteId, Long cartaoClienteId, Long idMotorista) {
        CartaoCliente cartao = clienteService.obterCartaoPorId(cartaoClienteId);
        Cliente cliente = clienteService.obterPorID(clienteId);
        Motorista motorista = motoristaService.obterPorID(idMotorista);

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
                viagem.setMotorista(motorista);

            } else {
                throw new RuntimeException(
                        "Erro ao criar cobrança da viagem no Asaas: " + response.code() + " - "
                                + response.body().string());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao se comunicar com a API do Asaas", e);
        }

        viagem.setHabilitado(Boolean.TRUE);
        viagem.setVersao(1L);
        viagem.setDataCriacao(LocalDate.now());

        Viagem viagemSalva = repository.save(viagem);

        // Cria um objeto JSON com os detalhes da viagem
        Map<String, Object> mensagemJson = new HashMap<>();
        mensagemJson.put("origem", viagem.getOrigem());
        mensagemJson.put("destino", viagem.getDestino());
        mensagemJson.put("valor", viagem.getValor());
        mensagemJson.put("mensagem", "Nova solicitação de viagem");
        mensagemJson.put("clienteId", cliente.getId());
        mensagemJson.put("viagemId", viagem.getId());

        // Converte para JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String mensagemJsonString;
        try {
            mensagemJsonString = objectMapper.writeValueAsString(mensagemJson);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter mensagem para JSON", e);
        }

        // Enviar a mensagem JSON pelo WebSocket
        motoristaWebSocket.enviarMensagemParaMotorista(idMotorista, mensagemJsonString);

        return viagemSalva;
    }

    @Transactional
    public List<Viagem> listarTodos() {
        return repository.findAll();
    }

    public Viagem obterPorID(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));
    }

    @Transactional
    public void update(Long id, Viagem viagemAlterado) {
        Viagem viagem = repository.findById(id).get();
        viagem.setOrigem(viagemAlterado.getOrigem());
        viagem.setDestino(viagemAlterado.getDestino());
        viagem.setValor(viagemAlterado.getValor());
        viagem.setStatusViagem(viagemAlterado.getStatusViagem());
        viagem.setViagemComprovante(viagemAlterado.getViagemComprovante());

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

    @Transactional
public Viagem atualizarStatusViagem(Long viagemId, StatusViagem statusViagem, Long motoristaId, Long contaBancariaMotoristaId) {
    Viagem viagem = this.obterPorID(viagemId);

    if (viagem == null) {
        throw new IllegalArgumentException("Viagem não encontrada com o ID: " + viagemId);
    }

    switch (statusViagem) {
        case ACEITO:
            return processarAceitacao(viagem, motoristaId, contaBancariaMotoristaId);

        case RECUSADO:
            return processarRecusa(viagem);

        default:
            throw new IllegalArgumentException("Status inválido para essa operação.");
    }
}

private Viagem processarAceitacao(Viagem viagem, Long motoristaId, Long contaBancariaMotoristaId) {
    Motorista motorista = motoristaService.obterPorID(motoristaId);
    ContaBancariaMotorista conta = motoristaService.obterContabancariaPorID(contaBancariaMotoristaId);

    viagem.setStatusViagem(StatusViagem.ANDAMENTO);
    viagem.setMotorista(motorista);
    viagem.setContaBancariaMotorista(conta);

    Viagem viagemAtualizada = repository.save(viagem);
    enviarMensagemWebSocket(viagemAtualizada, viagemAtualizada.getCliente().getId());

    return viagemAtualizada;
}

private Viagem processarRecusa(Viagem viagem) {
    viagem.setStatusViagem(StatusViagem.RECUSADO);
    viagem.setMotorista(null);

    Viagem viagemAtualizada = repository.save(viagem);
    enviarMensagemWebSocket(viagemAtualizada, viagemAtualizada.getCliente().getId());

    return viagemAtualizada;
}

private void enviarMensagemWebSocket(Viagem viagem, Long idCliente) {
    Map<String, Object> mensagemJson = new HashMap<>();
    mensagemJson.put("status", viagem.getStatusViagem());

    try {
        String mensagemJsonString = new ObjectMapper().writeValueAsString(mensagemJson);
        motoristaWebSocket.enviarMensagemParaMotorista(idCliente, mensagemJsonString);
    } catch (Exception e) {
        throw new RuntimeException("Erro ao converter mensagem para JSON", e);
    }
}

    // Pagamento

    public Pagamento obterPagamentoPorID(Long id) {
        return pagamentoRepository.findById(id).get();
    }

    @Transactional
    public Pagamento pagar(Pagamento pagamento, Long viagemId, Long contaBancariaId) {
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
                cliente.getResidenciaNumero());

        // Criando o corpo do RequestBody com o JSON gerado
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);

        // Construindo a requisição HTTP
        Request request = new Request.Builder()
                .url("https://sandbox.asaas.com/api/v3/payments/" + viagem.getNumeroProtocolo() + "/payWithCreditCard")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("access_token",
                        "$aact_MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OmZmNmE5MTFjLTc0NWUtNDU0OC04YTM2LTI2ZDM2NWY0MjFhZDo6JGFhY2hfMTk0OWRiZWItNTViNy00MjIzLTg1ZTItY2JlMDc5ZDU0YmVj")
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
                viagem.setViagemComprovante(responseJson.get("invoiceUrl").asText());
                viagem.setPgtoStatus("Done");
                pagamento.setViagem(viagem);
                pagamento.setHabilitado(Boolean.TRUE);
                pagamentoRepository.save(pagamento);

            } else {
                // Lançando exceção caso a resposta não seja bem-sucedida
                throw new RuntimeException(
                        "Erro ao realizar o pagamento no Asaas: " + response.code() + " - " + response.body().string());
            }
        } catch (Exception e) {
            // Tratamento de exceções
            throw new RuntimeException("Erro ao se comunicar com a API do Asaas", e);
        }

        viagem.setPagamento(pagamento);
        repository.save(viagem);

        return pagamento;
    }

    @Transactional
    public Pagamento transferirValorMotorista(Long idconta, Long idViagem, Long idMotorista, Long pagamentoId) {
        // Obter as entidades necessárias
        Motorista motorista = motoristaService.obterPorID(idMotorista);
        ContaBancariaMotorista conta = motoristaService.obterContabancariaPorID(idconta);
        Viagem viagem = obterPorID(idViagem);
        Pagamento pagamento = obterPagamentoPorID(pagamentoId);

        String dataTransferencia = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        double valorDeTransferencia = (80 * viagem.getValor()) / 100;

        // Preparar a requisição para transferência

        String json = "{\"value\":" + valorDeTransferencia
                + ",\"bankAccount\":{\"bank\":{\"code\":\"237\"},\"accountName\":\"" + conta.getNomeBanco()
                + "\",\"description\":\"" + "Origem: " + viagem.getOrigem() + "Destino: " + viagem.getDestino()
                + "\",\"ownerName\":\"" + motorista.getNome() + "\",\"cpfCnpj\":\"" + motorista.getCpf()
                + "\",\"account\":\"" + conta.getNumeroConta() + "\",\"accountDigit\":\"" + conta.getDigitoConta()
                + "\",\"bankAccountType\":\"CONTA_CORRENTE\",\"agency\":\"" + conta.getAgencia()
                + "\",\"externalReference\":\"" + dataTransferencia
                + "\"},\"operationType\":\"TED\"}";

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);

        // Criar e executar a requisição HTTP
        Request request = new Request.Builder()
                .url("https://sandbox.asaas.com/api/v3/transfers")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("access_token",
                        "$aact_MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OmZmNmE5MTFjLTc0NWUtNDU0OC04YTM2LTI2ZDM2NWY0MjFhZDo6JGFhY2hfMTk0OWRiZWItNTViNy00MjIzLTg1ZTItY2JlMDc5ZDU0YmVj")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Transferência realizada com sucesso
                pagamento.setStatusTransferenciaMotorista("Transferência realizada.");
                Pagamento saveStatusTransferenciaMotorista = pagamentoRepository.save(pagamento);
                return saveStatusTransferenciaMotorista;
            } else {
                // Erro ao transferir
                String responseBody = response.body().string();
                log.error("Erro ao transferir valor para o motorista: " + response.code() + " - " + responseBody);
                throw new RuntimeException(
                        "Erro ao transferir valor para o motorista: " + response.code() + " - " + responseBody);
            }
        } catch (IOException e) {
            log.error("Erro ao tentar realizar a transferência para o motorista.", e);
            throw new RuntimeException("Erro ao tentar realizar a transferência para o motorista", e);
        }
    }
}
