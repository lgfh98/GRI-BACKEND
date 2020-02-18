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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name = "CENTROS")
@Table(name = "CENTROS", schema = "gri")
public class Centro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name="INFORMACIONGENERAL", length = 2000)
	private String informaciongeneral;
	
	@Column(name="CONTACTO", length = 450)
	private String contacto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FACULTADES_ID")
	private Facultad facultad;

	@OneToMany(mappedBy = "centro", cascade = CascadeType.MERGE)
	private List<Grupo> grupo = new ArrayList<>();

	public Centro() {
	}

	public Centro(long id, String nombre, Facultad facultad,String informaciongeneral,String contacto) {
		this.id = id;
		this.nombre = nombre;
		this.informaciongeneral=informaciongeneral;
		this.contacto=contacto;
		this.facultad = facultad;
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

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}

	public List<Grupo> getGrupo() {
		return grupo;
	}

	public void setGrupo(List<Grupo> grupo) {
		this.grupo = grupo;
	}

}