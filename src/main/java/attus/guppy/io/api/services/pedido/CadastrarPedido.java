package attus.guppy.io.api.services.pedido;

import attus.guppy.io.api.dtos.pedido.DadosCadastroPedido;
import attus.guppy.io.api.dtos.pedido.DadosDetalhamentoPedido;
import attus.guppy.io.api.models.Cliente;
import attus.guppy.io.api.models.Pedido;
import attus.guppy.io.api.models.StatusPedido;
import attus.guppy.io.api.models.Vendedor;
import attus.guppy.io.api.repositories.ClienteRepository;
import attus.guppy.io.api.repositories.PedidoRepository;
import attus.guppy.io.api.repositories.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarPedido {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private VendedorRepository vendedorRepository;

    public DadosDetalhamentoPedido execute(DadosCadastroPedido dados) {
        Cliente cliente = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Vendedor vendedor = vendedorRepository.findById(dados.vendedorId())
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado"));

        Pedido pedido = Pedido.builder()
                .titulo(dados.titulo())
                .descricao(dados.descricao())
                .status(StatusPedido.PENDENTE)
                .cliente(cliente)
                .vendedor(vendedor)
                .build();

        pedidoRepository.save(pedido);
        return new DadosDetalhamentoPedido(pedido);
    }
}