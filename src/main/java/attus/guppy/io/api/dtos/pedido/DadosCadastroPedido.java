package attus.guppy.io.api.dtos.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroPedido(
        @NotBlank @Size(min = 5, max = 100) String titulo,
        @NotBlank String descricao,
        @NotNull Long clienteId,
        @NotNull Long vendedorId
) {}