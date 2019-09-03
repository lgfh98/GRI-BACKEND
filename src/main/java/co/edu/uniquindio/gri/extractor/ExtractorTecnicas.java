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
public class ExtractorTecnicas {

	@Autowired
	ArrayUtils utils;
	
	/**
	 * 
	 * @param elem
	 * @param tipo
	 * @param grupo
	 */
	public void extraerProdTecnicaG(ArrayList<String> elem, Tipo tipo, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		ArrayList<ProduccionGrupo> prodTecnicaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo produccionTecnica = new ProduccionGrupo();

			if (elem.get(i).contains(".-")) {
				int cont = i + 2;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")) {
					String actual = elem.get(cont);
					referencia += ", " + actual;

					if (actual.contains("AUTORES:")) {
						autores = actual.substring(9, actual.length() - 1);
					}
					cont++;
				}
				referencia = referencia.substring(4, referencia.length() - 1);
				anio = utils.extraerAnio(referencia);

				produccionTecnica.setAnio(anio);
				produccionTecnica.setAutores(autores);
				produccionTecnica.setReferencia(referencia);
				produccionTecnica.setTipo(tipo);
				produccionTecnica.setGrupo(grupo);
				produccionTecnica.setRepetido("NO");
				utils.identificarRepetidosG(prodTecnicaTemp, produccionTecnica);
				prodTecnicaTemp.add(produccionTecnica);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones((int) tipo.getId(),
				grupo.getProduccion(), prodTecnicaTemp);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerEmpresasTecnoG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_TECNICA, Constantes.TECNICA);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> prodTecnicaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo produccionTecnica = new ProduccionGrupo();
			if (elem.get(i).contains(".-")) {

				tipo = new Tipo(Constantes.ID_EMPRESA_TECNOLOGICA, Constantes.EMPRESA_TECNOLOGICA, tipoProduccion);

				int cont = i + 2;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")) {
					String actual = elem.get(cont);
					referencia += ", " + actual;

					if (actual.contains("AUTORES:")) {
						autores = actual.substring(9, actual.length() - 1);
					}
					cont++;
				}
				referencia = referencia.substring(4, referencia.length() - 1);
				anio = utils.extraerAnio(referencia);

				produccionTecnica.setAnio(anio);
				produccionTecnica.setAutores(autores);
				produccionTecnica.setReferencia(referencia);
				produccionTecnica.setTipo(tipo);
				produccionTecnica.setGrupo(grupo);
				produccionTecnica.setRepetido("NO");
				utils.identificarRepetidosG(prodTecnicaTemp, produccionTecnica);
				prodTecnicaTemp.add(produccionTecnica);

			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_EMPRESA_TECNOLOGICA,
				grupo.getProduccion(), prodTecnicaTemp);
		grupo.setProduccion(produccion);
	}
	
	/*
	 * Metodos que extraen informacion de los investigadores
	 */

	public void extraerProdTecnicaI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_TECNICA, Constantes.TECNICA);

		Tipo tipo = new Tipo();

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			if (elem.get(i).contains("PRODUCCIÓN TÉCNICA")) {

				Produccion produccion = new Produccion();

				if (elem.get(i).contains("SOFTWARES")) {

					tipo = new Tipo(Constantes.ID_SOFTWARE, Constantes.SOFTWARE, tipoProduccion);

				} else if (elem.get(i).contains("PROTOTIPO")) {

					tipo = new Tipo(Constantes.ID_PROTOTIPO, Constantes.PROTOTIPO, tipoProduccion);

				} else if (elem.get(i).contains("PRODUCTOS TECNOLÓGICOS")) {

					tipo = new Tipo(Constantes.ID_PRODUCTO_TECNOLOGICO, Constantes.PRODUCTO_TECNOLOGICO,
							tipoProduccion);

				} else if (elem.get(i).contains("INNOVACIÓN")) {

					tipo = new Tipo(Constantes.ID_INNOVACION_PROCESO, Constantes.INNOVACION_PROCESO, tipoProduccion);

				} else if (elem.get(i).contains("CONSULTORÍA CIENTÍFICO TECNOLÓGICA E INFORME TÉCNICO")) {

					tipo = new Tipo(Constantes.ID_TRABAJO_TECNICO, Constantes.TRABAJO_TECNICO, tipoProduccion);

				} else if (elem.get(i).contains("REGULACIÓN, NORMA")) {

					tipo = new Tipo(Constantes.ID_NORMA, Constantes.NORMA, tipoProduccion);

				} else if (elem.get(i).contains("INFORMES DE INV")) {

					tipo = new Tipo(Constantes.ID_INFORME_INVESTIGACION, Constantes.INFORME_INVESTIGACION,
							tipoProduccion);

				}
				// Autores
				String general = elem.get(i + 1).substring(0, elem.get(i + 1).length() - 1);
				autores = general.substring(0, general.lastIndexOf(",")).replaceAll(", ", ",");
				String autoresFinal = utils.verificarAutores(autores, investigador);

				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).startsWith("PRODUCCIÓN TÉCNICA")
						&& !elem.get(cont).startsWith("PALABRAS:") && !elem.get(cont).startsWith("AREAS:")
						&& !elem.get(cont).startsWith("SECTORES:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.substring(1);
				anio= utils.extraerAnio(referencia);
				
				if (!StringUtils.isNumeric(anio)) {
					anio = "N/D";
				}
				produccion.setReferencia(referencia);
				produccion.setAutores(autoresFinal);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> ProdTecnica = investigador.getProducciones();
		if (ProdTecnica == null) {
			investigador.setProducciones(ProdTecnica);
		} else {
			ProdTecnica.addAll(produccionAux);
			investigador.setProducciones(ProdTecnica);
		}
	}

	public void extraerEmpresasI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_TECNICA, Constantes.TECNICA);

		ArrayList<Produccion> produccionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("EMPRESA DE BASE TECNOLÓGICA")) {

				Produccion produccion = new Produccion();

				// Autores
				String general = elem.get(i + 1).substring(0, elem.get(i + 1).length() - 1);
				autores = general.substring(0, general.lastIndexOf(",")).replaceAll(", ", ",");
				String autoresFinal = utils.verificarAutores(autores, investigador);

				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("PRODUCCIÓN TÉCNICA")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("AREAS:")
						&& !elem.get(cont).contains("SECTORES:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					
					cont++;
				}
				referencia = referencia.substring(1);
				anio= utils.extraerAnio(referencia);
				
				if (!StringUtils.isNumeric(anio)) {
					anio = "N/D";
				}

				produccion.setReferencia(referencia);
				produccion.setAutores(autoresFinal);
				Tipo tipo = new Tipo(Constantes.ID_EMPRESA_TECNOLOGICA, Constantes.EMPRESA_TECNOLOGICA, tipoProduccion);
				produccion.setTipo(tipo);
				produccion.setAnio(anio);
				produccion.setInvestigador(investigador);
				produccion.setRepetido("NO");
				utils.identificarRepetidosI(produccionAux, produccion);
				produccionAux.add(produccion);
			}
		}
		List<Produccion> ProdTecnica = investigador.getProducciones();
		if (ProdTecnica == null) {
			investigador.setProducciones(ProdTecnica);
		} else {
			ProdTecnica.addAll(produccionAux);
			investigador.setProducciones(ProdTecnica);
		}
	}
}
