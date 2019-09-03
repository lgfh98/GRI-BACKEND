package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.repository.ProduccionBGrupoRepository;

@Service
public class ProduccionBGrupoDAO {
	
	@Autowired
	ProduccionBGrupoRepository produccionBGrupoRepository;
	
	public List<ProduccionBGrupo> findAll() {
		return produccionBGrupoRepository.findAll();
	}

	public void save(ProduccionBGrupo produccionBGrupo) {
		produccionBGrupoRepository.save(produccionBGrupo);
	}
	
	public void deleteById(Long id){
		produccionBGrupoRepository.delete(id);
	}

}
