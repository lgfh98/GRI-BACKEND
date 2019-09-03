package co.edu.uniquindio.gri.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
 
@Entity(name = "GRUPOS_INVES")
@Table(name = "GRUPOS_INVES", schema = "gri")
public class GruposInves implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CompositeKey id; 

	@ManyToOne()
	@MapsId("grupos")
	private Grupo grupos;

	@ManyToOne()
	@MapsId("investigadores")
	private Investigador investigadores;

	@Column(name = "ESTADO", length = 50)
	private String estado;

	public GruposInves() {
	}

	public GruposInves(Grupo grupo, Investigador investigador, String estado) {
		this.id = new CompositeKey(grupo.getId(), investigador.getId());
		this.grupos = grupo;
		this.investigadores = investigador;
		this.estado = estado;
	}

	public CompositeKey getId() {
		return id;
	}

	public void setId(CompositeKey id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupos;
	}

	public void setGrupo(Grupo grupo) {
		this.grupos = grupo;
	}

	public Investigador getInvestigador() {
		return investigadores;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigadores = investigador;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
