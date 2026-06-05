package attus.guppy.io.api.pedido;

import attus.guppy.io.api.dtos.pedido.DadosCadastroPedido;
import attus.guppy.io.api.dtos.pedido.DadosDetalhamentoPedido;
import attus.guppy.io.api.models.*;
import attus.guppy.io.api.repositories.ClienteRepository;
import attus.guppy.io.api.repositories.PedidoRepository;
import attus.guppy.io.api.repositories.VendedorRepository;
import attus.guppy.io.api.services.pedido.CadastrarPedido;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarPedidoTest {

    @InjectMocks private CadastrarPedido cadastrarPedido;
    @Mock private PedidoRepository pedidoRepository;
    @Mock private ClienteRepository clienteRepository;
    @Mock private VendedorRepository vendedorRepository;

    @Test
    @DisplayName("Deve cadastrar pedido com sucesso")
    void deveCadastrarPedidoComSucesso() {
        Cliente cliente = Cliente.builder().id(1L).nome("Alpha").email("alpha@email.com").build();
        Vendedor vendedor = Vendedor.builder().id(1L).nome("Carlos").build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(pedidoRepository.save(any())).thenAnswer(i -> {
            Pedido p = i.getArgument(0);
            p.setId(1L);
            return p;
        });

        DadosCadastroPedido dados = new DadosCadastroPedido(
                "Pedido de notebooks", "10 notebooks para TI", 1L, 1L
        );

        DadosDetalhamentoPedido resultado = cadastrarPedido.execute(dados);

        assertThat(resultado).isNotNull();
        assertThat(resultado.titulo()).isEqualTo("Pedido de notebooks");
        assertThat(resultado.status()).isEqualTo(StatusPedido.PENDENTE);
        assertThat(resultado.clienteNome()).isEqualTo("Alpha");
        verify(pedidoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não encontrado")
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        DadosCadastroPedido dados = new DadosCadastroPedido(
                "Pedido teste", "Descrição teste", 99L, 1L
        );

        assertThatThrownBy(() -> cadastrarPedido.execute(dados))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cliente não encontrado");

        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando vendedor não encontrado")
    void deveLancarExcecaoQuandoVendedorNaoEncontrado() {
        Cliente cliente = Cliente.builder().id(1L).nome("Alpha").email("alpha@email.com").build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(vendedorRepository.findById(99L)).thenReturn(Optional.empty());

        DadosCadastroPedido dados = new DadosCadastroPedido(
                "Pedido teste", "Descrição teste", 1L, 99L
        );

        assertThatThrownBy(() -> cadastrarPedido.execute(dados))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Vendedor não encontrado");

        verify(pedidoRepository, never()).save(any());
    }
}