package attus.guppy.io.api.dtos.cliente;

import attus.guppy.io.api.models.Cliente;

public record DadosDetalhamentoCliente(
        Long id,
        String nome,
        String email
) {
    public DadosDetalhamentoCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }
}