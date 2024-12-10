package br.com.zedacarga.zedacarga_api.api.viagem;

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

import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import br.com.zedacarga.zedacarga_api.modelo.viagem.ViagemService;

@RestController
@RequestMapping("/api/viagem")
@CrossOrigin
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @PostMapping
    public ResponseEntity<Viagem> save(@RequestBody ViagemRequest request) {
        Viagem viagem = viagemService.save(request.build());
        return new ResponseEntity<Viagem>(viagem, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Viagem> listarTodos() {
        return viagemService.listarTodos();
    }

    @GetMapping("/{id}")
    public Viagem obterPorID(@PathVariable Long id) {
        return viagemService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viagem> update(@PathVariable("id") Long id, @RequestBody ViagemRequest request) {
        viagemService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

}
