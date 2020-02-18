package co.edu.uniquindio.gri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "FACULTADES")
@Table(name = "FACULTADES", schema = "gri")
public class Facultad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "MISION", length = 2000)
	private String mision;

	@Column(name = "VISION", length = 2000)
	private String vision;

	@Column(name = "CONTACTO", length = 450)
	private String contacto;

	@OneToMany(mappedBy = "facultad", cascade = CascadeType.MERGE)
	private List<Centro> centros = new ArrayList<>();

	@OneToMany(mappedBy = "facultad", cascade = CascadeType.MERGE)
	private List<Programa> programas = new ArrayList<>();

	public Facultad() {
	}

	public Facultad(long id, String nombre, String mision, String vision, String contacto) {
		this.id = id;
		this.nombre = nombre;
		this.mision = mision;
		this.vision = vision;
		this.contacto = contacto;
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

	public String getMision() {
		return mision;
	}

	public void setMision(String mision) {
		this.mision = mision;
	}

	public String getVision() {
		return vision;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Centro> getCentros() {
		return centros;
	}

	public void setCentros(List<Centro> centros) {
		this.centros = centros;
	}

	public List<Programa> getPrograma() {
		return programas;
	}

	public void setPrograma(List<Programa> programa) {
		this.programas = programa;
	}

}
