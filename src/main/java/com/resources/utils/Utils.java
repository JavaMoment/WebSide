package com.resources.utils;

// Importación de clases necesarias
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import com.entities.Usuario;

import java.lang.StringBuilder;

// Declaración de la clase Utils
public class Utils {
	   // Constante para la ruta base para EJBs
    private static final String baseRoute = "ejb:TecnicaturaUtec/ServerSide/";
    
    // Método genérico para obtener un bean EJB
    public static <T> T getBean(Class<T> expectedBean) {
        // Inicializar propiedades para JNDI
        final Properties env = new Properties();
        final String sep = "!"; // Separador usado en la ruta JNDI
        try {
            // Intentar cargar el archivo de propiedades JNDI
            try(InputStream jndi = Utils.class.getClassLoader().getResourceAsStream("com/resources/configs/jndi.properties")) {
                env.load(jndi); // Cargar propiedades desde el archivo
            }
            // Crear un contexto inicial con las propiedades JNDI
            Context ctx = new InitialContext(env);
            
            // Construir la ruta JNDI para el bean
            String route = new StringBuilder(baseRoute)
                    .append(expectedBean.getSimpleName().replace("Remote", "")) // Añadir el nombre simple del bean esperado
                    .append(sep) // Añadir el separador
                    .append(expectedBean.getName()) // Añadir el nombre completo del bean
                    .toString(); // Convertir a String
            
            // Buscar y retornar el bean usando la ruta JNDI
            return expectedBean.cast(ctx.lookup(route));
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir la traza de la excepción IOException
            return null; // Retornar null en caso de IOException
        } catch (NamingException e) {
            e.printStackTrace(); // Imprimir la traza de la excepción NamingException
            return null; // Retornar null en caso de NamingException
        }
    }
    
    public static Usuario checkUser() {
    	
    	FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("userLogged");
		if (usuario == null)
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/WebSide/views/static/login/login.xhtml");
			} catch (IOException e) { 
				e.printStackTrace();
			}
    	return usuario;
    	
    }
}
