package attus.guppy.io.api.dtos.cliente;

import jakarta.validation.constraints.Email;

public record DadosAtualizacaoCliente(
        String nome,
        @Email String email
) {}