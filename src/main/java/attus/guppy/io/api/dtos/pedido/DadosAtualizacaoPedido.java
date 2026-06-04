package attus.guppy.io.api.dtos.pedido;

import attus.guppy.io.api.models.StatusPedido;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoPedido(
        @Size(min = 5, max = 100) String titulo,
        String descricao,
        StatusPedido status,
        Long clienteId,
        Long vendedorId
) {}