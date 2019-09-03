package co.edu.uniquindio.gri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.ProduccionB;

@Repository
public interface ProduccionBRepository extends JpaRepository<ProduccionB, Long>{

}
