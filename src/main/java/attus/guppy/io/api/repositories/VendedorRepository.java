package attus.guppy.io.api.repositories;

import attus.guppy.io.api.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}
