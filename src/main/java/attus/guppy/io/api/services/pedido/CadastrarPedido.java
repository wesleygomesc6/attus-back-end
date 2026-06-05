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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CadastrarPedido {

    private static final Logger log = LoggerFactory.getLogger(CadastrarPedido.class);

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private VendedorRepository vendedorRepository;

    public DadosDetalhamentoPedido execute(DadosCadastroPedido dados) {
        log.info("Iniciando cadastro de pedido para clienteId={} vendedorId={}", dados.clienteId(), dados.vendedorId());

        Cliente cliente = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> {
                    log.warn("Cliente não encontrado: id={}", dados.clienteId());
                    return new EntityNotFoundException("Cliente não encontrado");
                });

        Vendedor vendedor = vendedorRepository.findById(dados.vendedorId())
                .orElseThrow(() -> {
                    log.warn("Vendedor não encontrado: id={}", dados.vendedorId());
                    return new EntityNotFoundException("Vendedor não encontrado");
                });

        Pedido pedido = Pedido.builder()
                .titulo(dados.titulo())
                .descricao(dados.descricao())
                .status(StatusPedido.PENDENTE)
                .criadoEm(LocalDateTime.now())
                .cliente(cliente)
                .vendedor(vendedor)
                .build();

        pedidoRepository.save(pedido);
        log.info("Pedido cadastrado com sucesso: id={} titulo='{}' status={}", pedido.getId(), pedido.getTitulo(), pedido.getStatus());

        return new DadosDetalhamentoPedido(pedido);
    }
}