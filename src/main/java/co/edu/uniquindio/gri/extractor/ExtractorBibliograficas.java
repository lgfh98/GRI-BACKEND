package co.edu.uniquindio.gri.extractor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.model.ProduccionB;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.Tipo;
import co.edu.uniquindio.gri.model.TipoProduccion;
import co.edu.uniquindio.gri.utils.ArrayUtils;
import co.edu.uniquindio.gri.utils.Constantes;
import co.edu.uniquindio.gri.utils.FuzzyMatch;

@Service
public class ExtractorBibliograficas {

	@Autowired
	ArrayUtils utils;

	/***
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerArticulosG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";
		String issn = "";
		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);
		Tipo tipo = new Tipo();
		ArrayList<ProduccionBGrupo> prodBibliograficaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionBGrupo produccionBibliografica = new ProduccionBGrupo();
			if (elem.get(i).contains(".-")) {
				tipo = new Tipo(Constantes.ID_ARTICULO, Constantes.ARTICULO, tipoProduccion);
				referencia = "";
				for (int j = i + 2; j < elem.size(); j++) {
					if (!elem.get(j).contains("AUTORES:")) {
						referencia += " " + elem.get(j);
					} else {
						break;
					}
				}
				if (referencia.endsWith("DOI:")) {
					referencia = referencia.substring(1, referencia.length() - 6);
				}
				if (referencia.startsWith(" : ")) {
					referencia = referencia.substring(3);
				}
			}
			if (elem.get(i).contains(" ISSN:")) {
				String aux = elem.get(i).trim();
				aux = aux.substring(aux.indexOf(" ISSN:"), aux.indexOf("VOL:"));
				issn = aux.substring(aux.indexOf(": ") + 2, aux.indexOf(","));
				anio = utils.extraerAnio(aux);
			}

			if (elem.get(i).contains("AUTORES:")) {
				autores = elem.get(i).substring(9, elem.get(i).length() - 1);
				referencia = referencia.trim();
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(issn);
				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setGrupo(grupo);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosG(prodBibliograficaTemp, produccionBibliografica);
				prodBibliograficaTemp.add(produccionBibliografica);
			}
		}

		List<ProduccionBGrupo> prodBibliografica = verificarProducciones(Constantes.ID_ARTICULO,
				grupo.getProduccionBibliografica(), prodBibliograficaTemp);
		grupo.setProduccionBibliografica(prodBibliografica);

	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerLibrosG(ArrayList<String> elem, Grupo grupo) {

		String autores = "";
		String referencia = "";
		String anio = "";
		String isbn = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionBGrupo> prodBibliograficaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionBGrupo produccionBibliografica = new ProduccionBGrupo();

			if (elem.get(i).contains(".-")) {
				if (elem.get(i + 1).contains("LIBRO RESULTADO DE INVESTIGACIÓN")) {

					tipo = new Tipo(Constantes.ID_LIBRO, Constantes.LIBRO, tipoProduccion);

				}

				referencia = "";
				for (int j = i + 2; j < elem.size(); j++) {
					if (!elem.get(j).contains("AUTORES:")) {
						referencia += " " + elem.get(j);
					} else {
						break;
					}
				}

				if (referencia.startsWith(" : ")) {
					referencia = referencia.substring(3);
				}

			}

			if (elem.get(i).contains("ISBN")) {
				char[] aux = elem.get(i).toCharArray();
				for (int j = 0; j < aux.length; j++) {
					if (aux[j] == 'I' && aux[j + 1] == 'S' && aux[j + 2] == 'B') {
						int posI = j + 6;
						int posF = posI;
						for (int k = posI; k < aux.length; k++) {
							try {
								if (aux[k + 1] == 'V' && aux[k + 2] == 'O' && aux[k + 3] == 'L' && aux[k + 4] == ':') {
									posF = k;
									break;
								}
							} catch (Exception e) {
								posF = posI;
							}

						}
						isbn = elem.get(i).substring(posI, posF);
						int posAux = elem.get(i).indexOf(',') + 1;
						anio = elem.get(i).substring(posAux, posAux + 4);
						break;
					}
				}

			}

			if (elem.get(i).contains("AUTORES:")) {
				autores = elem.get(i).substring(9, elem.get(i).length() - 1);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(isbn);
				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setGrupo(grupo);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosG(prodBibliograficaTemp, produccionBibliografica);
				prodBibliograficaTemp.add(produccionBibliografica);
			}
		}

		List<ProduccionBGrupo> prodBibliografica = verificarProducciones(Constantes.ID_LIBRO,
				grupo.getProduccionBibliografica(), prodBibliograficaTemp);
		grupo.setProduccionBibliografica(prodBibliografica);

	}

	/**
	 * Metodo que extrae los capitulos de libro que publico el grupo de
	 * investigacion
	 * 
	 * @param elem,
	 *            Lista de elementos que contiene los capitulos de libro
	 * @return Lista de Producciones bibliograficas
	 */
	public void extraerCapLibrosG(ArrayList<String> elem, Grupo grupo) {

		String autores = "";
		String referencia = "";
		String anio = "";
		String isbn = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionBGrupo> prodBibliograficaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionBGrupo produccionBibliografica = new ProduccionBGrupo();

			if (elem.get(i).contains(".-")) {

				tipo = new Tipo(Constantes.ID_CAPITULO_LIBRO, Constantes.CAPITULO_LIBRO, tipoProduccion);

				referencia = "";
				for (int j = i + 2; j < elem.size(); j++) {
					if (!elem.get(j).contains("AUTORES:")) {
						referencia += " " + elem.get(j);
					} else {
						break;
					}
				}

				if (referencia.startsWith(" : ")) {
					referencia = referencia.substring(3);
				}

			}

			if (elem.get(i).contains("ISBN")) {
				char[] aux = elem.get(i).toCharArray();
				for (int j = 0; j < aux.length; j++) {
					if (aux[j] == 'I' && aux[j + 1] == 'S' && aux[j + 2] == 'B') {
						int posI = j + 6;
						int posF = posI;
						for (int k = posI; k < aux.length; k++) {
							try {
								if (aux[k + 2] == 'V' && aux[k + 3] == 'O' && aux[k + 4] == 'L' && aux[k + 5] == '.') {
									posF = k;
									break;
								}
							} catch (Exception e) {
								posF = posI;
							}

						}
						isbn = elem.get(i).substring(posI, posF);
						int posAux = elem.get(i).indexOf(',') + 2;
						anio = elem.get(i).substring(posAux, posAux + 4);
						break;
					}
				}

			}

			if (elem.get(i).contains("AUTORES:")) {
				autores = elem.get(i).substring(9, elem.get(i).length() - 1);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(isbn);
				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setGrupo(grupo);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosG(prodBibliograficaTemp, produccionBibliografica);
				prodBibliograficaTemp.add(produccionBibliografica);
			}
		}

