package br.com.zedacarga.zedacarga_api.api.viagem;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Pagamento;
import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import br.com.zedacarga.zedacarga_api.modelo.viagem.ViagemService;
import br.com.zedacarga.zedacarga_api.modelo.viagem.ViagemStatusEnum.StatusViagem;
import io.swagger.v3.oas.annotations.Operation;

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
@PutMapping("/{idViagem}/status")
public ResponseEntity<Viagem> atualizarStatusViagem(
        @PathVariable Long idViagem,
        @RequestParam StatusViagem statusViagem,
        @RequestParam Long idMotorista,
        @RequestParam Long idContaBancariaMotorista) {
    try {
        Viagem viagemAtualizada = viagemService.atualizarStatusViagem(idViagem, statusViagem, idMotorista, idContaBancariaMotorista);
        return ResponseEntity.ok(viagemAtualizada);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


    // pagamento

    @Operation(summary = "Serviço responsável por pagar uma viagem.", description = "Realize o pagamento de uma viagem adicionando o ID do cliente e do motorista.")
    @PostMapping("/{viagemId}/conta/{contaId}/pagamento")
    public ResponseEntity<Pagamento> pagar(@RequestBody PagamentoRequest request, Long viagemId, Long contaId) {
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
