package co.edu.uniquindio.gri.controller;

import java.util.ArrayList;
import java.util.concurrent.Future;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.extractor.ExtractorApSocial;
import co.edu.uniquindio.gri.extractor.ExtractorArte;
import co.edu.uniquindio.gri.extractor.ExtractorBibliograficas;
import co.edu.uniquindio.gri.extractor.ExtractorEvaluacion;
import co.edu.uniquindio.gri.extractor.ExtractorFormacion;
import co.edu.uniquindio.gri.extractor.ExtractorGenerales;
import co.edu.uniquindio.gri.extractor.ExtractorInfoAdicional;
import co.edu.uniquindio.gri.extractor.ExtractorTecnicas;
import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.utils.ArrayUtils;
import co.edu.uniquindio.gri.utils.JSoupUtil;

@Service
public class InvestigadorController {

	@Autowired
	JSoupUtil util;

	@Async("executor2")
	public Future<Investigador> extraer(String estado, String url) {
		
		Investigador investigador = new Investigador();

		int statusConnectionCode = util.getStatusConnectionCode(url);

		if (statusConnectionCode == 200) {

			// Obtenemos el id del investigador a partir de la URL
			long id = Long.parseLong(url.substring(url.length() - 10));

			Document document = util.getHtmlDocument(url);
			Elements entradas = document.select("tbody>tr>td>table>tbody");

			investigador = extraerDatos(entradas, estado, id, investigador);

		} else {
			System.out.println("El Status Code no es OK es: " + statusConnectionCode);
		}
		return  new AsyncResult<Investigador> (investigador);

	}

	@Autowired
	ArrayUtils utils;

	@Autowired
	ExtractorGenerales extractor;

	@Autowired
	ExtractorBibliograficas extractorBibliograficas;

	@Autowired
	ExtractorTecnicas extractorTecnicas;

	@Autowired
	ExtractorApSocial extractorApSocial;

	@Autowired
	ExtractorFormacion extractorFormacion;

	@Autowired
	ExtractorEvaluacion extractorEvaluacion;

	@Autowired
	ExtractorInfoAdicional extractorInfoAdicional;

	@Autowired
	ExtractorArte extractorArte;

