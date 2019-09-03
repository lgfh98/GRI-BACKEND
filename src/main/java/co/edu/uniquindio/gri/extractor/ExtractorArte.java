package co.edu.uniquindio.gri.extractor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.model.Produccion;
import co.edu.uniquindio.gri.model.ProduccionGrupo;
import co.edu.uniquindio.gri.model.Tipo;
import co.edu.uniquindio.gri.model.TipoProduccion;
import co.edu.uniquindio.gri.utils.ArrayUtils;
import co.edu.uniquindio.gri.utils.Constantes;

@Service
public class ExtractorArte {
	
	@Autowired
	ArrayUtils utils;
  
	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerObrasG(ArrayList<String> elem, Grupo grupo) {
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> auxProduccionTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo produccionArte = new ProduccionGrupo();

			if (elem.get(i).contains("NOMBRE DEL PRODUCTO:")) {

				tipo = new Tipo(Constantes.ID_OBRA, Constantes.OBRA, tipoProduccion);

				// Autores
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL PRODUCTO:")
						&& !elem.get(cont).contains("INSTANCIAS DE VALORACIÓN DE LA OBRA")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.trim();
				anio=utils.extraerAnio(referencia);
				
				produccionArte.setAnio(anio);
				produccionArte.setReferencia(referencia);
				produccionArte.setTipo(tipo);
				produccionArte.setGrupo(grupo);
				produccionArte.setRepetido("NO");
				utils.identificarRepetidosG(auxProduccionTemp, produccionArte);
				auxProduccionTemp.add(produccionArte);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_OBRA,
				grupo.getProduccion(), auxProduccionTemp);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerRegistrosAcuerdoG(ArrayList<String> elem, Grupo grupo) {
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> auxProduccionTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo produccionArte = new ProduccionGrupo();

			if (elem.get(i).contains("NOMBRE DEL PRODUCTO:")) {

				tipo = new Tipo(Constantes.ID_ACUERDO_LICENCIA, Constantes.ACUERDO_LICENCIA, tipoProduccion);

				// Autores
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL PRODUCTO:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.trim();
				anio=utils.extraerAnio(referencia);
				
				produccionArte.setAnio(anio);
				produccionArte.setReferencia(referencia);
				produccionArte.setTipo(tipo);
				produccionArte.setGrupo(grupo);
				produccionArte.setRepetido("NO");
				utils.identificarRepetidosG(auxProduccionTemp, produccionArte);
				auxProduccionTemp.add(produccionArte);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_ACUERDO_LICENCIA,
				grupo.getProduccion(), auxProduccionTemp);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerIndustriasG(ArrayList<String> elem, Grupo grupo) {
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> auxProduccionTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo produccionArte = new ProduccionGrupo();

			if (elem.get(i).contains("NOMBRE DE LA EMPRESA CREATIVA:")) {

				tipo = new Tipo(Constantes.ID_INDUSTRIA_CREATIVA, Constantes.INDUSTRIA_CREATIVA, tipoProduccion);

				// Autores
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DE LA EMPRESA CREATIVA:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.trim();
				anio=utils.extraerAnio(referencia);
				
				produccionArte.setAnio(anio);
				produccionArte.setReferencia(referencia);
				produccionArte.setTipo(tipo);
				produccionArte.setGrupo(grupo);
				produccionArte.setRepetido("NO");
				utils.identificarRepetidosG(auxProduccionTemp, produccionArte);
				auxProduccionTemp.add(produccionArte);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_INDUSTRIA_CREATIVA,
				grupo.getProduccion(), auxProduccionTemp);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerEventoArtisticoG(ArrayList<String> elem, Grupo grupo) {
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> auxProduccionTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo produccionArte = new ProduccionGrupo();

			if (elem.get(i).contains("NOMBRE DEL EVENTO:")) {

				tipo = new Tipo(Constantes.ID_EVENTO_ARTISTICO, Constantes.EVENTO_ARTISTICO, tipoProduccion);

				// Autores
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL EVENTO:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					cont++;
				}
				referencia = referencia.trim();
				anio=utils.extraerAnio(referencia);
				
				produccionArte.setAnio(anio);
				produccionArte.setReferencia(referencia);
				produccionArte.setTipo(tipo);
				produccionArte.setGrupo(grupo);
				produccionArte.setRepetido("NO");
				utils.identificarRepetidosG(auxProduccionTemp, produccionArte);
				auxProduccionTemp.add(produccionArte);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_EVENTO_ARTISTICO,
				grupo.getProduccion(), auxProduccionTemp);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerTallerCreativoG(ArrayList<String> elem, Grupo grupo) {
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> auxProduccionTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo produccionArte = new ProduccionGrupo();

			if (elem.get(i).contains("NOMBRE DEL TALLER:")) {

				tipo = new Tipo(Constantes.ID_TALLER_CREATIVO, Constantes.TALLER_CREATIVO, tipoProduccion);

				// Autores
				int cont = i;
				referencia = elem.get(i);
				cont++;
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL TALLER:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.trim();
				anio=utils.extraerAnio(referencia);
				
				produccionArte.setAnio(anio);
				produccionArte.setReferencia(referencia);
				produccionArte.setTipo(tipo);
				produccionArte.setGrupo(grupo);
				produccionArte.setRepetido("NO");
				utils.identificarRepetidosG(auxProduccionTemp, produccionArte);
				auxProduccionTemp.add(produccionArte);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_TALLER_CREATIVO,
				grupo.getProduccion(), auxProduccionTemp);
		grupo.setProduccion(produccion);
	}

	/*
	 * Metodos que extraen informacion de los investigadores
	 */
	
	public void extraerObrasI(ArrayList<String> elem, Investigador investigador) {

		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("NOMBRE DEL PRODUCTO:")) {
				Produccion produccion = new Produccion();

				// Autores
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL PRODUCTO:")
						&& !elem.get(cont).contains("INSTANCIAS DE VALORACIÓN DE LA OBRA")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					cont++;
				}
				referencia = referencia.trim();
				anio= utils.extraerAnio(referencia);
				produccion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_OBRA, Constantes.OBRA, tipoProduccion);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> prodArte = investigador.getProducciones();
		if (prodArte == null) {
			investigador.setProducciones(prodArte);
		} else {
			prodArte.addAll(produccionAux);
			investigador.setProducciones(prodArte);
		}
	}

	public void extraerRegistrosAcuerdoI(ArrayList<String> elem, Investigador investigador) {

		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("NOMBRE DEL PRODUCTO:")) {
				Produccion produccion = new Produccion();

				// Autores
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL PRODUCTO:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					cont++;
				}
				referencia = referencia.trim();
				anio= utils.extraerAnio(referencia);
				produccion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_ACUERDO_LICENCIA, Constantes.ACUERDO_LICENCIA, tipoProduccion);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> prodArte = investigador.getProducciones();
		if (prodArte == null) {
			investigador.setProducciones(prodArte);
		} else {
			prodArte.addAll(produccionAux);
			investigador.setProducciones(prodArte);
		}
	}

	public void extraerIndustriasI(ArrayList<String> elem, Investigador investigador) {

		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("NOMBRE DE LA EMPRESA CREATIVA:")) {
				Produccion produccion = new Produccion();

				// Autores
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DE LA EMPRESA CREATIVA:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.trim();
				anio= utils.extraerAnio(referencia);
				produccion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_INDUSTRIA_CREATIVA, Constantes.INDUSTRIA_CREATIVA, tipoProduccion);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> prodArte = investigador.getProducciones();
		if (prodArte == null) {
			investigador.setProducciones(prodArte);
		} else {
			prodArte.addAll(produccionAux);
			investigador.setProducciones(prodArte);
		}
	}

	public void extraerEventoArtisticoI(ArrayList<String> elem, Investigador investigador) {

		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("NOMBRE DEL EVENTO:")) {
				Produccion produccion = new Produccion();

				// Autores
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL EVENTO:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.trim();
				anio= utils.extraerAnio(referencia);
				produccion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_EVENTO_ARTISTICO, Constantes.EVENTO_ARTISTICO, tipoProduccion);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> prodArte = investigador.getProducciones();
		if (prodArte == null) {
			investigador.setProducciones(prodArte);
		} else {
			prodArte.addAll(produccionAux);
			investigador.setProducciones(prodArte);
		}
	}

	public void extraerTallerCreativoI(ArrayList<String> elem, Investigador investigador) {

		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_ARTE, Constantes.ARTE);

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("NOMBRE DEL TALLER:")) {
				Produccion produccion = new Produccion();

				// Autores
				int cont = i;
				referencia = elem.get(i);
				cont++;
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL TALLER:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.trim();
				anio= utils.extraerAnio(referencia);
				produccion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_TALLER_CREATIVO, Constantes.TALLER_CREATIVO, tipoProduccion);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> prodArte = investigador.getProducciones();
		if (prodArte == null) {
			investigador.setProducciones(prodArte);
		} else {
			prodArte.addAll(produccionAux);
			investigador.setProducciones(prodArte);
		}
	}
	
}
