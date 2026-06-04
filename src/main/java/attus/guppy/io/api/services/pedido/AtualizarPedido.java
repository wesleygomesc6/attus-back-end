package attus.guppy.io.api.services.pedido;

import attus.guppy.io.api.dtos.pedido.DadosAtualizacaoPedido;
import attus.guppy.io.api.dtos.pedido.DadosDetalhamentoPedido;
import attus.guppy.io.api.models.Pedido;
import attus.guppy.io.api.models.StatusPedido;
import attus.guppy.io.api.repositories.ClienteRepository;
import attus.guppy.io.api.repositories.PedidoRepository;
import attus.guppy.io.api.repositories.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizarPedido {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private VendedorRepository vendedorRepository;

    public DadosDetalhamentoPedido execute(Long id, DadosAtualizacaoPedido dados) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        validarTransicaoDeStatus(pedido.getStatus(), dados.status());

        if (dados.titulo() != null)    pedido.setTitulo(dados.titulo());
        if (dados.descricao() != null) pedido.setDescricao(dados.descricao());
        if (dados.status() != null)    pedido.setStatus(dados.status());

        if (dados.clienteId() != null) {
            pedido.setCliente(clienteRepository.findById(dados.clienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado")));
        }

        if (dados.vendedorId() != null) {
            pedido.setVendedor(vendedorRepository.findById(dados.vendedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado")));
        }

        return new DadosDetalhamentoPedido(pedido);
    }

    private void validarTransicaoDeStatus(StatusPedido atual, StatusPedido novo) {
        if (novo == null) return;

        if (atual == StatusPedido.CONCLUIDO) {
            throw new IllegalStateException("Pedido concluído não pode ser alterado");
        }

        if (novo == StatusPedido.CANCELADO) {
            if (atual != StatusPedido.PENDENTE && atual != StatusPedido.EM_ANALISE) {
                throw new IllegalStateException(
                        "Pedido só pode ser cancelado se estiver PENDENTE ou EM_ANALISE. Status atual: " + atual
                );
            }
        }

        if (novo == StatusPedido.CONCLUIDO) {
            if (atual != StatusPedido.EM_ANDAMENTO) {
                throw new IllegalStateException(
                        "Pedido só pode ser concluído se estiver EM_ANDAMENTO. Status atual: " + atual
                );
            }
        }
    }
}