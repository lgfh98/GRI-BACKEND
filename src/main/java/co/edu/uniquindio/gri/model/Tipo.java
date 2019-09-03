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

@Entity(name = "TIPOS")
@Table(name = "TIPOS", schema = "gri")
public class Tipo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "NOMBRE", length = 100)
	private String nombre;

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.MERGE)
	private List<Produccion> produccion = new ArrayList<Produccion>();

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.MERGE)
	private List<ProduccionB> produccionBibliografica = new ArrayList<ProduccionB>();

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.MERGE)
	private List<co.edu.uniquindio.gri.model.ProduccionGrupo> produccionG = new ArrayList<co.edu.uniquindio.gri.model.ProduccionGrupo>();

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.MERGE)
	private List<co.edu.uniquindio.gri.model.ProduccionBGrupo> produccionBibliograficaG = new ArrayList<co.edu.uniquindio.gri.model.ProduccionBGrupo>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPOPRODUCCION_ID")
	private TipoProduccion tipoProduccion;
	
	public Tipo() {
	}

	public Tipo(long id, String nombre, TipoProduccion tipoProduccion) {
		this.id = id;
		this.nombre = nombre;
		this.tipoProduccion = tipoProduccion;
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

	public List<Produccion> getProduccion() {
		return produccion;
	}

	public void setProduccion(List<Produccion> produccion) {
		this.produccion = produccion;
	}

	public List<co.edu.uniquindio.gri.model.ProduccionGrupo> getProduccionG() {
		return produccionG;
	}

	public void setProduccionG(List<co.edu.uniquindio.gri.model.ProduccionGrupo> produccionG) {
		this.produccionG = produccionG;
	}

	public List<ProduccionB> getProduccionBibliografica() {
		return produccionBibliografica;
	}

	public void setProduccionBibliografica(List<ProduccionB> produccionBibliografica) {
		this.produccionBibliografica = produccionBibliografica;
	}

	public List<co.edu.uniquindio.gri.model.ProduccionBGrupo> getProduccionBibliograficaG() {
		return produccionBibliograficaG;
	}

	public void setProduccionBibliograficaG(
			List<co.edu.uniquindio.gri.model.ProduccionBGrupo> produccionBibliograficaG) {
		this.produccionBibliograficaG = produccionBibliograficaG;
	}

	public TipoProduccion getTipoProduccion() {
		return tipoProduccion;
	}

	public void setTipoProduccion(TipoProduccion tipoProduccion) {
		this.tipoProduccion = tipoProduccion;
	}

}