		List<ProduccionBGrupo> prodBibliografica = verificarProducciones(Constantes.ID_CAPITULO_LIBRO,
				grupo.getProduccionBibliografica(), prodBibliograficaTemp);
		grupo.setProduccionBibliografica(prodBibliografica);

	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerDocumentosTrabajoG(ArrayList<String> elem, Grupo grupo) {

		String autores = "";
		String referencia = "";
		String anio = "";
		String id = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionBGrupo> prodBibliograficaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionBGrupo produccionBibliografica = new ProduccionBGrupo();

			if (elem.get(i).contains(".-")) {
				if (elem.get(i + 1).contains("DOCUMENTO DE TRABAJO")) {
					id = "DOCUMENTO DE TRABAJO";

					tipo = new Tipo(Constantes.ID_DOCUMENTO_TRABAJO, Constantes.DOCUMENTO_TRABAJO, tipoProduccion);

				}

				referencia = "";
				for (int j = i + 2; j < elem.size(); j++) {
					if (!elem.get(j).contains("AUTORES:")) {
						referencia += " " + elem.get(j);
					} else {
						break;
					}
				}

				if (referencia.endsWith("DOI:")) {
					referencia = referencia.substring(1, referencia.length() - 6);
				}

				if (referencia.startsWith(": ")) {
					referencia = referencia.substring(2);
				}

			}

			anio = utils.extraerAnio(referencia);

			if (elem.get(i).contains("AUTORES:")) {
				autores = elem.get(i).substring(9, elem.get(i).length() - 1);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(id);
				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setGrupo(grupo);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosG(prodBibliograficaTemp, produccionBibliografica);
				prodBibliograficaTemp.add(produccionBibliografica);
			}
		}

