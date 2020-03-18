package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.uniquindio.gri.model.ReconocimientosInvestigador;
import co.edu.uniquindio.gri.repository.ReconocimientosInvestigadorRepository;

public class ReconocimientosInvestigadorDAO {

	@Autowired
	ReconocimientosInvestigadorRepository reconocimientosInvestigadorRepository;

	public List<ReconocimientosInvestigador> findAll() {
		return reconocimientosInvestigadorRepository.findAll();
	}

	public void save(ReconocimientosInvestigador reconocimientosInvestigador) {
		reconocimientosInvestigadorRepository.save(reconocimientosInvestigador);
	}

}
