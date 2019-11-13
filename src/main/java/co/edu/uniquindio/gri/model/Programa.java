package co.edu.uniquindio.gri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "PROGRAMAS")
@Table(name = "PROGRAMAS", schema = "gri")
public class Programa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "INFORMACIONGENERAL", length = 2000)
	private String informaciongeneral;

	@Column(name = "MISION", length = 2000)
	private String mision;

	@Column(name = "VISION", length = 2000)
	private String vision;

	@Column(name = "CONTACTO", length = 450)
	private String contacto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FACULTADES_ID")
	private Facultad facultad;

	@ManyToMany(mappedBy = "programas", cascade = CascadeType.MERGE)
	private List<Grupo> grupos = new ArrayList<Grupo>();

	public Programa() {
	}

	public Programa(long id, String nombre, Facultad facultad, String informaciongeneral, String contacto,
			String vision, String mision) {
		this.id = id;
		this.nombre = nombre;
		this.informaciongeneral = informaciongeneral;
		this.contacto = contacto;
		this.facultad = facultad;
		this.mision = mision;
		this.vision = vision;
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

	public String getInformaciongeneral() {
		return informaciongeneral;
	}

	public void setInformaciongeneral(String informaciongeneral) {
		this.informaciongeneral = informaciongeneral;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
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

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}