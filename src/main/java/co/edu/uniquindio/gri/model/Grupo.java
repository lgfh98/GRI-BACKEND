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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "GRUPOS")
@Table(name = "GRUPOS", schema = "gri")
public class Grupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", length = 300)
	private long id;

	@Column(name = "NOMBRE", length = 300)
	private String nombre;

	@Column(name = "AREACONOCIMIENTO", length = 300)
	private String areaConocimiento;

	@Column(name = "ANIOFUNDACION", length = 300)
	private String anioFundacion;

	@Column(name = "LIDER", length = 300)
	private String lider;

	@Column(name = "CATEGORIA", length = 300)
	private String categoria;

	@Column(name = "INFORMACIONGENERAL", length = 2000)
	private String informaciongeneral;

	/** The contacto */
	@Column(name = "CONTACTO", length = 450)
	private String contacto;

	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "GRUPOS_LINEAS", joinColumns = { @JoinColumn(name = "GRUPOS_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "LINEASINVESTIGACION_ID") }, schema = "gri")
	private List<LineasInvestigacion> lineasInvestigacion = new ArrayList<LineasInvestigacion>();

	@OneToMany(mappedBy = "grupo", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ProduccionGrupo> produccion = new ArrayList<ProduccionGrupo>();

	@OneToMany(mappedBy = "grupo", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ProduccionBGrupo> produccionBibliografica = new ArrayList<ProduccionBGrupo>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CENTROS_ID")
	private Centro centro;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "PROGRAMAS_GRUPOS", joinColumns = { @JoinColumn(name = "GRUPOS_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PROGRAMAS_ID") }, schema = "gri")
	private List<Programa> programas = new ArrayList<Programa>();

	@OneToMany(mappedBy = "grupos", fetch = FetchType.EAGER)
	private List<GruposInves> investigadores = new ArrayList<GruposInves>();

	public Grupo(long id, String nombre, String areaConocimiento, String anioFundacion, String lider, String categoria,
			String informaciongeneral, String contacto, List<LineasInvestigacion> lineasInvestigacion,
			List<ProduccionGrupo> produccion, List<ProduccionBGrupo> produccionBibliografica, Centro centro,
			List<GruposInves> investigadores) {
		this.id = id;
		this.nombre = nombre;
		this.areaConocimiento = areaConocimiento;
		this.anioFundacion = anioFundacion;
		this.lider = lider;
		this.categoria = categoria;
		this.informaciongeneral = informaciongeneral;
		this.contacto = contacto;
		this.lineasInvestigacion = lineasInvestigacion;
		this.produccion = produccion;
		this.produccionBibliografica = produccionBibliografica;
		this.centro = centro;
		this.investigadores = investigadores;
	}

	public Grupo() {

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

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getAnioFundacion() {
		return anioFundacion;
	}

	public void setAnioFundacion(String anioFundacion) {
		this.anioFundacion = anioFundacion;
	}

	public String getLider() {
		return lider;
	}

	public void setLider(String lider) {
		this.lider = lider;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	public List<LineasInvestigacion> getLineasInvestigacion() {
		return lineasInvestigacion;
	}

	public void setLineasInvestigacion(List<LineasInvestigacion> lineasInvestigacion) {
		this.lineasInvestigacion = lineasInvestigacion;
	}

	public List<ProduccionGrupo> getProduccion() {
		return produccion;
	}

	public void setProduccion(List<ProduccionGrupo> produccion) {
		this.produccion = produccion;
	}

	public List<ProduccionBGrupo> getProduccionBibliografica() {
		return produccionBibliografica;
	}

	public void setProduccionBibliografica(List<ProduccionBGrupo> produccionBibliografica) {
		this.produccionBibliografica = produccionBibliografica;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}

	public void removeLineasInvestigacion(LineasInvestigacion lineas) {
		lineasInvestigacion.remove(lineas);
		lineas.getGrupos().remove(this);
	}

	public List<GruposInves> getInvestigadores() {
		return investigadores;
	}

	public void setInvestigadores(List<GruposInves> investigadores) {
		this.investigadores = investigadores;
	}

	public void addInvestigador(Investigador investigador, String estado) {
		GruposInves gruposInves = new GruposInves(this, investigador, estado);
		investigadores.add(gruposInves);
		investigador.getGrupos().add(gruposInves);
	}

	public void removePrograma(Programa programa) {
		programas.remove(programa);
		programa.getGrupos().remove(this);
	}
}