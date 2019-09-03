package co.edu.uniquindio.gri.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class ExtractorEvaluacion {

	@Autowired
	ArrayUtils utils;
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerJuradoG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_EVALUADOR, Constantes.EVALUADOR);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> actEvaluadorAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo actividadesEvaluador = new ProduccionGrupo();

			if (elem.get(i).contains("JURADO/COMISIONES EVALUADORAS DE TRABAJO DE GRADO")) {

				tipo = new Tipo(Constantes.ID_JURADO_COMITE_EVALUADOR, Constantes.JURADO_COMITE_EVALUADOR,
						tipoProduccion);

			}
			
			
			Pattern p = Pattern.compile("\\d.-");
			Matcher m = p.matcher(elem.get(i));
			
			if (m.find()) {
				int cont = i + 1;
				referencia = "";
				
				while (cont < elem.size()) {
				
				Pattern p2 = Pattern.compile("\\d.-");
				Matcher m2 = p2.matcher(elem.get(cont));
				
				if (!m2.find()) {
						String actual = elem.get(cont);
						referencia += " " + actual;

						if (actual.contains("AUTORES:")&& actual.length()>8) {
							autores = actual.substring(9, actual.length() - 1);
						}else {
							autores = "NO ESPECIFICADO";
						}
					}else {
						break;
					}
				cont++;
				}
				referencia = referencia.trim();
				anio=utils.extraerAnio(referencia);
				
				actividadesEvaluador.setAnio(anio);
				actividadesEvaluador.setAutores(autores);
				actividadesEvaluador.setReferencia(referencia);
				actividadesEvaluador.setTipo(tipo);
				actividadesEvaluador.setGrupo(grupo);
				actividadesEvaluador.setRepetido("NO");
				utils.identificarRepetidosG(actEvaluadorAux, actividadesEvaluador);
				actEvaluadorAux.add(actividadesEvaluador);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_JURADO_COMITE_EVALUADOR,
				grupo.getProduccion(), actEvaluadorAux);
		grupo.setProduccion(produccion);

	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerPartipacionComitesG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_EVALUADOR, Constantes.EVALUADOR);

		ArrayList<ProduccionGrupo> actEvaluadorAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo actividadesEvaluador = new ProduccionGrupo();

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
				anio=utils.extraerAnio(referencia);
				
				actividadesEvaluador.setAnio(anio);
				actividadesEvaluador.setAutores(autores);
				actividadesEvaluador.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_PARTICIPACION_COMITE_EVALUADOR,
						Constantes.PARTICIPACION_COMITE_EVALUADOR, tipoProduccion);
				actividadesEvaluador.setTipo(tipo);
				actividadesEvaluador.setGrupo(grupo);
				actividadesEvaluador.setRepetido("NO");
				utils.identificarRepetidosG(actEvaluadorAux, actividadesEvaluador);
				actEvaluadorAux.add(actividadesEvaluador);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_PARTICIPACION_COMITE_EVALUADOR,
				grupo.getProduccion(), actEvaluadorAux);
		grupo.setProduccion(produccion);
	}
	
	/*
	 * Metodos que extraen informacion de los investigadores
	 */
	
	public void extraerJuradoI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_EVALUADOR, Constantes.EVALUADOR);

		ArrayList<Produccion> actEvaluadorAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			try {

				if (elem.get(i).startsWith("JURADO EN COMITÉS DE EVALUACIÓN")) {

					for (int j = i; j < elem.size(); j++) {

						Produccion actividadesEvaluador = new Produccion();

						if (elem.get(j).startsWith("PARTICIPACIÓN EN COMITÉS DE EVALUACIÓN")) {
							break;
						} else if (elem.get(j).startsWith("DATOS COMPLEMENTARIOS")) {
							j++;
							autores = elem.get(j).substring(0, elem.get(j).length() - 1);
						} else if (elem.get(j).startsWith("TITULO:")) {
							referencia = "";
							for (int k = j; k < elem.size(); k++) {
								if (elem.get(k).startsWith("DATOS COMPLEMENTARIOS")
										|| elem.get(k).startsWith("AREAS:")) {
									break;
								}
								if (elem.get(k).endsWith(":")) {
									referencia += elem.get(k) + " ";
								} else {
									referencia += elem.get(k) + ", ";
								}
								j = k;
							}

							if (referencia.endsWith(",, ")) {
								referencia = referencia.substring(0, referencia.length() - 3);
							} else {
								referencia = referencia.substring(0, referencia.length() - 2);
							}

							anio= utils.extraerAnio(referencia);

							actividadesEvaluador.setAutores(autores);
							actividadesEvaluador.setReferencia(referencia);
							actividadesEvaluador.setAnio(anio);
							Tipo tipo = new Tipo(Constantes.ID_JURADO_COMITE_EVALUADOR,
									Constantes.JURADO_COMITE_EVALUADOR, tipoProduccion);
							actividadesEvaluador.setTipo(tipo);
							actividadesEvaluador.setInvestigador(investigador);
							actividadesEvaluador.setRepetido("NO");
							utils.identificarRepetidosI(actEvaluadorAux, actividadesEvaluador);
							actEvaluadorAux.add(actividadesEvaluador);
							i = j;
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Produccion> evaluacion = investigador.getProducciones();
		if (evaluacion == null) {
			investigador.setProducciones(actEvaluadorAux);
		} else {
			evaluacion.addAll(actEvaluadorAux);
			investigador.setProducciones(evaluacion);
		}
	}
	
	public void extraerPartipacionComitesI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_EVALUADOR, Constantes.EVALUADOR);

		ArrayList<Produccion> actEvaluadorAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			try {

				if (elem.get(i).startsWith("PARTICIPACIÓN EN COMITÉS DE EVALUACIÓN")) {
					for (int j = i; j < elem.size(); j++) {

						Produccion actividadesEvaluador = new Produccion();

						if (elem.get(i).startsWith("PAR EVALUADOR")) {
							break;
						} else if (elem.get(j).startsWith("DATOS COMPLEMENTARIOS")) {
							j++;
							autores = elem.get(j).substring(0, elem.get(j).indexOf(','));
							referencia = elem.get(j).substring(elem.get(j).indexOf(',') + 2) + " ";

							for (int k = j + 1; k < elem.size(); k++) {
								if (elem.get(k).startsWith("DATOS COMPLEMENTARIOS") || elem.get(k).startsWith("AREAS:")
										|| elem.get(k).startsWith("PAR EVALUADOR")) {
									break;
								}
								if (elem.get(k).endsWith(":")) {
									referencia += elem.get(k) + " ";
								} else {
									referencia += elem.get(k) + ", ";
								}
								j = k;
							}

							referencia = referencia.substring(0, referencia.length() - 2);
							anio= utils.extraerAnio(referencia);

							actividadesEvaluador.setAutores(autores);
							actividadesEvaluador.setReferencia(referencia);
							actividadesEvaluador.setAnio(anio);
							Tipo tipo = new Tipo(Constantes.ID_PARTICIPACION_COMITE_EVALUADOR,
									Constantes.PARTICIPACION_COMITE_EVALUADOR, tipoProduccion);
							actividadesEvaluador.setTipo(tipo);
							actividadesEvaluador.setInvestigador(investigador);
							actividadesEvaluador.setRepetido("NO");
							utils.identificarRepetidosI(actEvaluadorAux, actividadesEvaluador);
							actEvaluadorAux.add(actividadesEvaluador);
							i = j;
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Produccion> evaluacion = investigador.getProducciones();
		if (evaluacion == null) {
			investigador.setProducciones(actEvaluadorAux);
		} else {
			evaluacion.addAll(actEvaluadorAux);
			investigador.setProducciones(evaluacion);
		}
	}

	public void extraerParEvaluadorI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_EVALUADOR, Constantes.EVALUADOR);

		ArrayList<Produccion> actEvaluadorAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

				if (elem.get(i).startsWith("PAR EVALUADOR") && elem.get(i + 1).startsWith("ÁMBITO:")) {
					for (int j = i + 1; j < elem.size(); j++) {

						Produccion actividadesEvaluador = new Produccion();

						if (elem.get(j).startsWith("ÁMBITO:")) {

							referencia = elem.get(j) + " ";

							for (int k = j + 1; k < elem.size(); k++) {

								if (elem.get(k).startsWith("ÁMBITO:")) {
									break;
								}

								if (elem.get(k).endsWith(":")) {
									referencia += elem.get(k) + " ";
								} else {
									referencia += elem.get(k) + ", ";
								}

								j = k;
							}

							referencia = referencia.substring(0, referencia.length() - 2);

							anio= utils.extraerAnio(referencia);

							actividadesEvaluador.setAutores(autores);
							actividadesEvaluador.setReferencia(referencia);
							actividadesEvaluador.setAnio(anio);
							Tipo tipo = new Tipo(Constantes.ID_PAR_EVALUADOR, Constantes.PAR_EVALUADOR, tipoProduccion);
							actividadesEvaluador.setTipo(tipo);
							actividadesEvaluador.setInvestigador(investigador);
							actividadesEvaluador.setRepetido("NO");
							utils.identificarRepetidosI(actEvaluadorAux, actividadesEvaluador);
							actEvaluadorAux.add(actividadesEvaluador);
							i = j;
						}

					}
				}
		}
		List<Produccion> evaluacion = investigador.getProducciones();
		if (evaluacion == null) {
			investigador.setProducciones(actEvaluadorAux);
		} else {
			evaluacion.addAll(actEvaluadorAux);
			investigador.setProducciones(evaluacion);
		}
	}
}
