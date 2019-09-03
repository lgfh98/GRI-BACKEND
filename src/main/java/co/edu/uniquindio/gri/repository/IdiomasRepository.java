package co.edu.uniquindio.gri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.Idiomas;

@Repository
public interface IdiomasRepository extends JpaRepository<Idiomas, Long>{

}
