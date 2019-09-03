package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.LineasInvestigacion;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;
import co.edu.uniquindio.gri.repository.GrupoRepository;

@Service
public class GrupoDAO {

	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	ProduccionBGrupoDAO produccionBGrupoDAO;
	
	@Autowired
	ProduccionGrupoDAO produccionGrupoDAO;
	
	public List<Grupo> findAll(){
		return grupoRepository.findAll();
	}
	
	public Grupo findOne(Long id){
		return grupoRepository.findOne(id);
	}
	
	public void save (Grupo grupo){
		grupoRepository.save(grupo);
		List <ProduccionBGrupo> produccionesB = grupo.getProduccionBibliografica();
		for (int i = 0; i< produccionesB.size(); i++) {
			ProduccionBGrupo produccionB = produccionesB.get(i);
			if(produccionB.debeEliminarse){
				produccionBGrupoDAO.deleteById(produccionB.getId());
			}
		}
		
		List <ProduccionGrupo> producciones = grupo.getProduccion();
		for (int i = 0; i< producciones.size(); i++) {
			ProduccionGrupo produccion = producciones.get(i);
			if(produccion.debeEliminarse){
				produccionGrupoDAO.deleteById(produccion.getId());
			}
		}
	}
	
	public void deleteLineas(Grupo grupo) {
		List<LineasInvestigacion> lineasInvestigacion =grupo.getLineasInvestigacion();
		for (int i = 0; i < lineasInvestigacion.size(); i++) {
			LineasInvestigacion lineaInvestigacion = lineasInvestigacion.get(i);
			lineasInvestigacion.remove(lineaInvestigacion);
		}
	}
}
