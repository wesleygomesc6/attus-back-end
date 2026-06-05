package attus.guppy.io.api.services.pedido;

import attus.guppy.io.api.dtos.pedido.DadosAtualizacaoPedido;
import attus.guppy.io.api.dtos.pedido.DadosDetalhamentoPedido;
import attus.guppy.io.api.models.Pedido;
import attus.guppy.io.api.models.StatusPedido;
import attus.guppy.io.api.repositories.ClienteRepository;
import attus.guppy.io.api.repositories.PedidoRepository;
import attus.guppy.io.api.repositories.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AtualizarPedido {

    private static final Logger log = LoggerFactory.getLogger(AtualizarPedido.class);

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private VendedorRepository vendedorRepository;

    public DadosDetalhamentoPedido execute(Long id, DadosAtualizacaoPedido dados) {
        log.info("Iniciando atualização do pedido: id={}", id);

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pedido não encontrado: id={}", id);
                    return new EntityNotFoundException("Pedido não encontrado");
                });

        StatusPedido statusAnterior = pedido.getStatus();
        validarTransicaoDeStatus(statusAnterior, dados.status());

        pedido.setAtualizadoEm(LocalDateTime.now());
        if (dados.titulo() != null)    pedido.setTitulo(dados.titulo());
        if (dados.descricao() != null) pedido.setDescricao(dados.descricao());
        if (dados.status() != null) {
            log.info("Alterando status do pedido id={}: {} -> {}", id, statusAnterior, dados.status());
            pedido.setStatus(dados.status());
        }

        if (dados.clienteId() != null) {
            pedido.setCliente(clienteRepository.findById(dados.clienteId())
                    .orElseThrow(() -> {
                        log.warn("Cliente não encontrado: id={}", dados.clienteId());
                        return new EntityNotFoundException("Cliente não encontrado");
                    }));
        }

        if (dados.vendedorId() != null) {
            pedido.setVendedor(vendedorRepository.findById(dados.vendedorId())
                    .orElseThrow(() -> {
                        log.warn("Vendedor não encontrado: id={}", dados.vendedorId());
                        return new EntityNotFoundException("Vendedor não encontrado");
                    }));
        }

        log.info("Pedido atualizado com sucesso: id={}", id);
        return new DadosDetalhamentoPedido(pedido);
    }

    private void validarTransicaoDeStatus(StatusPedido atual, StatusPedido novo) {
        if (novo == null) return;

        if (atual == StatusPedido.CONCLUIDO) {
            log.warn("Tentativa de alterar pedido já concluído");
            throw new IllegalStateException("Pedido concluído não pode ser alterado");
        }

        if (novo == StatusPedido.CANCELADO && atual != StatusPedido.PENDENTE && atual != StatusPedido.EM_ANALISE) {
            log.warn("Transição de status inválida: {} -> {}", atual, novo);
            throw new IllegalStateException("Pedido só pode ser cancelado se estiver PENDENTE ou EM_ANALISE. Status atual: " + atual);
        }

        if (novo == StatusPedido.CONCLUIDO && atual != StatusPedido.EM_ANDAMENTO) {
            log.warn("Transição de status inválida: {} -> {}", atual, novo);
            throw new IllegalStateException("Pedido só pode ser concluído se estiver EM_ANDAMENTO. Status atual: " + atual);
        }
    }
}