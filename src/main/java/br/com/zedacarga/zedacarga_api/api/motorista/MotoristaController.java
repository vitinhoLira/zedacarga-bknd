package br.com.zedacarga.zedacarga_api.api.motorista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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

}
