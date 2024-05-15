package com.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.api.app.schemas.users.UserCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpRequestDispatcher {
	
	protected final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_1_1)
			.build();
	private final Properties env = new Properties();
	private Context ctx;
	private String baseUri;
	protected ObjectMapper objectMapper = new ObjectMapper();
	
	public HttpRequestDispatcher() {
		try {
            // Intentar cargar el archivo de propiedades de la API (url, version, etc)
            try(InputStream api = HttpRequestDispatcher.class.getClassLoader().getResourceAsStream("api.properties")) {
                env.load(api);
            }
            this.ctx = new InitialContext(env);
            baseUri = new StringBuilder()
            		.append(env.get("protocol"))
            		.append("://")
            		.append(env.get("host"))
            		.append(":")
            		.append(env.get("port"))
            		.append("/")
            		.append(env.get("appName"))
            		.append("/")
            		.append("api")
            		.append("/")
            		.append(env.get("version"))
            		.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
	}
	
	// TODO: Get parametrizados ej: url=*WebSide/api/v1/departamentos/{id_departamento}
	
	/**
	 * Envía una solicitud GET a un endpoint especificado.
	 *
	 * @param context 				  El contexto del punto final, una lista ordenada de cómo llegar a la URI
	 *                				  (por ejemplo, ["signup", "estudiante"] para ~/signup/estudiante/).
	 * @return HttpResponse 		  La respuesta recibida del servidor.
	 * @throws IOException            Si ocurre un error de entrada o salida durante la comunicación con el servidor.
	 * @throws InterruptedException   Si la ejecución del hilo es interrumpida mientras espera la respuesta del servidor.
	 */
	public HttpResponse sendGet(ArrayList<String> context) throws IOException, InterruptedException {
		context.add(0, baseUri);
		String endpointUri = String.join("/", context);  
		System.out.println(endpointUri);
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(endpointUri))
				.build();
		
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		
		System.out.println(response.statusCode());
		
		System.out.println(response.body());
		return response;
	}
	
	/**
	 * Envía una solicitud POST a un endpoint especificado con un cuerpo de entidad en formato JSON.
	 *
	 * @param context      			El contexto del endpoint, una lista ordenada de como llegar a la uri (ej: [signup, estudiante] => ~/signup/estudiante/).
	 * @param entityMapped 			El cuerpo de la solicitud en forma de un map, que se convertirá a JSON.
	 * @return HttpResponse 		La respuesta recibida del servidor.
	 * @throws IOException          Si ocurre un error de entrada o salida durante la comunicación con el servidor.
	 * @throws InterruptedException Si la ejecución del hilo es interrumpida mientras espera la respuesta del servidor.
	 */
	public HttpResponse sendPost(ArrayList<String> context, Map<String, Object> entityMapped) throws IOException, InterruptedException {
		context.add(0, baseUri);
		String endpointUri = String.join("/", context);
		System.out.println(endpointUri);
		String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entityMapped);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endpointUri))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
	}
	
	public Properties getEnv() {
		return this.env;
	}
	
	public Context getCtx() {
		return this.ctx;
	}
	
	public String getBaseUri() {
		return this.baseUri;
	}
	
}
