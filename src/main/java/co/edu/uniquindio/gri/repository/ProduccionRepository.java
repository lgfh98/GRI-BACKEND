package co.edu.uniquindio.gri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.Produccion;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion, Long>{

}
