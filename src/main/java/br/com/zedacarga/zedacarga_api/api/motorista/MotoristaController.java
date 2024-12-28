package br.com.zedacarga.zedacarga_api.api.motorista;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.zedacarga.zedacarga_api.modelo.motorista.ContaBancariaMotorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.MotoristaService;
import br.com.zedacarga.zedacarga_api.modelo.motorista.Veiculo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/motorista")
@CrossOrigin
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @Operation(summary = "Serviço responsável por inserir um motorista no sistema.", 
    description = "Inserir um motorista no sistema.")
    @PostMapping
    public ResponseEntity<Motorista> save(@RequestBody MotoristaRequest request) {
        Motorista motorista = motoristaService.save(request.build());
        return new ResponseEntity<Motorista>(motorista, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por obter todos os motoristas do sistema.", 
    description = "Obter todos os motoristas do sistema.")
    @GetMapping
    public List<Motorista> listarTodos() {
        return motoristaService.listarTodos();
    }

    @Operation(summary = "Serviço responsável por obter um motorista do sistema.", 
    description = "Obtenha um motorista do sistema inserindo o 'id'.")
    @GetMapping("/{id}")
    public Motorista obterPorID(@PathVariable Long id) {
        return motoristaService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por editar um motorista do sistema.", 
    description = "Editar um motorista do sistema inserindo o 'id'.")
    @PutMapping("/{id}")
    public ResponseEntity<Motorista> update(@PathVariable("id") Long id, @RequestBody MotoristaRequest request) {
        motoristaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por excluir um motorista do sistema.", 
    description = "Exclua um motorista do sistema inserindo o 'id'.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motoristaService.delete(id);
        return ResponseEntity.ok().build();
    }

    // ContaBancariaMotorista

    @Operation(summary = "Serviço responsável por salvar uma conta do motorista no sistema.", 
    description = "Inclua uma conta do motorista no sistema.")
    @PostMapping("/conta/{motoristaId}")
    public ResponseEntity<ContaBancariaMotorista> adicionarContaBancariaMotorista(@PathVariable("motoristaId") Long motoristaId, @RequestBody @Valid ContaBancariaMotoristaRequest request) {
        ContaBancariaMotorista conta = motoristaService.adicionarContaMotorista(motoristaId, request.build());
        return new ResponseEntity<ContaBancariaMotorista>(conta, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por editar uma conta do motorista no sistema.", 
    description = "Edite uma conta do motorista no sistema.")
    @PutMapping("/conta/{contaId}")
    public ResponseEntity<ContaBancariaMotorista> atualizarContaBancariaMotorista(@PathVariable("contaId") Long contaId, @RequestBody ContaBancariaMotoristaRequest request) {
        ContaBancariaMotorista conta = motoristaService.atualizarContaMotorista(contaId, request.build());
        return new ResponseEntity<ContaBancariaMotorista>(conta, HttpStatus.OK);
    }

    @Operation(summary = "Serviço responsável por excluir uma conta do motorista no sistema.", 
    description = "Exclua uma conta do motorista no sistema.")
    @DeleteMapping("/conta/{contaId}")
    public ResponseEntity<Void> removerContaMotorista(@PathVariable("contaId") Long contaId) {
        motoristaService.removerContaMotorista(contaId);
        return ResponseEntity.noContent().build();
    }

    // Veículo

    @Operation(summary = "Serviço responsável por salvar um veículo do motorista no sistema.", 
    description = "Inclua um veículo do motorista no sistema.")
    @PostMapping("/veiculo/{motoristaId}")
    public ResponseEntity<Veiculo> adicionarVeiculoMotorista(@PathVariable("motoristaId") Long motoristaId, @RequestBody @Valid VeiculoRequest request) {
        Veiculo veiculo = motoristaService.adicionarVeiculoMotorista(motoristaId, request.build());
        return new ResponseEntity<Veiculo>(veiculo, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por editar um veículo do motorista no sistema.", 
    description = "Edite um veículo do motorista no sistema.")
    @PutMapping("/veiculo/{veiculoId}")
    public ResponseEntity<Veiculo> atualizarVeiculoMotorista(@PathVariable("veiculoId") Long veiculoId, @RequestBody VeiculoRequest request) {
        Veiculo veiculo = motoristaService.atualizarVeiculoMotorista(veiculoId, request.build());
        return new ResponseEntity<Veiculo>(veiculo, HttpStatus.OK);
    }

    @Operation(summary = "Serviço responsável por excluir um veículo do motorista no sistema.", 
    description = "Exclua um veículo do motorista no sistema.")
    @DeleteMapping("/veiculo/{motoristaId}")
    public ResponseEntity<Void> removerVeiculoMotorista(@PathVariable("motoristaId") Long motoristaId) {
        motoristaService.removerVeiculoMotorista(motoristaId);
        return ResponseEntity.noContent().build();
    }
}
