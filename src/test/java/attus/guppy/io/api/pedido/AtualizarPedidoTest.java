package attus.guppy.io.api.pedido;

import attus.guppy.io.api.dtos.pedido.DadosAtualizacaoPedido;
import attus.guppy.io.api.models.*;
import attus.guppy.io.api.repositories.ClienteRepository;
import attus.guppy.io.api.repositories.PedidoRepository;
import attus.guppy.io.api.repositories.VendedorRepository;
import attus.guppy.io.api.services.pedido.AtualizarPedido;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarPedidoTest {

    @InjectMocks private AtualizarPedido atualizarPedido;
    @Mock private PedidoRepository pedidoRepository;
    @Mock private ClienteRepository clienteRepository;
    @Mock private VendedorRepository vendedorRepository;

    private Cliente cliente;
    private Vendedor vendedor;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder().id(1L).nome("Alpha").email("alpha@email.com").build();
        vendedor = Vendedor.builder().id(1L).nome("Carlos").build();
        pedido = Pedido.builder()
                .id(1L).titulo("Pedido original").descricao("Descrição original")
                .status(StatusPedido.PENDENTE).cliente(cliente).vendedor(vendedor)
                .build();
    }

    @Test
    @DisplayName("Deve atualizar título e descrição com sucesso")
    void deveAtualizarDadosBasicosComSucesso() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        DadosAtualizacaoPedido dados = new DadosAtualizacaoPedido("Novo título", "Nova descrição", null, null, null);
        var resultado = atualizarPedido.execute(1L, dados);

        assertThat(resultado.titulo()).isEqualTo("Novo título");
        assertThat(resultado.descricao()).isEqualTo("Nova descrição");
        assertThat(resultado.status()).isEqualTo(StatusPedido.PENDENTE);
    }

    @Test
    @DisplayName("Deve cancelar pedido PENDENTE com sucesso")
    void deveCancelarPedidoPendente() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        DadosAtualizacaoPedido dados = new DadosAtualizacaoPedido(null, null, StatusPedido.CANCELADO, null, null);
        var resultado = atualizarPedido.execute(1L, dados);

        assertThat(resultado.status()).isEqualTo(StatusPedido.CANCELADO);
    }

    @Test
    @DisplayName("Deve cancelar pedido EM_ANALISE com sucesso")
    void deveCancelarPedidoEmAnalise() {
        pedido.setStatus(StatusPedido.EM_ANALISE);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        DadosAtualizacaoPedido dados = new DadosAtualizacaoPedido(null, null, StatusPedido.CANCELADO, null, null);
        var resultado = atualizarPedido.execute(1L, dados);

        assertThat(resultado.status()).isEqualTo(StatusPedido.CANCELADO);
    }

    @Test
    @DisplayName("Não deve cancelar pedido EM_ANDAMENTO")
    void naoDeveCancelarPedidoEmAndamento() {
        pedido.setStatus(StatusPedido.EM_ANDAMENTO);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        DadosAtualizacaoPedido dados = new DadosAtualizacaoPedido(null, null, StatusPedido.CANCELADO, null, null);

        assertThatThrownBy(() -> atualizarPedido.execute(1L, dados))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("PENDENTE ou EM_ANALISE");
    }

    @Test
    @DisplayName("Deve concluir pedido EM_ANDAMENTO com sucesso")
    void deveConcluirPedidoEmAndamento() {
        pedido.setStatus(StatusPedido.EM_ANDAMENTO);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        DadosAtualizacaoPedido dados = new DadosAtualizacaoPedido(null, null, StatusPedido.CONCLUIDO, null, null);
        var resultado = atualizarPedido.execute(1L, dados);

        assertThat(resultado.status()).isEqualTo(StatusPedido.CONCLUIDO);
    }

    @Test
    @DisplayName("Não deve concluir pedido PENDENTE")
    void naoDeveConcluirPedidoPendente() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        DadosAtualizacaoPedido dados = new DadosAtualizacaoPedido(null, null, StatusPedido.CONCLUIDO, null, null);

        assertThatThrownBy(() -> atualizarPedido.execute(1L, dados))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("EM_ANDAMENTO");
    }

    @Test
    @DisplayName("Não deve alterar pedido CONCLUIDO")
    void naoDeveAlterarPedidoConcluido() {
        pedido.setStatus(StatusPedido.CONCLUIDO);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        DadosAtualizacaoPedido dados = new DadosAtualizacaoPedido("Novo título", null, null, null, null);

        assertThatThrownBy(() -> atualizarPedido.execute(1L, dados))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("concluído não pode ser alterado");
    }

    @Test
    @DisplayName("Deve lançar exceção quando pedido não encontrado")
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        DadosAtualizacaoPedido dados = new DadosAtualizacaoPedido(null, null, null, null, null);

        assertThatThrownBy(() -> atualizarPedido.execute(99L, dados))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pedido não encontrado");
    }
}