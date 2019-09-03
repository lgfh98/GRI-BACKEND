package co.edu.uniquindio.gri.extractor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
public class ExtractorInfoAdicional {

	@Autowired
	ArrayUtils utils;
	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerDemasTrabajosG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_MASINFORMACION, Constantes.MASINFORMACION);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> masInformacionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo masInformacion = new ProduccionGrupo();

			if (elem.get(i).contains("DEMÁS TRABAJOS")) {

				tipo = new Tipo(Constantes.ID_DEMAS_TRABAJOS, Constantes.DEMAS_TRABAJOS, tipoProduccion);
			}
			if (elem.get(i).contains(".-")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")) {
					String actual = elem.get(cont);
					referencia += " " + actual;

					if (actual.contains("AUTORES:")) {
						autores = actual.substring(9, actual.length() - 1);
					}
					cont++;
				}
				referencia = referencia.trim();
				anio = utils.extraerAnio(referencia);

				masInformacion.setAnio(anio);
				masInformacion.setAutores(autores);
				masInformacion.setReferencia(referencia);
				masInformacion.setTipo(tipo);
				masInformacion.setGrupo(grupo);
				masInformacion.setRepetido("NO");
				utils.identificarRepetidosG(masInformacionAux, masInformacion);
				masInformacionAux.add(masInformacion);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_DEMAS_TRABAJOS,
				grupo.getProduccion(), masInformacionAux);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerProyectosG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_MASINFORMACION, Constantes.MASINFORMACION);

		ArrayList<ProduccionGrupo> masInformacionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo masInformacion = new ProduccionGrupo();

			if (elem.get(i).contains(".-")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")) {
					String actual = elem.get(cont);
					referencia += " " + actual;

					cont++;
				}
				referencia = referencia.trim();
				anio = utils.extraerAnio(referencia);

				masInformacion.setAnio(anio);
				masInformacion.setAutores(autores);
				masInformacion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_PROYECTO, Constantes.PROYECTO, tipoProduccion);
				masInformacion.setTipo(tipo);
				masInformacion.setGrupo(grupo);
				masInformacion.setRepetido("NO");
				utils.identificarRepetidosG(masInformacionAux, masInformacion);
				masInformacionAux.add(masInformacion);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_PROYECTO,
				grupo.getProduccion(), masInformacionAux);
		grupo.setProduccion(produccion);
	}

	/*
	 * Metodos que extraen informacion de los investigadores
	 */
	
	public void extraerDemasTrabajosI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_MASINFORMACION, Constantes.MASINFORMACION);

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("DEMÁS TRABAJOS -")) {
				Produccion produccion = new Produccion();

				// Autores
				String general = elem.get(i + 1).substring(0, elem.get(i + 1).length() - 1);
				autores = general.substring(0, general.lastIndexOf(",")).replaceAll(", ", ",");
				String autoresFinal = utils.verificarAutores(autores, investigador);

				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("DEMÁS TRABAJOS -")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("AREAS:")
						&& !elem.get(cont).contains("SECTORES:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.trim();
				anio= utils.extraerAnio(referencia);

				if (!StringUtils.isNumeric(anio)) {
					anio = "N/D";
				}
				produccion.setAutores(autoresFinal);
				produccion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_DEMAS_TRABAJOS, Constantes.DEMAS_TRABAJOS, tipoProduccion);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> infoAdicional = investigador.getProducciones();
		if (infoAdicional == null) {
			investigador.setProducciones(infoAdicional);
		} else {
			infoAdicional.addAll(produccionAux);
			investigador.setProducciones(infoAdicional);
		}
	}

	public void extraerProyectosI(ArrayList<String> elem, Investigador investigador) {

		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_MASINFORMACION, Constantes.MASINFORMACION);

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("TIPO DE PROYECTO:")) {
				Produccion produccion = new Produccion();

				referencia = elem.get(i);
				int cont = i + 1;
				while (cont < elem.size() && !elem.get(cont).contains("TIPO DE PROYECTO:")
						&& !elem.get(cont).contains("RESUMEN")) {
					String actual = elem.get(cont);
					referencia += " " + actual;

					cont++;
				}
				referencia = referencia.trim();
				anio= utils.extraerAnio(referencia);

				if (!StringUtils.isNumeric(anio)) {
					anio = "N/D";
				}

				produccion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_PROYECTO, Constantes.PROYECTO, tipoProduccion);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> infoAdicional = investigador.getProducciones();
		if (infoAdicional == null) {
			investigador.setProducciones(infoAdicional);
		} else {
			infoAdicional.addAll(produccionAux);
			investigador.setProducciones(infoAdicional);
		}
	}

}
