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
public class ExtractorApSocial {

	@Autowired
	ArrayUtils utils;

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerEdicionesG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<ProduccionGrupo> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo apropiacionSocial = new ProduccionGrupo();

			if (elem.get(i).contains(".-")) {
				int cont = i + 2;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")) {
					String actual = elem.get(cont);
					referencia += " " + actual;

					if (actual.contains("AUTORES:")) {
						autores = actual.substring(9, actual.length() - 1);
					}
					cont++;
				}
				referencia = referencia.substring(3, referencia.length() - 1);
				anio = utils.extraerAnio(referencia);

				apropiacionSocial.setAnio(anio);
				apropiacionSocial.setAutores(autores);
				apropiacionSocial.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_EDICION, Constantes.EDICION, tipoProduccion);
				apropiacionSocial.setTipo(tipo);
				apropiacionSocial.setGrupo(grupo);
				apropiacionSocial.setRepetido("NO");
				utils.identificarRepetidosG(actAprSocialAux, apropiacionSocial);
				actAprSocialAux.add(apropiacionSocial);
			}
		}
		
		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_EDICION,
				grupo.getProduccion(), actAprSocialAux);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerInformesG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<ProduccionGrupo> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo apropiacionSocial = new ProduccionGrupo();

			if (elem.get(i).contains(".-")) {
				int cont = i + 2;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")) {
					String actual = elem.get(cont);
					referencia += " " + actual;

					if (actual.contains("AUTORES:")) {
						autores = actual.substring(9, actual.length() - 1);
					}
					cont++;
				}
				referencia = referencia.substring(3, referencia.length() - 1);
				anio = utils.extraerAnio(referencia);

				apropiacionSocial.setAnio(anio);
				apropiacionSocial.setAutores(autores);
				apropiacionSocial.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_INFORME_INVESTIGACION, Constantes.INFORME_INVESTIGACION,
						tipoProduccion);
				apropiacionSocial.setTipo(tipo);
				apropiacionSocial.setGrupo(grupo);
				apropiacionSocial.setRepetido("NO");
				utils.identificarRepetidosG(actAprSocialAux, apropiacionSocial);
				actAprSocialAux.add(apropiacionSocial);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_INFORME_INVESTIGACION,
				grupo.getProduccion(), actAprSocialAux);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerRedesYParticipacionG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo apropiacionSocial = new ProduccionGrupo();

			if (elem.get(i).contains("REDES DE CONOCIMIENTO")) {

				tipo = new Tipo(Constantes.ID_RED, Constantes.RED, tipoProduccion);

			} else if (elem.get(i).contains("ESPACIOS DE PARTICIPACIÓN CIUDADANA")) {

				tipo = new Tipo(Constantes.ID_ESPACIO_PARTICIPACION, Constantes.ESPACIO_PARTICIPACION, tipoProduccion);

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

				apropiacionSocial.setAnio(anio);
				apropiacionSocial.setAutores(autores);
				apropiacionSocial.setReferencia(referencia);
				apropiacionSocial.setTipo(tipo);
				apropiacionSocial.setGrupo(grupo);
				apropiacionSocial.setRepetido("NO");
				utils.identificarRepetidosG(actAprSocialAux, apropiacionSocial);
				actAprSocialAux.add(apropiacionSocial);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones((int)tipo.getId(),
				grupo.getProduccion(), actAprSocialAux);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerContenidoImpresoMultimediaVirtualG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		Tipo tipo = new Tipo();

		ArrayList<ProduccionGrupo> actAprSocialAux = new ArrayList<>();
		
		ArrayList<ProduccionGrupo> contenidoImpreso = new ArrayList<>();		
		ArrayList<ProduccionGrupo> contenidoMultimedia = new ArrayList<>();		
		ArrayList<ProduccionGrupo> contenidoVirtual = new ArrayList<>();	

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo apropiacionSocial = new ProduccionGrupo();

			if (elem.get(i).contains("GENERACIÓN DE CONTENIDO IMPRESO")) {

				tipo = new Tipo(Constantes.ID_CONTENIDO_IMPRESO, Constantes.CONTENIDO_IMPRESO, tipoProduccion);

			} else if (elem.get(i).contains("GENERACIÓN DE CONTENIDO MULTIMEDIA")) {

				tipo = new Tipo(Constantes.ID_CONTENIDO_MULTIMEDIA, Constantes.CONTENIDO_MULTIMEDIA, tipoProduccion);

			} else if (elem.get(i).contains("GENERACIÓN DE CONTENIDO VIRTUAL")) {

				tipo = new Tipo(Constantes.ID_CONTENIDO_VIRTUAL, Constantes.CONTENIDO_VIRTUAL, tipoProduccion);

			}
			if (elem.get(i).contains(".-")) {
				int cont = i + 2;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")) {
					String actual = elem.get(cont);
					referencia += " " + actual;

					if (actual.contains("AUTORES:")) {
						autores = actual.substring(9, actual.length() - 1);
					}
					cont++;
				}
				referencia = referencia.substring(3, referencia.length() - 1);
				anio = utils.extraerAnio(referencia);

				apropiacionSocial.setAnio(anio);
				apropiacionSocial.setAutores(autores);
				apropiacionSocial.setReferencia(referencia);
				apropiacionSocial.setTipo(tipo);
				apropiacionSocial.setGrupo(grupo);
				apropiacionSocial.setRepetido("NO");
				utils.identificarRepetidosG(actAprSocialAux, apropiacionSocial);
				actAprSocialAux.add(apropiacionSocial);
			}
		}

		List<ProduccionGrupo> produccionI = utils.verificarProducciones(Constantes.ID_CONTENIDO_IMPRESO,
				grupo.getProduccion(), contenidoImpreso);
		grupo.setProduccion(produccionI);
		
		List<ProduccionGrupo> produccionM = utils.verificarProducciones(Constantes.ID_CONTENIDO_MULTIMEDIA,
				grupo.getProduccion(), contenidoMultimedia);
		grupo.setProduccion(produccionM);
		
		List<ProduccionGrupo> produccionV = utils.verificarProducciones(Constantes.ID_CONTENIDO_VIRTUAL,
				grupo.getProduccion(), contenidoVirtual);
		grupo.setProduccion(produccionV);
		
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerEstrategiasComunicacionG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<ProduccionGrupo> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo apropiacionSocial = new ProduccionGrupo();

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

				apropiacionSocial.setAnio(anio);
				apropiacionSocial.setAutores(autores);
				apropiacionSocial.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_ESTRATEGIA_COMUNICACION, Constantes.ESTRATEGIA_COMUNICACION,
						tipoProduccion);
				apropiacionSocial.setTipo(tipo);
				apropiacionSocial.setGrupo(grupo);
				apropiacionSocial.setRepetido("NO");
				utils.identificarRepetidosG(actAprSocialAux, apropiacionSocial);
				actAprSocialAux.add(apropiacionSocial);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_ESTRATEGIA_COMUNICACION,
				grupo.getProduccion(), actAprSocialAux);
		grupo.setProduccion(produccion);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerEstrategiasYParticipacionCtiG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);
 
		ArrayList<ProduccionGrupo> actAprSocialAux = new ArrayList<>();
		
		ArrayList<ProduccionGrupo> estrategiaPedagogica = new ArrayList<>();
		ArrayList<ProduccionGrupo> participacionCTI = new ArrayList<>();
		
		Tipo tipo = new Tipo();

		for (int i = 0; i < elem.size(); i++) {
			ProduccionGrupo apropiacionSocial = new ProduccionGrupo();
			
			if (elem.get(i).contains("ESTRATEGIAS PEDAGÓGICAS PARA EL FOMENTO A LA CTI")) {

				tipo = new Tipo(Constantes.ID_ESTRATEGIA_PEDAGOGICA, Constantes.ESTRATEGIA_PEDAGOGICA,
						tipoProduccion);

			} else if (elem.get(i).contains("PARTICIPACIÓN CIUDADANA EN PROYECTOS DE CTI")){

				tipo = new Tipo(Constantes.ID_ESPACIO_PARTICIPACION_CTI, Constantes.ESPACIO_PARTICIPACION_CTI, tipoProduccion);

			}

			if (elem.get(i).contains(".-")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")
						&& !elem.get(cont).contains("DESCRIPCIÓN:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;

					if (actual.contains("AUTORES:")) {
						autores = actual.substring(9, actual.length() - 1);
					}
					cont++;
				}
				referencia = referencia.trim();
				anio = utils.extraerAnio(referencia);

				apropiacionSocial.setAnio(anio);
				apropiacionSocial.setAutores(autores);
				apropiacionSocial.setReferencia(referencia);;
				apropiacionSocial.setTipo(tipo);
				apropiacionSocial.setGrupo(grupo);
				apropiacionSocial.setRepetido("NO");
				utils.identificarRepetidosG(actAprSocialAux, apropiacionSocial);
				actAprSocialAux.add(apropiacionSocial);
			}
		}

		List<ProduccionGrupo> produccionP = utils.verificarProducciones(Constantes.ID_ESTRATEGIA_PEDAGOGICA,
				grupo.getProduccion(), estrategiaPedagogica);
		grupo.setProduccion(produccionP);
		
		List<ProduccionGrupo> produccionCTI = utils.verificarProducciones(Constantes.ID_ESPACIO_PARTICIPACION_CTI,
				grupo.getProduccion(), participacionCTI);
		grupo.setProduccion(produccionCTI);
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public void extraerEventosG(ArrayList<String> elem, Grupo grupo) {
		String autores = "";
		String referencia = "";
		String anio = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<ProduccionGrupo> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			ProduccionGrupo apropiacionSocial = new ProduccionGrupo();

			if (elem.get(i).contains(".-")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains(".-")
						&& !elem.get(cont).contains("INSTITUCIONES ASOCIADAS")
						&& !elem.get(cont).contains("NOMBRE DE LA INSTITUCIÓN:")
						&& !elem.get(cont).contains("TIPO DE VINCULACIÓN")) {
					String actual = elem.get(cont);
					referencia += " " + actual;

					if (actual.contains("AUTORES:")) {
						autores = actual.substring(9, actual.length() - 1);
					}
					cont++;
				}
				referencia = referencia.trim();
				anio = utils.extraerAnio(referencia);

				apropiacionSocial.setAnio(anio);
				apropiacionSocial.setAutores(autores);
				apropiacionSocial.setReferencia(referencia);
				Tipo tipo = new Tipo(Constantes.ID_EVENTO_CIENTIFICO, Constantes.EVENTO_CIENTIFICO, tipoProduccion);
				apropiacionSocial.setTipo(tipo);
				apropiacionSocial.setGrupo(grupo);
				apropiacionSocial.setRepetido("NO");
				utils.identificarRepetidosG(actAprSocialAux, apropiacionSocial);
				actAprSocialAux.add(apropiacionSocial);
			}
		}

		List<ProduccionGrupo> produccion = utils.verificarProducciones(Constantes.ID_EVENTO_CIENTIFICO,
				grupo.getProduccion(), actAprSocialAux);
		grupo.setProduccion(produccion);
	}

	
	/*
	 * Metodos que extraen informacion de los investigadores
	 */
	
	public void extraerEdicionesI(ArrayList<String> elem, Investigador investigador) {
		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			Produccion actAprSocial = new Produccion();

			if (elem.get(i).startsWith("PRODUCCIÓN TÉCNICA - EDITORACIÓN O REVISIÓN")) {
				autores = elem.get(i + 1).substring(0, elem.get(i + 1).indexOf(","));
				referencia = elem.get(i + 1).substring(elem.get(i + 1).indexOf(",") + 2,
						elem.get(i + 1).lastIndexOf(","));
			}
			if (elem.get(i).contains("EN: ")) {
				anio = utils.extraerAnio(referencia);

				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				Tipo tipo = new Tipo(Constantes.ID_EDICION, Constantes.EDICION, tipoProduccion);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	public void extraerEventosI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			Produccion actAprSocial = new Produccion();

			if (elem.get(i).contains("NOMBRE DEL EVENTO:")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL EVENTO:")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:") && !elem.get(cont).contains("PRODUCTOS ASOCIADOS")
						&& !elem.get(cont).contains("NOMBRE DEL PRODUCTO:")
						&& !elem.get(cont).contains("TIPO DE PRODUCTO:")
						&& !elem.get(cont).contains("INSTITUCIONES ASOCIADAS")
						&& !elem.get(cont).contains("PARTICIPANTES") && !StringUtils.isNumeric(elem.get(cont))) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.trim();
			}
			if (elem.get(i).contains("REALIZADO EL:")) {
				anio= utils.extraerAnio(referencia);
			}
			autores = "";
			if (elem.get(i).contains("PARTICIPANTES")) {
				int cont = i + 1;
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL EVENTO:")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:") && !elem.get(cont).contains("PARTICIPANTES")
						&& !StringUtils.isNumeric(elem.get(cont))) {
					String actual = elem.get(cont);
					autores += " " + actual;
					cont++;
				}
				autores = autores.replace("NOMBRE:", "");
				autores = autores.trim();
				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				Tipo tipo = new Tipo(Constantes.ID_EVENTO_CIENTIFICO, Constantes.EVENTO_CIENTIFICO, tipoProduccion);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	public void extraerRedesDeConocimientoI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			Produccion actAprSocial = new Produccion();

			if (elem.get(i).contains("NOMBRE DE LA RED")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DE LA RED")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.trim();
			}
			if (elem.get(i).contains("CREADA EL:")) {
				anio= utils.extraerAnio(referencia);
				
				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				Tipo tipo = new Tipo(Constantes.ID_RED, Constantes.RED, tipoProduccion);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	public void extraerContenidoImpresoI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			
			Produccion actAprSocial = new Produccion();
			
			if (elem.get(i).contains("NOMBRE")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE") && !elem.get(cont).contains("PALABRAS:")
						&& !elem.get(cont).contains("SECTORES:") && !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.trim();
			}
			if (elem.get(i).contains("EN LA FECHA")) {

				anio= utils.extraerAnio(referencia);
				
				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				Tipo tipo = new Tipo(Constantes.ID_CONTENIDO_IMPRESO, Constantes.CONTENIDO_IMPRESO, tipoProduccion);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	public void extraerContenidoMultimediaI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains("PRODUCCIÓN TÉCNICA - MULTIMEDIA -")) {
				
				Produccion actAprSocial = new Produccion();
				
				String aux = elem.get(i + 1).substring(0, elem.get(i + 1).lastIndexOf(",")).replaceAll(", ", ",");
				
				autores = utils.verificarAutores(aux, investigador);
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("PRODUCCIÓN TÉCNICA - MULTIMEDIA -")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.substring(1);
				anio= utils.extraerAnio(referencia);
				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				Tipo tipo = new Tipo(Constantes.ID_CONTENIDO_MULTIMEDIA, Constantes.CONTENIDO_MULTIMEDIA,
						tipoProduccion);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	public void extraerContenidoVirtualI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			Produccion actAprSocial = new Produccion();

			if (elem.get(i).contains("NOMBRE")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE") && !elem.get(cont).contains("PALABRAS:")
						&& !elem.get(cont).contains("SECTORES:") && !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.trim();
			}
			if (elem.get(i).contains(", EN 1") || elem.get(i).contains(", EN 2")) {
				anio= utils.extraerAnio(referencia);
				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				Tipo tipo = new Tipo(Constantes.ID_CONTENIDO_VIRTUAL, Constantes.CONTENIDO_VIRTUAL, tipoProduccion);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	public void extraerEstrategiaComunicacionPedagogicaI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";
		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		Tipo tipo = new Tipo();

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			Produccion actAprSocial = new Produccion();

			if (elem.get(i).contains("ESTRATEGIAS DE COMUNICACIÓN DEL CONOCIMIENTO")) {

				tipo = new Tipo(Constantes.ID_ESTRATEGIA_COMUNICACION, Constantes.ESTRATEGIA_COMUNICACION,
						tipoProduccion);

			} else if (elem.get(i).contains("ESTRATEGIAS PEDAGÓGICAS PARA EL FOMENTO A LA CTI")) {

				tipo = new Tipo(Constantes.ID_ESTRATEGIA_PEDAGOGICA, Constantes.ESTRATEGIA_PEDAGOGICA, tipoProduccion);

			}
			if (elem.get(i).contains("NOMBRE DE LA ESTRATEGIA")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DE LA ESTRATEGIA")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.trim();
			}
			if (elem.get(i).contains("FINALIZÓ EN :")) {
				anio= utils.extraerAnio(referencia);
				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	public void extraerParticipacionCiudadanaI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			Produccion actAprSocial = new Produccion();

			if (elem.get(i).contains("NOMBRE DEL ESPACIO")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL ESPACIO")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.trim();
			}
			if (elem.get(i).contains("REALIZADO EL:")) {
				anio= utils.extraerAnio(referencia);
				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				Tipo tipo = new Tipo(Constantes.ID_ESPACIO_PARTICIPACION, Constantes.ESPACIO_PARTICIPACION,
						tipoProduccion);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	public void extraerParticipacionCiudadanaCtiI(ArrayList<String> elem, Investigador investigador) {

		String autores = "";
		String anio = "";
		String referencia = "";

		TipoProduccion tipoProduccion = new TipoProduccion(Constantes.ID_APROPIACION, Constantes.APROPIACION);

		ArrayList<Produccion> actAprSocialAux = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			Produccion actAprSocial = new Produccion();

			if (elem.get(i).contains("NOMBRE DEL PROYECTO")) {
				int cont = i + 1;
				referencia = "";
				while (cont < elem.size() && !elem.get(cont).contains("NOMBRE DEL PROYECTO")
						&& !elem.get(cont).contains("PALABRAS:") && !elem.get(cont).contains("SECTORES:")
						&& !elem.get(cont).contains("AREAS:")) {
					String actual = elem.get(cont);
					referencia += " " + actual;
					cont++;
				}
				referencia = referencia.trim();
			}
			if (elem.get(i).contains("FINALIZÓ EN :")) {
				anio= utils.extraerAnio(referencia);
				actAprSocial.setAutores(autores);
				actAprSocial.setReferencia(referencia);
				actAprSocial.setAnio(anio);
				Tipo tipo = new Tipo(Constantes.ID_ESPACIO_PARTICIPACION_CTI, Constantes.ESPACIO_PARTICIPACION_CTI,
						tipoProduccion);
				actAprSocial.setTipo(tipo);
				actAprSocial.setInvestigador(investigador);
				actAprSocial.setRepetido("NO");
				utils.identificarRepetidosI(actAprSocialAux, actAprSocial);
				actAprSocialAux.add(actAprSocial);
			}

		}

		List<Produccion> aprSocial = investigador.getProducciones();
		if (aprSocial == null) {
			investigador.setProducciones(actAprSocialAux);
		} else {
			aprSocial.addAll(actAprSocialAux);
			investigador.setProducciones(aprSocial);
		}
	}

	
	
}
