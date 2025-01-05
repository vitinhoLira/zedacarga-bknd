package br.com.zedacarga.zedacarga_api.api.viagem;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import br.com.zedacarga.zedacarga_api.modelo.viagem.ViagemService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/viagem")
@CrossOrigin
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @Operation(summary = "Serviço responsável por inserir uma viagem no sistema.", 
               description = "Insira uma viagem no sistema adicionando o ID do cliente e do motorista.")
    @PostMapping("/{clienteId}/{cartaoClienteId}")
    public ResponseEntity<Viagem> save(@RequestBody ViagemRequest request, Long clienteId, Long motoristaId, Long cartaoClienteId, Long contaMotoristaId) {
        Viagem viagem = viagemService.save(request.build(), clienteId, cartaoClienteId);
        return new ResponseEntity<>(viagem, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por obter todas as viagens do sistema.", 
               description = "Obtenha todas as viagens do sistema inserindo.")
    @GetMapping
    public List<Viagem> listarTodos() {
        return viagemService.listarTodos();
    }

    @Operation(summary = "Serviço responsável por obter uma viagem do sistema.", 
               description = "Obtenha uma viagem do sistema inserindo o 'id'.")
    @GetMapping("/{id}")
    public Viagem obterPorID(@PathVariable Long id) {
        return viagemService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por editar uma viagem do sistema.", 
               description = "Edite uma viagem do sistema inserindo o 'id'.")
    @PutMapping("/{id}")
    public ResponseEntity<Viagem> update(@PathVariable("id") Long id, @RequestBody ViagemRequest request) {
        viagemService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por excluir uma viagem do sistema.", 
               description = "Exclua uma viagem do sistema inserindo o 'id'.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        viagemService.delete(id);
        return ResponseEntity.ok().build();
    }

}
