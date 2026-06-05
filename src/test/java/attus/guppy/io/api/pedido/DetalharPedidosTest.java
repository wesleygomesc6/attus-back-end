package attus.guppy.io.api.pedido;

import attus.guppy.io.api.repositories.PedidoRepository;
import attus.guppy.io.api.services.pedido.DeletarPedido;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarPedidoTest {

    @InjectMocks private DeletarPedido deletarPedido;
    @Mock private PedidoRepository pedidoRepository;

    @Test
    @DisplayName("Deve deletar pedido com sucesso")
    void deveDeletarPedidoComSucesso() {
        when(pedidoRepository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> deletarPedido.execute(1L)).doesNotThrowAnyException();
        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando pedido não encontrado")
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        when(pedidoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> deletarPedido.execute(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pedido não encontrado");

        verify(pedidoRepository, never()).deleteById(any());
    }
}
