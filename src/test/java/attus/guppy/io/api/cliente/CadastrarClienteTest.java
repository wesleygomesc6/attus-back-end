package attus.guppy.io.api.cliente;

import attus.guppy.io.api.dtos.cliente.DadosCadastroCliente;
import attus.guppy.io.api.dtos.cliente.DadosDetalhamentoCliente;
import attus.guppy.io.api.models.Cliente;
import attus.guppy.io.api.repositories.ClienteRepository;
import attus.guppy.io.api.services.cliete.CadastrarCliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarClienteTest {

    @InjectMocks private CadastrarCliente cadastrarCliente;
    @Mock private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve cadastrar cliente com sucesso")
    void deveCadastrarClienteComSucesso() {
        when(clienteRepository.save(any())).thenAnswer(i -> {
            Cliente c = i.getArgument(0);
            c.setId(1L);
            return c;
        });

        DadosCadastroCliente dados = new DadosCadastroCliente("Alpha Ltda", "alpha@email.com");
        DadosDetalhamentoCliente resultado = cadastrarCliente.execute(dados);

        assertThat(resultado).isNotNull();
        assertThat(resultado.nome()).isEqualTo("Alpha Ltda");
        assertThat(resultado.email()).isEqualTo("alpha@email.com");
        verify(clienteRepository, times(1)).save(any());
    }
}
