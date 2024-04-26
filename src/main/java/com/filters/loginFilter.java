package com.filters;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.entities.Usuario;
import com.services.JWTservice;

public class loginFilter implements Filter {
	FilterConfig fc;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		fc = filterConfig;
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        String loginURL = request.getContextPath() + "/";

		String token = (String) session.getAttribute("token");
	    Usuario user = (Usuario) session.getAttribute("userLogged");
        boolean loggedIn = (session != null) && (session.getAttribute("userLogged") != null); // ¿El usuario inicio sesion?
        boolean loginRequest = request.getRequestURI().equals(loginURL); // ¿La pagina solicitada es la del login? loggin esta mapeada como /WebSide/ == ContextPath
        boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER); // ¿Se solicito un recurso (.css, .js, etc)?

        if (!JWTservice.validateToken(token,user)) {
				response.sendRedirect(loginURL);
			  }
    
        if (loggedIn || loginRequest || resourceRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURL);
        }
	}

}
