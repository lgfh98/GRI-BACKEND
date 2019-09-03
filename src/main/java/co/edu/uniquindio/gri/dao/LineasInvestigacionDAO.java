package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.LineasInvestigacion;
import co.edu.uniquindio.gri.repository.LineasInvestigacionRepository;

@Service
public class LineasInvestigacionDAO {

	@Autowired
	LineasInvestigacionRepository lineasInvestigacionRepository;

	public List<LineasInvestigacion> findAll() {
		return lineasInvestigacionRepository.findAll();
	}

	public void save(LineasInvestigacion lineasInvestigacion) {
		lineasInvestigacionRepository.save(lineasInvestigacion);
	}
	
	public void deleteAll() {
		lineasInvestigacionRepository.eliminarGruposLineas();
		lineasInvestigacionRepository.eliminarInvestLineas();
		lineasInvestigacionRepository.eliminarLineasInvestigacion();
	}
	
	
}