		List<ProduccionBGrupo> prodBibliografica = verificarProducciones(Constantes.ID_DOCUMENTO_TRABAJO,
				grupo.getProduccionBibliografica(), prodBibliograficaTemp);
		grupo.setProduccionBibliografica(prodBibliografica);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerOtraProdBibliograficaG(ArrayList<String> elem, Grupo grupo) {

		String autores = "";
		String referencia = "";
		String anio = "";
		String id = "OTRA PUBLICACIÓN DIVULGATIVA";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		ArrayList<ProduccionBGrupo> prodBibliograficaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionBGrupo produccionBibliografica = new ProduccionBGrupo();

			if (elem.get(i).contains(".-")) {

				referencia = "";
				for (int j = i + 2; j < elem.size(); j++) {
					if (!elem.get(j).contains("AUTORES:")) {
						referencia += " " + elem.get(j);
					} else {
						break;
					}
				}

				if (referencia.startsWith(" : ")) {
					referencia = referencia.substring(3);
				}

			}

			anio = utils.extraerAnio(referencia);

			if (elem.get(i).contains("AUTORES:")) {
				autores = elem.get(i).substring(9, elem.get(i).length() - 1);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(id);
				produccionBibliografica.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_OTRA_PUBLICACION_DIVULGATIVA,
						Constantes.OTRA_PUBLICACION_DIVULGATIVA, tipoProduccion);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setGrupo(grupo);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosG(prodBibliograficaTemp, produccionBibliografica);
				prodBibliograficaTemp.add(produccionBibliografica);
			}
		}

		List<ProduccionBGrupo> prodBibliografica = verificarProducciones(Constantes.ID_OTRA_PUBLICACION_DIVULGATIVA,
				grupo.getProduccionBibliografica(), prodBibliograficaTemp);
		grupo.setProduccionBibliografica(prodBibliografica);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerOtroArticuloG(ArrayList<String> elem, Grupo grupo) {

		String autores = "";
		String referencia = "";
		String anio = "";
		String issn = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		ArrayList<ProduccionBGrupo> prodBibliograficaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionBGrupo produccionBibliografica = new ProduccionBGrupo();

			if (elem.get(i).contains(".-")) {

				referencia = "";
				for (int j = i + 2; j < elem.size(); j++) {
					if (!elem.get(j).contains("AUTORES:")) {
						referencia += " " + elem.get(j);
					} else {
						break;
					}
				}

				if (referencia.startsWith(" : ")) {
					referencia = referencia.substring(3);
				}

			}

			if (elem.get(i).contains("ISSN:")) {

				String aux = elem.get(i).substring(elem.get(i).indexOf("ISSN: "));
				int posAux = aux.indexOf(':') + 2;
				issn = aux.substring(posAux, aux.indexOf(","));
				anio = aux.substring(aux.indexOf(",") + 2, aux.indexOf(",") + 6);

			}

			if (elem.get(i).contains("AUTORES:")) {
				autores = elem.get(i).substring(9, elem.get(i).length() - 1);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(issn);
				produccionBibliografica.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_OTRO_ARTICULO, Constantes.OTRO_ARTICULO, tipoProduccion);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setGrupo(grupo);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosG(prodBibliograficaTemp, produccionBibliografica);
				prodBibliograficaTemp.add(produccionBibliografica);
			}
		}

		List<ProduccionBGrupo> prodBibliografica = verificarProducciones(Constantes.ID_OTRO_ARTICULO,
				grupo.getProduccionBibliografica(), prodBibliograficaTemp);
		grupo.setProduccionBibliografica(prodBibliografica);

	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerOtroLibroG(ArrayList<String> elem, Grupo grupo) {

		String autores = "";
		String referencia = "";
		String anio = "";
		String isbn = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		ArrayList<ProduccionBGrupo> prodBibliograficaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionBGrupo produccionBibliografica = new ProduccionBGrupo();

			if (elem.get(i).contains(".-")) {

				referencia = "";
				for (int j = i + 2; j < elem.size(); j++) {
					if (!elem.get(j).contains("AUTORES:")) {
						referencia += " " + elem.get(j);
					} else {
						break;
					}
				}

				if (referencia.startsWith(" : ")) {
					referencia = referencia.substring(3);
				}

			}

			if (elem.get(i).contains("ISBN:")) {

				int posAux = elem.get(i).indexOf(',');
				anio = elem.get(i).substring(posAux + 1, posAux + 5);
				try {
					isbn = elem.get(i).substring(elem.get(i).indexOf("ISBN:") + 6, elem.get(i).indexOf("VOL:") - 1);

				} catch (Exception e) {
					isbn = "";
				}

			}

			if (elem.get(i).contains("AUTORES:")) {
				autores = elem.get(i).substring(9, elem.get(i).length() - 1);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(isbn);
				produccionBibliografica.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_OTRO_LIBRO, Constantes.OTRO_LIBRO, tipoProduccion);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setGrupo(grupo);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosG(prodBibliograficaTemp, produccionBibliografica);
				prodBibliograficaTemp.add(produccionBibliografica);
			}
		}

		List<ProduccionBGrupo> prodBibliografica = verificarProducciones(Constantes.ID_OTRO_LIBRO,
				grupo.getProduccionBibliografica(), prodBibliograficaTemp);
		grupo.setProduccionBibliografica(prodBibliografica);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerTraduccionesG(ArrayList<String> elem, Grupo grupo) {

		String autores = "";
		String referencia = "";
		String anio = "";
		String id = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		ArrayList<ProduccionBGrupo> prodBibliograficaTemp = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionBGrupo produccionBibliografica = new ProduccionBGrupo();

			if (elem.get(i).contains(".-")) {

				referencia = "";
				for (int j = i + 1; j < elem.size(); j++) {
					if (!elem.get(j).contains("AUTORES:")) {
						referencia += " " + elem.get(j);
					} else {
						break;
					}
				}

				if (referencia.startsWith(" : ")) {
					referencia = referencia.substring(3);
				}

			}

			if (elem.get(i).contains("LIBRO: ISBN")) {
				String aux = elem.get(i).substring(elem.get(i).indexOf("ISSN"));
				int posAux = aux.indexOf(',');
				id = aux.substring(4, posAux);
			}

			if (elem.get(i).contains("REVISTA: ISSN")) {
				String aux = elem.get(i).substring(elem.get(i).indexOf("ISBN "));
				int posAux = aux.indexOf(',');
				id = aux.substring(4, posAux);
			}

			anio = utils.extraerAnio(referencia);

			if (elem.get(i).contains("AUTORES:")) {
				autores = elem.get(i).substring(9, elem.get(i).length() - 1);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(id);
				produccionBibliografica.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_TRADUCCION, Constantes.TRADUCCION, tipoProduccion);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setGrupo(grupo);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosG(prodBibliograficaTemp, produccionBibliografica);
				prodBibliograficaTemp.add(produccionBibliografica);
			}
		}

		List<ProduccionBGrupo> prodBibliografica = verificarProducciones(Constantes.ID_TRADUCCION,
				grupo.getProduccionBibliografica(), prodBibliograficaTemp);
		grupo.setProduccionBibliografica(prodBibliografica);

	}

	/*
	 * Metodos que extraen informacion de los investigadores
	 */
	
	public void extraerArticulosI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String referencia = "";
		String anio = "";
		String issn = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionB> prodBibliograficaAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("PRODUCCIÓN BIBLIOGRÁFICA")) {

				ProduccionB produccionBibliografica = new ProduccionB();

				if (elem.get(i).contains("PUBLICADO EN REVISTA ESPECIALIZADA")) {

					tipo = new Tipo(Constantes.ID_ARTICULO, Constantes.ARTICULO, tipoProduccion);

				} else {

					tipo = new Tipo(Constantes.ID_OTRO_ARTICULO, Constantes.OTRO_ARTICULO, tipoProduccion);

				}
				// Autores
				String general = elem.get(i + 1);
				int inicio = general.indexOf("\"");
				try {
					autores = general.substring(0, inicio - 2);
				} catch (Exception e) {
					autores = "N/D";
				}

				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("PRODUCCIÓN BIBLIOGRÁFICA")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					if (actual.contains("ISSN:")) {
						if (elem.get(cont + 1).contains("ED:")) {
							issn = "N/D";
						} else {
							issn = elem.get(cont + 1);
						}
					}
					cont++;
				}
				referencia = referencia.trim();
				if (referencia.endsWith("DOI:")) {
					referencia = referencia.substring(0, referencia.length() - 6);
				}

				anio = utils.extraerAnio(referencia);
				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(issn);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setInvestigador(investigador);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosI(prodBibliograficaAux, produccionBibliografica);
				prodBibliograficaAux.add(produccionBibliografica);
			}
		}

		List<ProduccionB> Bibliografica = investigador.getProduccionesBibliograficas();
		if (Bibliografica == null) {
			investigador.setProduccionesBibliograficas(Bibliografica);
		} else {
			Bibliografica.addAll(prodBibliograficaAux);
			investigador.setProduccionesBibliograficas(Bibliografica);
		}
	}

	public void extraerLibrosI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String referencia = "";
		String anio = "";
		String isbn = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionB> prodBibliograficaAux = new ArrayList<>();
		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("PRODUCCIÓN BIBLIOGRÁFICA")) {
				ProduccionB produccionBibliografica = new ProduccionB();
				if (elem.get(i).contains("LIBRO RESULTADO DE INVESTIGACIÓN")) {

					tipo = new Tipo(Constantes.ID_LIBRO, Constantes.LIBRO, tipoProduccion);

				} else {

					tipo = new Tipo(Constantes.ID_OTRO_LIBRO, Constantes.OTRO_LIBRO, tipoProduccion);

				}
				// Autores
				String general = elem.get(i + 1);
				int inicio = general.indexOf("\"");
				autores = general.substring(0, inicio - 2);
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("PRODUCCIÓN BIBLIOGRÁFICA")
						&& !elem.get(cont).contains("PALABRA:S") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					if (actual.contains("ISBN:")) {
						if (elem.get(cont + 1).contains("V.")) {
							isbn = "N/D";
						} else {
							isbn = elem.get(cont + 1);
						}
					}
					cont++;
				}
				referencia = referencia.trim();

				anio = utils.extraerAnio(referencia);

				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(isbn);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setInvestigador(investigador);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosI(prodBibliograficaAux, produccionBibliografica);
				prodBibliograficaAux.add(produccionBibliografica);
			}
		}

		List<ProduccionB> Bibliografica = investigador.getProduccionesBibliograficas();
		if (Bibliografica == null) {
			investigador.setProduccionesBibliograficas(Bibliografica);
		} else {
			Bibliografica.addAll(prodBibliograficaAux);
			investigador.setProduccionesBibliograficas(Bibliografica);
		}
	}

	public void extraerCapLibrosI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String referencia = "";
		String anio = "";
		String isbn = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionB> prodBibliograficaAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).startsWith("TIPO:")) {
				ProduccionB produccionBibliografica = new ProduccionB();
				if (!elem.get(i).contains("OTRO CAPÍTULO")) {

					tipo = new Tipo(Constantes.ID_CAPITULO_LIBRO, Constantes.CAPITULO_LIBRO, tipoProduccion);

				} else {

					tipo = new Tipo(Constantes.ID_OTRO_CAPITULO_LIBRO, Constantes.OTRO_CAPITULO_LIBRO, tipoProduccion);

				}
				// Autores
				autores = "";
				int ref = i + 1;
				String general = "";
				do {
					general = elem.get(ref);
					int fin = general.indexOf(",");
					autores = autores + " " + general.substring(0, fin + 1);
					ref++;
				} while (!general.contains("\""));

				int cont = ref - 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).startsWith("TIPO:")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					if (actual.contains("\"")) {
						int pos = actual.indexOf(",");
						actual = actual.substring(pos);
					}
					referencia = referencia + " " + actual;
					if (actual.contains("ISBN:")) {
						if (elem.get(cont + 1).contains("ED:")) {
							isbn = "N/D";
						} else {
							isbn = elem.get(cont + 1);
						}
					}
					cont++;
				}
				autores = autores.substring(1, autores.length() - 1);
				referencia = autores + referencia;

				anio = utils.extraerAnio(referencia);

				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(isbn);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setInvestigador(investigador);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosI(prodBibliograficaAux, produccionBibliografica);
				prodBibliograficaAux.add(produccionBibliografica);
			}
		}

		List<ProduccionB> Bibliografica = investigador.getProduccionesBibliograficas();
		if (Bibliografica == null) {
			investigador.setProduccionesBibliograficas(Bibliografica);
		} else {
			Bibliografica.addAll(prodBibliograficaAux);
			investigador.setProduccionesBibliograficas(Bibliografica);
		}
	}

	public void extraerPubNoCientificasI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String referencia = "";
		String anio = "";
		String issn = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		ArrayList<ProduccionB> prodBibliograficaAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("PRODUCCIÓN BIBLIOGRÁFICA")) {
				ProduccionB produccionBibliografica = new ProduccionB();

				Tipo tipo = new Tipo(Constantes.ID_ARTICULO_NO_CIENTIFICO, Constantes.ARTICULO_NO_CIENTIFICO,
						tipoProduccion);

				// Autores
				String general = elem.get(i + 1);
				int inicio = general.indexOf("\"");
				autores = general.substring(0, inicio - 2);
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("PRODUCCIÓN BIBLIOGRÁFICA")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;
					if (actual.contains("ISSN:")) {
						if (elem.get(cont + 1).contains("P.")) {
							issn = "N/D";
						} else {
							issn = elem.get(cont + 1);
						}
					}
					cont++;
				}
				referencia = referencia.trim();

				anio = utils.extraerAnio(referencia);

				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(issn);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setInvestigador(investigador);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosI(prodBibliograficaAux, produccionBibliografica);
				prodBibliograficaAux.add(produccionBibliografica);
			}
		}

		List<ProduccionB> Bibliografica = investigador.getProduccionesBibliograficas();
		if (Bibliografica == null) {
			investigador.setProduccionesBibliograficas(Bibliografica);
		} else {
			Bibliografica.addAll(prodBibliograficaAux);
			investigador.setProduccionesBibliograficas(Bibliografica);
		}
	}

	public void extraerOtraProdBibliograficaI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String referencia = "";
		String anio = "";
		String id = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_BIBLIOGRAFICA, Constantes.BIBLIOGRAFICA);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionB> prodBibliograficaAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("PRODUCCIÓN BIBLIOGRÁFICA")) {
				ProduccionB produccionBibliografica = new ProduccionB();
				if (elem.get(i).contains("DOCUMENTO DE TRABAJO")) {
					id = "DOCUMENTO DE TRABAJO";

					tipo = new Tipo(Constantes.ID_DOCUMENTO_TRABAJO, Constantes.DOCUMENTO_TRABAJO, tipoProduccion);

				} else {
					id = "OTRA PROD. BIBLIOGRAFICA";

					tipo = new Tipo(Constantes.ID_OTRA_PRODUCCION_BIBLIOGRAFICA,
							Constantes.OTRA_PRODUCCION_BIBLIOGRAFICA, tipoProduccion);

				}
				// Autores
				String general = elem.get(i + 1);
				int inicio = general.indexOf("\"");
				autores = general.substring(0, inicio - 2);
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("PRODUCCIÓN BIBLIOGRÁFICA")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia = referencia + " " + actual;

					cont++;
				}
				referencia = referencia.trim();

				anio = utils.extraerAnio(referencia);

				produccionBibliografica.setReferencia(referencia);
				produccionBibliografica.setAutores(autores);
				produccionBibliografica.setIdentificador(id);
				produccionBibliografica.setTipo(tipo);
				produccionBibliografica.setAnio(anio);
				produccionBibliografica.setInvestigador(investigador);
				produccionBibliografica.setRepetido("NO");
				utils.identificarRepetidosBibliograficosI(prodBibliograficaAux, produccionBibliografica);
				prodBibliograficaAux.add(produccionBibliografica);
			}
		}

		List<ProduccionB> Bibliografica = investigador.getProduccionesBibliograficas();
		if (Bibliografica == null) {
			investigador.setProduccionesBibliograficas(Bibliografica);
		} else {
			Bibliografica.addAll(prodBibliograficaAux);
			investigador.setProduccionesBibliograficas(Bibliografica);
		}
	}

	public List<ProduccionBGrupo> verificarProducciones(int idTipo, List<ProduccionBGrupo> prodBibliografica,
			List<ProduccionBGrupo> prodBibliograficaTemp) {
		if (prodBibliografica.isEmpty()) {
			return prodBibliograficaTemp;
		} else {
			List<ProduccionBGrupo> prodBibliograficaTipo = new ArrayList<>();
			for (ProduccionBGrupo produccion : prodBibliografica) {
				if (produccion.getTipo().getId() == idTipo) {
					prodBibliograficaTipo.add(produccion);
				}
			}

			if (prodBibliograficaTemp.size() < prodBibliograficaTipo.size()) {
				prodBibliografica = eliminarProducciones(prodBibliograficaTemp, prodBibliograficaTipo,
						prodBibliografica);
			}

			List<ProduccionBGrupo> prodBibliograficaValidado = validarProducciones(prodBibliograficaTemp,
					prodBibliograficaTipo, prodBibliografica);
			return prodBibliograficaValidado;
		}
	}

	@Autowired
	FuzzyMatch fuzzyMatch;
	
	public List<ProduccionBGrupo> validarProducciones(List<ProduccionBGrupo> prodBibliograficaTemp,
			List<ProduccionBGrupo> prodBibliograficaTipo, List<ProduccionBGrupo> prodBibliografica) {
		
		for (ProduccionBGrupo produccionTemp : prodBibliograficaTemp) {
			boolean existe = false;
			for (ProduccionBGrupo produccionTipo : prodBibliograficaTipo) {
				if (produccionTemp.getReferencia().equals(produccionTipo.getReferencia())) {
					existe = true;
					break;
				} else if (fuzzyMatch.getRatio(produccionTemp.getReferencia(),
						produccionTipo.getReferencia()) >= Constantes.FUZZY_MATCH) {
					int indice = prodBibliografica.indexOf(produccionTipo);
					prodBibliografica.get(indice).setReferencia(produccionTemp.getReferencia());
					existe = true;
					break;
				}
			}
			if (!existe) {
				prodBibliografica.add(produccionTemp);
			}
		}

		return prodBibliografica;
	}

	
	public List<ProduccionBGrupo> eliminarProducciones(List<ProduccionBGrupo> prodBibliograficaTemp,
			List<ProduccionBGrupo> prodBibliograficaTipo, List<ProduccionBGrupo> prodBibliografica) {
		FuzzyMatch fuzzyMatch = new FuzzyMatch();

		for (ProduccionBGrupo produccionTipo : prodBibliograficaTipo) {
			boolean existe = false;
			for (ProduccionBGrupo produccionTemp : prodBibliograficaTemp) {
				if (fuzzyMatch.getRatio(produccionTipo.getReferencia(),
						produccionTemp.getReferencia()) >= Constantes.FUZZY_MATCH) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				int indice = prodBibliografica.indexOf(produccionTipo);
				prodBibliografica.get(indice).setDebeEliminarse(true);
			}
		}

		return prodBibliografica;
	}

}
