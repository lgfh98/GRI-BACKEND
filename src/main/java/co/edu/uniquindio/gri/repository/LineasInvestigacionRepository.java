package co.edu.uniquindio.gri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.gri.model.LineasInvestigacion;

@Repository
public interface LineasInvestigacionRepository extends JpaRepository<LineasInvestigacion, Long> {

	@Transactional
	@Modifying
	@Query(value="delete from gri.grupos_lineas", nativeQuery=true)
	public void eliminarGruposLineas();
	
	@Transactional
	@Modifying
	@Query(value="delete from gri.invest_lineas", nativeQuery=true)
	public void eliminarInvestLineas();
	
	@Transactional
	@Modifying
	@Query(value="delete from gri.lineasinvestigacion", nativeQuery=true)
	public void eliminarLineasInvestigacion();
}