	public Investigador extraerDatos(Elements entradas, String estado, long id, Investigador investigador) {
		/*
		 * Extraer Datos Personales del investigador
		 */
		ArrayList<String> elemInfoPersonal = new ArrayList<>();

		if (estado.equals("ACTUAL")) {
			for (Element elem : entradas) {

				if (elem.text().contains("Nombre en citaciones")) {
					ArrayList<String> elemDatos = utils.ordenarArreglo(elem.toString());
					elemInfoPersonal.addAll(elemDatos);

				} else if (elem.text().contains("Formación Académica")) {
					ArrayList<String> elemDatos = utils.ordenarArreglo(elem.toString());
					elemInfoPersonal.addAll(elemDatos);

				} else if (elem.text().contains("Experiencia profesional")) {
					ArrayList<String> elemDatos = utils.ordenarArreglo(elem.toString());
					elemInfoPersonal.addAll(elemDatos);

				} else if (elem.text().contains("Líneas de investigación")) {
					ArrayList<String> elemLineas = utils.ordenarArreglo(elem.toString());
					extractor.extraerLineasInvestigacionI(elemLineas, investigador);

				}
			}

			investigador = extractor.extraerDatosGeneralesI(investigador, elemInfoPersonal, id, estado);

			for (Element elem : entradas) {

				/*
				 * Extraer idiomas de los investigadores
				 */

				if (elem.text().startsWith("Idiomas")) {
					ArrayList<String> elemIdiomas = utils.ordenarArreglo(elem.toString());
					extractor.extraerIdiomas(elemIdiomas, investigador);
				}

				/*
				 * Extraer las Actividades de Formacion
				 */

				else if (elem.text().contains("Cursos de corta duración")) {
					ArrayList<String> elemCursosCortaDuracion = utils.ordenarArreglo(elem.toString());
					extractorFormacion.extraerCursosCortosI(elemCursosCortaDuracion, investigador);

				} else if (elem.text().contains("Trabajos dirigidos/tutorías")) {
					ArrayList<String> elemTrabajosDirigidosTutorias = utils.ordenarArreglo(elem.toString());
					extractorFormacion.extraerTrabajosDirigidosI(elemTrabajosDirigidosTutorias, investigador);
				}

				/*
				 * Extraer las Actividades como Evaluador
				 */

				else if (elem.text().startsWith("Jurado en comités de evaluación")) {
					ArrayList<String> elemJuradoComite = utils.ordenarArreglo(elem.toString());
					extractorEvaluacion.extraerJuradoI(elemJuradoComite, investigador);

				} else if (elem.text().startsWith("Participación en comités de evaluación")) {
					ArrayList<String> elemParticipacionComite = utils.ordenarArreglo(elem.toString());
					extractorEvaluacion.extraerPartipacionComitesI(elemParticipacionComite, investigador);

				} else if (elem.text().contains("Par evaluador")
						&& elem.text().contains("Ámbito:")) {
					ArrayList<String> elemParEvaluador = utils.ordenarArreglo(elem.toString());
					extractorEvaluacion.extraerParEvaluadorI(elemParEvaluador, investigador);			
					
				}

				/*
				 * Extraer la Apropiacion social
				 */

				else if (elem.text().startsWith("Ediciones/revisiones")) {
					ArrayList<String> elemEdiciones = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerEdicionesI(elemEdiciones, investigador);

				} else if (elem.text().startsWith("Eventos científicos")) {
					ArrayList<String> elemEventos = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerEventosI(elemEventos, investigador);

				} else if (elem.text().startsWith("Redes de conocimiento especializado")) {
					ArrayList<String> elemRedesConocimiento = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerRedesDeConocimientoI(elemRedesConocimiento, investigador);

				} else if (elem.text().startsWith("Generación de contenido impresa")) {
					ArrayList<String> elemContenidoImpreso = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerContenidoImpresoI(elemContenidoImpreso, investigador);

				} else if (elem.text().startsWith("Generación de contenido multimedia")) {
					ArrayList<String> elemContenidoMultimedia = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerContenidoMultimediaI(elemContenidoMultimedia, investigador);

				} else if (elem.text().startsWith("Generación de contenido virtual")) {
					ArrayList<String> elemContenidoVirtual = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerContenidoVirtualI(elemContenidoVirtual, investigador);

				} else if (elem.text().startsWith("Estrategias de comunicación del conocimiento")) {
					ArrayList<String> elemEstrategiaComunicacion = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerEstrategiaComunicacionPedagogicaI(elemEstrategiaComunicacion,
							investigador);

				} else if (elem.text().startsWith("Estrategias pedagógicas para el fomento a la CTI")) {
					ArrayList<String> elemEstrategiaPedagogica = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerEstrategiaComunicacionPedagogicaI(elemEstrategiaPedagogica, investigador);

				} else if (elem.text().startsWith("Espacios de participación ciudadana")) {
					ArrayList<String> elemParticipacionCiudadana = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerParticipacionCiudadanaI(elemParticipacionCiudadana, investigador);

				} else if (elem.text().startsWith("Participación ciudadana en proyectos de CTI")) {
					ArrayList<String> elemParticipacionCiudadanaCti = utils.ordenarArreglo(elem.toString());
					extractorApSocial.extraerParticipacionCiudadanaCtiI(elemParticipacionCiudadanaCti, investigador);
				}

				/*
				 * Extraer Producciones bibliograficas
				 */

				else if (elem.text().startsWith("Artículos")) {
					ArrayList<String> elemArticulos = utils.ordenarArreglo(elem.toString());
					extractorBibliograficas.extraerArticulosI(elemArticulos, investigador);

				} else if (elem.text().startsWith("Libros")) {
					ArrayList<String> elemLibros = utils.ordenarArreglo(elem.toString());
					extractorBibliograficas.extraerLibrosI(elemLibros, investigador);

				} else if (elem.text().startsWith("Capitulos de libro")) {
					ArrayList<String> elemCapLibros = utils.ordenarArreglo(elem.toString());
					extractorBibliograficas.extraerCapLibrosI(elemCapLibros, investigador);

				} else if (elem.text().startsWith("Textos en publicaciones no científicas")) {
					ArrayList<String> elemPubNoCientificas = utils.ordenarArreglo(elem.toString());
					extractorBibliograficas.extraerPubNoCientificasI(elemPubNoCientificas, investigador);

				} else if (elem.text().startsWith("Otra producción blibliográfica")) {
					ArrayList<String> elemOtraProdBibliografica = utils.ordenarArreglo(elem.toString());
					extractorBibliograficas.extraerOtraProdBibliograficaI(elemOtraProdBibliografica, investigador);

				} else if (elem.text().startsWith("Documentos de trabajo")) {
					ArrayList<String> elemDocumentosTrabajo = utils.ordenarArreglo(elem.toString());
					extractorBibliograficas.extraerOtraProdBibliograficaI(elemDocumentosTrabajo, investigador);
				}

				/*
				 * Extraer Producciones tecnicas
				 */

				else if (elem.text().startsWith("Softwares")) {
					ArrayList<String> elemSoftwares = utils.ordenarArreglo(elem.toString());
					extractorTecnicas.extraerProdTecnicaI(elemSoftwares, investigador);

				} else if (elem.text().startsWith("Prototipos")) {
					ArrayList<String> elemPatentes = utils.ordenarArreglo(elem.toString());
					extractorTecnicas.extraerProdTecnicaI(elemPatentes, investigador);

				} else if (elem.text().startsWith("Productos tecnológicos")) {
					ArrayList<String> elemProdTecnologicos = utils.ordenarArreglo(elem.toString());
					extractorTecnicas.extraerProdTecnicaI(elemProdTecnologicos, investigador);

				} else if (elem.text().startsWith("Informes de investigaci")) {
					ArrayList<String> elemInformeInvestigacion = utils.ordenarArreglo(elem.toString());
					extractorTecnicas.extraerProdTecnicaI(elemInformeInvestigacion, investigador);

				} else if (elem.text().startsWith("Innovación de proceso o procedimiento")) {
					ArrayList<String> elemProcesosTecnicas = utils.ordenarArreglo(elem.toString());
					extractorTecnicas.extraerProdTecnicaI(elemProcesosTecnicas, investigador);

				} else if (elem.text().startsWith("Trabajos técnicos")) {
					ArrayList<String> elemTrabajosTecnicos = utils.ordenarArreglo(elem.toString());
					extractorTecnicas.extraerProdTecnicaI(elemTrabajosTecnicos, investigador);

				} else if (elem.text().startsWith("Normas y Regulaciones")) {
					ArrayList<String> elemNormasRegulaciones = utils.ordenarArreglo(elem.toString());
					extractorTecnicas.extraerProdTecnicaI(elemNormasRegulaciones, investigador);

				} else if (elem.text().startsWith("Empresas de base tecnológica")) {
					ArrayList<String> elemEmpresasTecnologicas = utils.ordenarArreglo(elem.toString());
					extractorTecnicas.extraerEmpresasI(elemEmpresasTecnologicas, investigador);
				}

				/*
				 * Extraer Informacion adicional
				 */

				else if (elem.text().startsWith("Demás trabajos")) {
					ArrayList<String> elemDemasTrabajos = utils.ordenarArreglo(elem.toString());
					extractorInfoAdicional.extraerDemasTrabajosI(elemDemasTrabajos, investigador);

				} else if (elem.text().startsWith("Proyectos")) {
					ArrayList<String> elemProyectos = utils.ordenarArreglo(elem.toString());
					extractorInfoAdicional.extraerProyectosI(elemProyectos, investigador);
				}

				/*
				 * Extraer Producciones en Arte
				 */

				else if (elem.text().startsWith("Obras o productos")) {
					ArrayList<String> elemObrasProductos = utils.ordenarArreglo(elem.toString());
					extractorArte.extraerObrasI(elemObrasProductos, investigador);

				} else if (elem.text().startsWith("Registros de acuerdo de licencia")) {
					ArrayList<String> elemRegistrosLicencia = utils.ordenarArreglo(elem.toString());
					extractorArte.extraerRegistrosAcuerdoI(elemRegistrosLicencia, investigador);

				} else if (elem.text().startsWith("Industrias Creativas y culturales")) {
					ArrayList<String> elemIndustriasCreativas = utils.ordenarArreglo(elem.toString());
					extractorArte.extraerIndustriasI(elemIndustriasCreativas, investigador);

				} else if (elem.text().startsWith("Eventos artísticos")) {
					ArrayList<String> elemEventoArtistico = utils.ordenarArreglo(elem.toString());
					extractorArte.extraerEventoArtisticoI(elemEventoArtistico, investigador);

				} else if (elem.text().startsWith("Talleres Creativos")) {
					ArrayList<String> elemTalleresCreativos = utils.ordenarArreglo(elem.toString());
					extractorArte.extraerTallerCreativoI(elemTalleresCreativos, investigador);
				}

			}
			
		} else if (estado.equals("NO ACTUAL")) {
			for (Element elem : entradas) {

				if (elem.text().contains("Nombre en citaciones")) {
					ArrayList<String> elemDatos = utils.ordenarArreglo(elem.toString());
					elemInfoPersonal.addAll(elemDatos);
				}

				if (elem.text().contains("Formación Académica")) {
					ArrayList<String> elemDatos = utils.ordenarArreglo(elem.toString());
					elemInfoPersonal.addAll(elemDatos);
				}

				if (elem.text().contains("Experiencia profesional")) {
					ArrayList<String> elemDatos = utils.ordenarArreglo(elem.toString());
					elemInfoPersonal.addAll(elemDatos);
				}
			}

			investigador = extractor.extraerDatosGeneralesI(investigador, elemInfoPersonal, id, estado);
		}
		return investigador;
	}
	
}