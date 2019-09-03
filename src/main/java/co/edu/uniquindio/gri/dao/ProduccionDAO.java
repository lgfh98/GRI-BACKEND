package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Produccion;
import co.edu.uniquindio.gri.repository.ProduccionRepository;

@Service
public class ProduccionDAO {
	
	@Autowired
	ProduccionRepository produccionRepository;
	
	public List<Produccion> findAll() {
		return produccionRepository.findAll();
	}

	public void save(Produccion produccion) {
		produccionRepository.save(produccion);
	}

}
