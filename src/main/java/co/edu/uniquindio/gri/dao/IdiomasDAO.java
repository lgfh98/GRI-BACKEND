package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Idiomas;
import co.edu.uniquindio.gri.repository.IdiomasRepository;

@Service
public class IdiomasDAO {
	
	@Autowired
	IdiomasRepository idiomasRepository;
	
	public List<Idiomas> findAll() {
		return idiomasRepository.findAll();
	}

	public void save(Idiomas idiomas) {
		idiomasRepository.save(idiomas);
	}

}
