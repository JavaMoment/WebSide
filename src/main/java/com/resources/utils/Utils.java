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
