package attus.guppy.io.api.services.cliete;

import attus.guppy.io.api.dtos.cliente.DadosAtualizacaoCliente;
import attus.guppy.io.api.dtos.cliente.DadosDetalhamentoCliente;
import attus.guppy.io.api.models.Cliente;
import attus.guppy.io.api.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizarCliente {

    @Autowired
    private ClienteRepository clienteRepository;

    public DadosDetalhamentoCliente execute(Long id, DadosAtualizacaoCliente dados) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        if (dados.nome() != null)     cliente.setNome(dados.nome());
        if (dados.email() != null)    cliente.setEmail(dados.email());

        return new DadosDetalhamentoCliente(cliente);
    }
}