package co.edu.uniquindio.gri.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.controller.InvestigadorController;
import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.Idiomas;
import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.model.LineasInvestigacion;
import co.edu.uniquindio.gri.utils.ArrayUtils;

@Service
public class ExtractorGenerales {

	@Autowired
	ArrayUtils utils;

	public Grupo extraerDatosGeneralesG(Grupo grupo, ArrayList<String> elemInfoGeneral) {

		try {
			for (int i = 0; i < elemInfoGeneral.size(); i++) {

				// Extraccion del año en que se formo el grupo de investigacion
				if (elemInfoGeneral.get(i).startsWith("AÑO Y MES DE FORMACIÓN")) {
					String anioFormacion = elemInfoGeneral.get(i + 1);
					grupo.setAnioFundacion(anioFormacion);
				}

				// Extraccion del Lider del grupo de investigacion
				if (elemInfoGeneral.get(i).startsWith("LÍDER")) {
					grupo.setLider(elemInfoGeneral.get(i + 1));
				}

				// Extraccion de la categoria del grupo de investigacion
				if (elemInfoGeneral.get(i).startsWith("CLASIFICACIÓN")) {
					grupo.setCategoria(elemInfoGeneral.get(i + 1));
					if (grupo.getCategoria().equalsIgnoreCase("ÁREA DE CONOCIMIENTO")) {
						grupo.setCategoria("SIN CATEGORÍA");
					}
				}

				// Extraccion del area de conocimiento que cobija al grupo de
				// investigacion
				if (elemInfoGeneral.get(i).startsWith("ÁREA DE CONOCIMIENTO")) {
					grupo.setAreaConocimiento(elemInfoGeneral.get(i + 1));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grupo;
	}

	public void extraerLineasInvestigacionG(ArrayList<String> elem, Grupo grupo) {

		ArrayList<LineasInvestigacion> lineas = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			if (elem.get(i).contains(".-")) {
				String nomLinea = elem.get(i).substring(elem.get(i).indexOf(".- ") + 3);
				nomLinea = StringUtils.stripAccents(nomLinea);
				nomLinea = nomLinea.trim();

				LineasInvestigacion lineaInvestigacion = new LineasInvestigacion();
				int pos=utils.BuscarLineasRepetidas(nomLinea);
				if (pos<0) {
					lineaInvestigacion.setNombre(nomLinea);
					utils.getLineasInvestigacion().add(lineaInvestigacion);
					lineas.add(lineaInvestigacion);
				}else {
					lineas.add(utils.getLineasInvestigacion().get(pos));
				}
				
			}

		}
		grupo.setLineasInvestigacion(lineas);
	}

	
	private InvestigadorController investigadorController;
	
	@Autowired
	public ExtractorGenerales(@Lazy InvestigadorController investigadorController) {
		this.investigadorController=investigadorController;
	}

	/**
	 * Metodo que extrae la lista de investigadores y sus respectivos links
	 * 
	 * @param elem
	 * @param grupo
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void extraerIntegrantes(ArrayList<String> elem, Grupo grupo)
			throws InterruptedException, ExecutionException {

		String link = "";

		for (int i = 0; i < elem.size(); i++) {
			if (elem.get(i).contains(".-")) {
				link = elem.get(i + 1);
				Future<Investigador> auxInvestigador;
				if (elem.get(i + 3).contains("Actual") || elem.get(i + 4).contains("Actual")
						|| elem.get(i + 5).contains("Actual")) {
					auxInvestigador = investigadorController.extraer("ACTUAL", link);
					grupo.addInvestigador(auxInvestigador.get(), "ACTUAL");
				} else {
					auxInvestigador = investigadorController.extraer("NO ACTUAL", link);
					grupo.addInvestigador(auxInvestigador.get(), "NO ACTUAL");
				}
			}
		}
	}

	public Investigador extraerDatosGeneralesI(Investigador investigador, ArrayList<String> elemInfoPersonal, long id,
			String estado) {

		boolean pertenece = false;
		investigador.setId(id);

		// Si no posee datos personales, el perfil es privado.
		if (elemInfoPersonal.size() == 0) {

			investigador.setNombre("PERFIL PRIVADO");
			investigador.setNombreInvestigadorAux(investigador.getNombre());
			investigador.setId(id);
			investigador.setCategoria("SIN CATEGORÍA");
			investigador.setNivelAcademico("NO ESPECIFICADO");
			investigador.setPertenencia("NO ESPECIFICADO");

		} else {
			try {

				for (int i = 0; i < elemInfoPersonal.size(); i++) {

					// Extraccion de la categoria del investigador

					if (elemInfoPersonal.get(i).startsWith("CATEGORÍA")) {
						String registro = elemInfoPersonal.get(i + 1);
						String categoria = registro.substring(0, registro.indexOf('(') - 1);
						investigador.setCategoria(categoria);
					}

					// Extraccion del nombre del investigador

					if (elemInfoPersonal.get(i).equals("NOMBRE")) {
						investigador.setNombre(elemInfoPersonal.get(i + 1));
						investigador.setNombreInvestigadorAux(investigador.getNombre());
					}

					// Extraccion de la formacion academica
					if (elemInfoPersonal.get(i).startsWith("FORMACIÓN ACADÉMICA")) {
						investigador.setNivelAcademico(elemInfoPersonal.get(i + 1));
					}

					try {
						if (estado.equals("ACTUAL")) {
							if (elemInfoPersonal.get(i).equals("UNIVERSIDAD DEL QUINDÍO")
									&& (elemInfoPersonal.get(i + 2).contains("ACTUAL") || elemInfoPersonal.get(i + 2).endsWith("DE"))) {
								pertenece = true;
								investigador.setPertenencia("INVESTIGADOR INTERNO");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (investigador.getCategoria() == null) {
					investigador.setCategoria("SIN CATEGORÍA");
				}

				if (investigador.getNivelAcademico() == null) {
					investigador.setNivelAcademico("NO ESPECIFICADO");
				}

				if (estado.equals("ACTUAL")) {
					if (pertenece == false) {
						investigador.setPertenencia("INVESTIGADOR EXTERNO");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return investigador;
	}

	/**
	 * Metodo que extrae los idiomas con los que el investigador esta familiarizado
	 * 
	 * @param elem,
	 *            Lista de elementos que contiene los idiomas del investigador
	 */
	public void extraerIdiomas(ArrayList<String> elem, Investigador investigador) {

		ArrayList<Idiomas> auxIdiomasTemp = new ArrayList<>();

		for (int i = 5; i < elem.size() - 1; i++) {
			try {
				Idiomas idioma = new Idiomas();

				idioma.setIdioma(elem.get(i));
				idioma.setHabla(elem.get(i + 1));
				idioma.setEscribe(elem.get(i + 2));
				idioma.setLee(elem.get(i + 3));
				idioma.setEntiende(elem.get(i + 4));
				idioma.setInvestigador(investigador);
				auxIdiomasTemp.add(idioma);

			} catch (Exception e) {
				Idiomas idioma = new Idiomas();

				idioma.setIdioma("N/D");
				idioma.setHabla("N/D");
				idioma.setEscribe("N/D");
				idioma.setLee("N/D");
				idioma.setEntiende("N/D");
				idioma.setInvestigador(investigador);
				auxIdiomasTemp.add(idioma);

			}
			i = i + 4;

		}
		List<Idiomas> auxIdiomas = investigador.getIdiomas();
		if (auxIdiomas == null) {
			investigador.setIdiomas(auxIdiomasTemp);
		} else {
			auxIdiomas.addAll(auxIdiomasTemp);
			investigador.setIdiomas(auxIdiomas);
		}
	}

	public void extraerLineasInvestigacionI(ArrayList<String> elem, Investigador investigador) {

		ArrayList<LineasInvestigacion> lineas = new ArrayList<>();

		for (int i = 0; i < elem.size(); i++) {

			try {
				if (elem.size() >= i && elem.get(i).contains(",") && elem.get(i + 2).equals("SI")) {

					String nomLinea = elem.get(i).substring(0, elem.get(i).length() - 1);
					nomLinea = StringUtils.stripAccents(nomLinea);
					nomLinea = nomLinea.trim();

					LineasInvestigacion lineaInvestigacion = new LineasInvestigacion();
					int pos=utils.BuscarLineasRepetidas(nomLinea);
					if (pos<0) {
						lineaInvestigacion.setNombre(nomLinea);
						utils.getLineasInvestigacion().add(lineaInvestigacion);
						lineas.add(lineaInvestigacion);
					}else {
						lineas.add(utils.getLineasInvestigacion().get(pos));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		investigador.setLineasInvestigacion(lineas);

	}

}
