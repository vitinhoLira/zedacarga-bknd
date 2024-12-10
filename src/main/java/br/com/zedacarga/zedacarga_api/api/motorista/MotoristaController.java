package br.com.zedacarga.zedacarga_api.api.motorista;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.MotoristaService;

@RestController
@RequestMapping("/api/motorista")
@CrossOrigin
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<Motorista> save(@RequestBody MotoristaRequest request) {

        Motorista motorista = motoristaService.save(request.build());
        return new ResponseEntity<Motorista>(motorista, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Motorista> listarTodos() {
        return motoristaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Motorista obterPorID(@PathVariable Long id) {
        return motoristaService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorista> update(@PathVariable("id") Long id, @RequestBody MotoristaRequest request) {
        motoristaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

}
