package attus.guppy.io.api.repositories;

import attus.guppy.io.api.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByVendedorId(Long vendedorId);
}
