package attus.guppy.io.api.repositories;

import attus.guppy.io.api.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
