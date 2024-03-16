package com.filters;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wildfly.security.http.HttpServerResponse;

import com.entities.Usuario;
import com.resources.utils.Utils;
import com.services.JWTservice;

public class loginFilter implements Filter {
	FilterConfig fc;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		fc = filterConfig;
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		if (!req.getRequestURL().toString().contains("login") && !req.getRequestURL().toString().contains(".css")
				&& !req.getRequestURL().toString().contains(".js")) {
			String token = (String) session.getAttribute("token");
			Usuario user = (Usuario) session.getAttribute("userLogged");
			if (!JWTservice.validateToken(token,user)) {
				resp.sendRedirect("/WebSide/views/static/login/login.xhtml");
			}

		}
		chain.doFilter(req, resp);

	}

}
