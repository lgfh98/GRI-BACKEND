package co.edu.uniquindio.gri.utils;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class JSoupUtil {
	/**
	 * Método provisto por JSoup para comprobar el Status code de la respuesta que
	 * recibo al hacer la petición Codigos: 200 OK 300 Multiple Choices 301 Moved
	 * Permanently 305 Use Proxy 400 Bad Request 403 Forbidden 404 Not Found 500
	 * Internal Server Error 502 Bad Gateway 503 Service Unavailable
	 * 
	 * @param url,
	 *            el enlace de la página web a analizar.
	 * @return Status Code, el código que identifica el estado de la página.
	 */
	@SuppressWarnings("deprecation")
	public int getStatusConnectionCode(String url) {

		Response response = null;

		try {
			response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).validateTLSCertificates(false)
					.ignoreHttpErrors(true).execute();
		} catch (IOException ex) {

			return getStatusConnectionCode(url);
		}
		return response.statusCode();
	}

	/**
	 * Método que retorna un objeto de la clase Document con el contenido del HTML
	 * de la web para poder ser parseado posteriormente con JSoup
	 * 
	 * @param url,
	 *            el enlace de la página web a analizar.
	 * @return Documento con el HTML de la página en cuestión.
	 */
	@SuppressWarnings("deprecation")
	public Document getHtmlDocument(String url) {

		Document document = null;
		try {
			document = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).validateTLSCertificates(false).get();
		} catch (IOException ex) {
			return getHtmlDocument(url);
		}
		return document;
	}
}
