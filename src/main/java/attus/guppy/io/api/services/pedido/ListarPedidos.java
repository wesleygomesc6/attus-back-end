package attus.guppy.io.api.services.pedido;

import attus.guppy.io.api.dtos.pedido.DadosDetalhamentoPedido;
import attus.guppy.io.api.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarPedidos {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<DadosDetalhamentoPedido> execute() {
        return pedidoRepository.findAll()
                .stream()
                .map(DadosDetalhamentoPedido::new)
                .toList();
    }
}