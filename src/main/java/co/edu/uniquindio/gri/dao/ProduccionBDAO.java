package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.ProduccionB;
import co.edu.uniquindio.gri.repository.ProduccionBRepository;

@Service
public class ProduccionBDAO {
	
	@Autowired
	ProduccionBRepository produccionBRepository;
	
	public List<ProduccionB> findAll() {
		return produccionBRepository.findAll();
	}

	public void save(ProduccionB produccionB) {
		produccionBRepository.save(produccionB);
	}
	
	public void deleteById(Long id){
		produccionBRepository.delete(id);
	}

}
