package attus.guppy.io.api.dtos.pedido;

import attus.guppy.io.api.models.Pedido;
import attus.guppy.io.api.models.StatusPedido;

import java.time.LocalDateTime;

public record DadosDetalhamentoPedido(
        Long id,
        String titulo,
        String descricao,
        StatusPedido status,
        Long clienteId,
        String clienteNome,
        Long vendedorId,
        String vendedorNome,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
    public DadosDetalhamentoPedido(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getTitulo(),
                pedido.getDescricao(),
                pedido.getStatus(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getVendedor().getId(),
                pedido.getVendedor().getNome(),
                pedido.getCriadoEm(),
                pedido.getAtualizadoEm()
        );
    }
}