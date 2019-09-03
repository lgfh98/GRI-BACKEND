package co.edu.uniquindio.gri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "LINEASINVESTIGACION")
@Table(name = "LINEASINVESTIGACION", schema = "gri")
public class LineasInvestigacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "NOMBRE", length = 400)
	private String nombre;

	@ManyToMany(mappedBy = "lineasInvestigacion")
	private List<Investigador> investigadores = new ArrayList<Investigador>();

	@ManyToMany()
	@JoinTable(name = "GRUPOS_LINEAS", joinColumns = { @JoinColumn(name = "LINEASINVESTIGACION_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "GRUPOS_ID") }, schema = "gri")
	private List<Grupo> grupos = new ArrayList<Grupo>();

	public LineasInvestigacion() {
	}

	public LineasInvestigacion(long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<Investigador> getInvestigadores() {
		return investigadores;
	}

	public void setInvestigadores(List<Investigador> investigadores) {
		this.investigadores = investigadores;
	}

}