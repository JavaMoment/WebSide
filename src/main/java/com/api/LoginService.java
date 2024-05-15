package com.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.entities.Usuario;
import com.services.JWTservice;
import com.services.UsuarioBeanRemote;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginService {

    @EJB
    private UsuarioBeanRemote userBeanRemote; 
    @Context
    private HttpServletRequest request; 

    public static class LoginRequest {
        public String emailUtec;
        public String password;
    }

    @POST
    public Response doLogin(LoginRequest requestBody) {
        Usuario user = userBeanRemote.selectUserBy(requestBody.emailUtec); // buscamos el usuario por su correo
        if (user != null && user.isValidUser(requestBody.password)) { // si el usuario es válido
            String jwt = JWTservice.generateToken(user); // generamos el token jwt
            HttpSession session = request.getSession(true); // creamos la sesión
            session.setAttribute("userLogged", user); // guardamos el usuario en la sesión
            session.setAttribute("token", jwt); // guardamos el token en la sesión

            return Response.ok()
                .entity(new LoginResponse("¡Bienvenido!", jwt)) // respondemos con un mensaje de bienvenida y el token
                .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new LoginResponse("¡Oh no! Oh no no no", "El usuario o contraseña no es correcto")) // respondemos con un error si las credenciales son incorrectas
                .build();
        }
    }

    public static class LoginResponse {
        public String message;
        public String token;

        public LoginResponse(String message, String token) {
            this.message = message; // mensaje usuario
            this.token = token; // el token jwt
        }
    }
}
