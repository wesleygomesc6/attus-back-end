package attus.guppy.io.api.services.cliete;

import attus.guppy.io.api.dtos.cliente.DadosDetalhamentoCliente;
import attus.guppy.io.api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarClientes {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<DadosDetalhamentoCliente> execute() {
        return clienteRepository.findAll()
                .stream()
                .map(DadosDetalhamentoCliente::new)
                .toList();
    }
}