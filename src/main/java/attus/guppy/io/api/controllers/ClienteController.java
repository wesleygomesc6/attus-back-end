package attus.guppy.io.api.controllers;

import attus.guppy.io.api.dtos.cliente.DadosAtualizacaoCliente;
import attus.guppy.io.api.dtos.cliente.DadosCadastroCliente;
import attus.guppy.io.api.dtos.cliente.DadosDetalhamentoCliente;
import attus.guppy.io.api.services.cliete.AtualizarCliente;
import attus.guppy.io.api.services.cliete.CadastrarCliente;
import attus.guppy.io.api.services.cliete.DeletarCliente;
import attus.guppy.io.api.services.cliete.ListarClientes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired private ListarClientes listarClientes;
    @Autowired private CadastrarCliente cadastrarCliente;
    @Autowired private AtualizarCliente atualizarCliente;
    @Autowired private DeletarCliente deletarCliente;

    @GetMapping
    @Operation(summary = "Lista todos os clientes")
    public ResponseEntity<List<DadosDetalhamentoCliente>> listar() {
        return ResponseEntity.ok(listarClientes.execute());
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastra um novo cliente")
    public ResponseEntity<DadosDetalhamentoCliente> cadastrar(
            @RequestBody @Valid DadosCadastroCliente dados,
            UriComponentsBuilder uriBuilder) {

        DadosDetalhamentoCliente dto = cadastrarCliente.execute(dados);
        URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualiza um cliente existente")
    public ResponseEntity<DadosDetalhamentoCliente> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoCliente dados) {

        return ResponseEntity.ok(atualizarCliente.execute(id, dados));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Remove um cliente")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarCliente.execute(id);
        return ResponseEntity.noContent().build();
    }
}