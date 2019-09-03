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
public class ExtractorFormacion {

	@Autowired
	ArrayUtils utils;

	/**
	 * 
	 * @param elem
	 * @param bandera
	 * @return
	 */
	public void extraerCursosCortosG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_FORMACION, Constantes.FORMACION);

		ArrayList<ProduccionGrupo> actFormacionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo actividadesFormacion = new ProduccionGrupo();

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

				actividadesFormacion.setAnio(anio);
				actividadesFormacion.setAutores(autores);
				actividadesFormacion.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_CURSO_CORTO, Constantes.CURSO_CORTO, tipoProduccion);
				actividadesFormacion.setTipo(tipo);
				actividadesFormacion.setGrupo(grupo);
				actividadesFormacion.setRepetido("NO");
				utils.identificarRepetidosG(actFormacionAux, actividadesFormacion);
				actFormacionAux.add(actividadesFormacion);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_CURSO_CORTO,
				grupo.getProduccion(), actFormacionAux);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerTrabajosDirigidosG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_FORMACION, Constantes.FORMACION);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> actFormacionAux = new ArrayList<>();
		
		ArrayList<ProduccionGrupo> trabajosPregrado = new ArrayList<>();
		ArrayList<ProduccionGrupo> trabajosMaestria = new ArrayList<>();
		ArrayList<ProduccionGrupo> trabajosDoctorado = new ArrayList<>();
		ArrayList<ProduccionGrupo> otros = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			

			if (elem.get(i).contains(".-")) {
				
				ProduccionGrupo actividadesFormacion = new ProduccionGrupo();
				
				if (elem.get(i + 1).contains("TRABAJOS DE GRADO DE PREGRADO")) {

					tipo = new Tipo(Constantes.ID_TRABAJO_GRADO_P, Constantes.TRABAJO_GRADO_P, tipoProduccion);

				} else if (elem.get(i + 1).contains("TRABAJO DE GRADO DE MAESTRÍA")) {

					tipo = new Tipo(Constantes.ID_TRABAJO_GRADO_M, Constantes.TRABAJO_GRADO_M, tipoProduccion);

				} else if (elem.get(i + 1).contains("TESIS DE DOCTORADO")) {

					tipo = new Tipo(Constantes.ID_TRABAJO_GRADO_D, Constantes.TRABAJO_GRADO_D, tipoProduccion);

				} else {

					tipo = new Tipo(Constantes.ID_TUTORIA, Constantes.TUTORIA, tipoProduccion);

				}
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

				actividadesFormacion.setAnio(anio);
				actividadesFormacion.setAutores(autores);
				actividadesFormacion.setReferencia(referencia);
				actividadesFormacion.setTipo(tipo);
				actividadesFormacion.setGrupo(grupo);
				actividadesFormacion.setRepetido("NO");
				utils.identificarRepetidosG(actFormacionAux, actividadesFormacion);
				
				if(actividadesFormacion.getTipo().getId()==Constantes.ID_TRABAJO_GRADO_P){
					trabajosPregrado.add(actividadesFormacion);
				}else if(actividadesFormacion.getTipo().getId()==Constantes.ID_TRABAJO_GRADO_M){
					trabajosMaestria.add(actividadesFormacion);
				}else if(actividadesFormacion.getTipo().getId()==Constantes.ID_TRABAJO_GRADO_D){
					trabajosDoctorado.add(actividadesFormacion);
				}else{
					otros.add(actividadesFormacion);
				}
				
				actFormacionAux.add(actividadesFormacion);
			}
		}

		List<ProduccionGrupo> produccionP = utils.verificarProducciones(Constantes.ID_TRABAJO_GRADO_P,
				grupo.getProduccion(), trabajosPregrado);
		grupo.setProduccion(produccionP);
		
		List<ProduccionGrupo> produccionM = utils.verificarProducciones(Constantes.ID_TRABAJO_GRADO_M,
				grupo.getProduccion(), trabajosMaestria);
		grupo.setProduccion(produccionM);
		
		List<ProduccionGrupo> produccionD = utils.verificarProducciones(Constantes.ID_TRABAJO_GRADO_D,
				grupo.getProduccion(), trabajosDoctorado);
		grupo.setProduccion(produccionD);
		
		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_TUTORIA,
				grupo.getProduccion(), otros);
		grupo.setProduccion(produccion);
	
	}
	
	/*
	 * Metodos que extraen informacion de los investigadores
	 */

	public void extraerCursosCortosI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		ArrayList<Produccion> actFormacionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			if (elem.get(i).startsWith("CURSOS DE CORTA DURACIÓN")) {
				for (int j = i; j < elem.size(); j++) {
					if (elem.get(j).startsWith("TRABAJOS DIRIGIDOS/TUTORÍAS")) {
						break;
					}
					Produccion actividadesFormacion = new Produccion();
					if (elem.get(j).contains("PRODUCCIÓN TÉCNICA - CURSOS DE CORTA DURACIÓN DICTADOS -")) {
						try {
							j++;
							autores = elem.get(j).substring(0, elem.get(j).indexOf(","));
							referencia= elem.get(j).substring(elem.get(j).indexOf(",")+2);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (elem.get(j).contains("FINALIDAD:")) {
						referencia=referencia+" "+elem.get(j);
						referencia=referencia+elem.get(j+1);
						anio= utils.extraerAnio(referencia);

						actividadesFormacion.setAutores(autores);
						actividadesFormacion.setAnio(anio);
						actividadesFormacion.setReferencia(referencia);
						TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_FORMACION,
								Constantes.FORMACION);
						Tipo tipo = new Tipo(Constantes.ID_CURSO_CORTO, Constantes.CURSO_CORTO, tipoProduccion);
						actividadesFormacion.setTipo(tipo);
						actividadesFormacion.setInvestigador(investigador);
						actividadesFormacion.setRepetido("NO");
						utils.identificarRepetidosI(actFormacionAux, actividadesFormacion);
						actFormacionAux.add(actividadesFormacion);
					}
				}
			}
		}

		List<Produccion> formacion = investigador.getProducciones();
		if (formacion == null) {
			investigador.setProducciones(actFormacionAux);
		} else {
			formacion.addAll(actFormacionAux);
			investigador.setProducciones(formacion);
		}
	}

	public void extraerTrabajosDirigidosI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_FORMACION, Constantes.FORMACION);

		Tipo tipo = new Tipo();

		ArrayList<Produccion> actFormacionAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("TRABAJOS DIRIGIDOS/TUTORÍAS - ")) {
				Produccion actividadesFormacion = new Produccion();

				if (elem.get(i).contains("TRABAJOS DE GRADO DE PREGRADO")) {

					tipo = new Tipo(Constantes.ID_TRABAJO_GRADO_P, Constantes.TRABAJO_GRADO_P, tipoProduccion);

				} else if (elem.get(i).contains("TRABAJO DE GRADO DE MAESTRÍA")) {

					tipo = new Tipo(Constantes.ID_TRABAJO_GRADO_M, Constantes.TRABAJO_GRADO_M, tipoProduccion);

				} else if (elem.get(i).contains("TESIS DE DOCTORADO")) {

					tipo = new Tipo(Constantes.ID_TRABAJO_GRADO_D, Constantes.TRABAJO_GRADO_D, tipoProduccion);

				} else {

					tipo = new Tipo(Constantes.ID_TUTORIA, Constantes.TUTORIA, tipoProduccion);

				}
				String aux = elem.get(i + 1).substring(0, elem.get(i + 1).lastIndexOf(",")).replaceAll(", ", ",");
				autores = utils.verificarAutores(aux, investigador);
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("TRABAJOS DIRIGIDOS/TUTORÍAS - ")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.trim();
				anio= utils.extraerAnio(referencia);

				actividadesFormacion.setAutores(autores);
				actividadesFormacion.setAnio(anio);
				actividadesFormacion.setReferencia(referencia);
				actividadesFormacion.setTipo(tipo);
				actividadesFormacion.setInvestigador(investigador);
				actividadesFormacion.setRepetido("NO");
				utils.identificarRepetidosI(actFormacionAux, actividadesFormacion);
				actFormacionAux.add(actividadesFormacion);
			}
		}
		List<Produccion> formacion = investigador.getProducciones();
		if (formacion == null) {
			investigador.setProducciones(actFormacionAux);
		} else {
			formacion.addAll(actFormacionAux);
			investigador.setProducciones(formacion);
		}
	}

}
