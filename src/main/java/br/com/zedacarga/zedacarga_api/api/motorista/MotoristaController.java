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

import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.MotoristaService;
import io.swagger.v3.oas.annotations.Operation;

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

}
