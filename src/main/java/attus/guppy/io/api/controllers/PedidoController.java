package attus.guppy.io.api.controllers;

import attus.guppy.io.api.dtos.pedido.DadosAtualizacaoPedido;
import attus.guppy.io.api.dtos.pedido.DadosCadastroPedido;
import attus.guppy.io.api.dtos.pedido.DadosDetalhamentoPedido;
import attus.guppy.io.api.services.pedido.*;
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

@Tag(name = "Pedidos", description = "Operações relacionadas a pedidos")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired private ListarPedidos listarPedidos;
    @Autowired private CadastrarPedido cadastrarPedido;
    @Autowired private AtualizarPedido atualizarPedido;
    @Autowired private DeletarPedido deletarPedido;

    @GetMapping
    @Operation(summary = "Lista todos os pedidos")
    public ResponseEntity<List<DadosDetalhamentoPedido>> listar() {
        return ResponseEntity.ok(listarPedidos.execute());
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastra um novo pedido")
    public ResponseEntity<DadosDetalhamentoPedido> cadastrar(
            @RequestBody @Valid DadosCadastroPedido dados,
            UriComponentsBuilder uriBuilder) {

        DadosDetalhamentoPedido dto = cadastrarPedido.execute(dados);
        URI uri = uriBuilder.path("/pedidos/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualiza um pedido existente")
    public ResponseEntity<DadosDetalhamentoPedido> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoPedido dados) {

        return ResponseEntity.ok(atualizarPedido.execute(id, dados));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Remove um pedido")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarPedido.execute(id);
        return ResponseEntity.noContent().build();
    }
}