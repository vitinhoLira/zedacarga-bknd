package br.com.zedacarga.zedacarga_api.api.cliente;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

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

import br.com.zedacarga.zedacarga_api.modelo.cliente.CartaoCliente;
import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
import br.com.zedacarga.zedacarga_api.modelo.cliente.ClienteService;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Serviço responsável por salvar um cliente no sistema.", description = "Insira um cliente no sistema.")

    // cliente

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRequest request) {
        Cliente cliente = clienteService.save(request.build());
        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por obter todos clientes do sistema.", description = "Obtenha todos os clientes registrados do sistema.")

    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @Operation(summary = "Serviço responsável por obter um cliente do sistema.", description = "Obtenha um cliente do sistema inserindo o 'id'.")

    @GetMapping("/{id}")
    public Cliente obterPorID(@PathVariable Long id) {
        return clienteService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por editar um cliente do sistema.", description = "Edite um cliente do sistema inserindo o 'id'.")

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable("id") Long id, @RequestBody ClienteRequest request) {
        clienteService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por excluir um cliente do sistema.", description = "Exclua um cliente do sistema inserindo o 'id'.")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.ok().build();
    }

    // CartaoCliente

    @Operation(summary = "Serviço responsável por obter um cartão por ID.", description = "Obtenha os detalhes de um cartão com base no ID.")
    @GetMapping("/cartao/{cartaoId}")
    public ResponseEntity<CartaoCliente> obterCartaoPorId(@PathVariable("cartaoId") Long cartaoId) {

        CartaoCliente cartao = clienteService.obterCartaoPorId(cartaoId);
        return ResponseEntity.ok(cartao);
    }

    @Operation(summary = "Serviço responsável por listar todos os cartões de um cliente.", description = "Lista todos os cartões cadastrados para um cliente específico.")
    @GetMapping("/cartoes/{clienteId}")
    public ResponseEntity<List<CartaoCliente>> listarCartoesPorCliente(@PathVariable("clienteId") Long clienteId) {
        List<CartaoCliente> cartoes = clienteService.listarCartoesPorCliente(clienteId);
        return ResponseEntity.ok(cartoes);
    }

    @Operation(summary = "Serviço responsável por salvar um cartão no sistema.", description = "Insira um cartão no sistema.")
    @PostMapping("/cartao/{clienteId}")
    public ResponseEntity<CartaoCliente> adicionarCartaoCliente(@PathVariable("clienteId") Long clienteId,
            @RequestBody @Valid CartaoClienteRequest request) {

        CartaoCliente cartao = clienteService.adicionarCartaoCliente(clienteId, request.build());
        return new ResponseEntity<CartaoCliente>(cartao, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por editar um cartão no sistema.", description = "Edite um cartão no sistema.")

    @PutMapping("/cartao/{cartaoId}")
    public ResponseEntity<CartaoCliente> atualizarCartaoCliente(@PathVariable("cartaoId") Long cartaoId,
            @RequestBody CartaoClienteRequest request) {

        CartaoCliente cartao = clienteService.atualizarCartaoCliente(cartaoId, request.build());
        return new ResponseEntity<CartaoCliente>(cartao, HttpStatus.OK);
    }

    @Operation(summary = "Serviço responsável por excluir um cartão no sistema.", description = "Delete um cartão no sistema.")

    @DeleteMapping("/cartao/{cartaoId}")
    public ResponseEntity<Void> removerCartaoCliente(@PathVariable("cartaoId") Long cartaoId) {

        clienteService.removerCartaoCliente(cartaoId);
        return ResponseEntity.noContent().build();
    }

}
