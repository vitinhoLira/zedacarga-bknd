package br.com.zedacarga.zedacarga_api.api.viagem;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Pagamento;
import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import br.com.zedacarga.zedacarga_api.modelo.viagem.ViagemService;
import br.com.zedacarga.zedacarga_api.modelo.viagem.ViagemStatusEnum.StatusViagem;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/viagem")
@CrossOrigin
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @Operation(summary = "Serviço responsável por inserir uma viagem no sistema.", description = "Insira uma viagem no sistema adicionando o ID do cliente e do motorista.")
    @PostMapping("/cliente/{clienteId}/cartao/{cartaoClienteId}/motorista/{motoristaId}")
    public ResponseEntity<Viagem> save(@RequestBody ViagemRequest request, Long clienteId, Long cartaoClienteId,
            Long motoristaId) {
        Viagem viagem = viagemService.save(request.build(), clienteId, cartaoClienteId, motoristaId);
        return new ResponseEntity<>(viagem, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por obter todas as viagens do sistema.", description = "Obtenha todas as viagens do sistema inserindo.")
    @GetMapping
    public List<Viagem> listarTodos() {
        return viagemService.listarTodos();
    }

    @Operation(summary = "Serviço responsável por obter uma viagem do sistema.", description = "Obtenha uma viagem do sistema inserindo o 'id'.")
    @GetMapping("/{id}")
    public Viagem obterPorID(@PathVariable Long id) {
        return viagemService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por editar uma viagem do sistema.", description = "Edite uma viagem do sistema inserindo o 'id'.")
    @PutMapping("/{id}")
    public ResponseEntity<Viagem> update(@PathVariable("id") Long id, @RequestBody ViagemRequest request) {
        viagemService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por excluir uma viagem do sistema.", description = "Exclua uma viagem do sistema inserindo o 'id'.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        viagemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualiza o status de uma viagem.", description = "Atualiza o status de uma viagem com base no ID da viagem, status desejado, ID do motorista e ID da conta bancária do motorista.")
    @PutMapping("/{viagemId}/motorista/{motoristaId}/contaBancariaMotorista/{contaBancariaMotoristaId}/status")
    public ResponseEntity<?> atualizarStatusViagem(
            @PathVariable Long viagemId,
            @PathVariable Long motoristaId,
            @PathVariable Long contaBancariaMotoristaId,
            @RequestBody @Valid StatusViagemRequest statusViagemRequest) {

        Logger logger = LoggerFactory.getLogger(getClass());

        try {
            logger.info("Recebida requisição para atualizar status da viagem {} para motorista {} e conta bancária {}",
                    viagemId, motoristaId, contaBancariaMotoristaId);

            String status = statusViagemRequest.getStatusViagem().toUpperCase();

            try {
                StatusViagem statusViagem = StatusViagem.valueOf(status);
                Viagem viagemAtualizada = viagemService.atualizarStatusViagem(viagemId, statusViagem, motoristaId,
                        contaBancariaMotoristaId);
                return ResponseEntity.ok(viagemAtualizada);
            } catch (IllegalArgumentException e) {
                logger.error("Status inválido recebido: {}", status, e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "erro", "Status inválido: " + status,
                                "detalhe", e.getMessage() // Retorna a mensagem detalhada do erro
                        ));
            } catch (Exception e) {
                logger.error("Erro ao processar o status da viagem", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of(
                                "erro", "Erro ao processar o status da viagem.",
                                "detalhe", e.getMessage() // Retorna a mensagem detalhada do erro
                        ));
            }

        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar status da viagem", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "erro", "Erro interno no servidor.",
                            "detalhe", e.getMessage() // Retorna a mensagem detalhada do erro
                    ));
        }
    }

    // pagamento

    @Operation(summary = "Serviço responsável por pagar uma viagem.", description = "Realize o pagamento de uma viagem adicionando o ID do cliente e do motorista.")
    @PostMapping("/{viagemId}/conta/{contaId}/pagamento")
    public ResponseEntity<Pagamento> pagar(@RequestBody PagamentoRequest request, @PathVariable Long viagemId,
            @PathVariable Long contaId) {
        Pagamento pagamento = viagemService.pagar(request.build(), viagemId, contaId);
        return new ResponseEntity<>(pagamento, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por transferir valor para o motorista.", description = "Realize a transferência de valor para o motorista da viagem.")
    @PostMapping("/{viagemId}/conta/{contaId}/motorista/{motoristaId}/pagamento/{pagamentoId}/transferir")
    public ResponseEntity<Pagamento> transferirValor(
            @PathVariable Long viagemId,
            @PathVariable Long contaId,
            @PathVariable Long motoristaId,
            @PathVariable long pagamentoId) {
        // Chama o método de transferência do serviço
        Pagamento pagamento = viagemService.transferirValorMotorista(contaId, viagemId, motoristaId, pagamentoId);

        // Retorna uma resposta de sucesso com o objeto Pagamento
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);

    }

}
