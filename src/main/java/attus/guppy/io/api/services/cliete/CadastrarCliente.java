package attus.guppy.io.api.services.cliete;
import attus.guppy.io.api.dtos.cliente.DadosCadastroCliente;
import attus.guppy.io.api.dtos.cliente.DadosDetalhamentoCliente;
import attus.guppy.io.api.models.Cliente;
import attus.guppy.io.api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarCliente {

    @Autowired
    private ClienteRepository clienteRepository;

    public DadosDetalhamentoCliente execute(DadosCadastroCliente dados) {
        Cliente cliente = Cliente.builder()
                .nome(dados.nome())
                .email(dados.email())
                .build();

        clienteRepository.save(cliente);
        return new DadosDetalhamentoCliente(cliente);
    }
}