package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.ProduccionGrupo;
import co.edu.uniquindio.gri.repository.ProduccionGrupoRepository;

@Service
public class ProduccionGrupoDAO {
	
	@Autowired
	ProduccionGrupoRepository produccionGrupoRepository;
	
	public List<ProduccionGrupo> findAll() {
		return produccionGrupoRepository.findAll();
	}

	public void save(ProduccionGrupo produccionGrupo) {
		produccionGrupoRepository.save(produccionGrupo);
	}
	
	public void deleteById(Long id){
		produccionGrupoRepository.delete(id);
	}

}
