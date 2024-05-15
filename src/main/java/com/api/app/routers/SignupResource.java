package com.api.app.routers;

import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.app.misc.IgnoreType;
import com.api.app.schemas.users.AnalistaDTO;
import com.api.app.schemas.users.EstudianteDTO;
import com.api.app.schemas.users.TutorDTO;
import com.api.app.schemas.users.UserInDbDTO;
import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Tutor;
import com.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.AnalistaBeanRemote;
import com.services.EstudianteBeanRemote;
import com.services.TutorBeanRemote;
import com.services.UsuarioBeanRemote;

@Path("signup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SignupResource {
	
	@EJB
	private UsuarioBeanRemote userService;
	@EJB
	private EstudianteBeanRemote studentService;
	@EJB
	private AnalistaBeanRemote analystService;
	@EJB
	private TutorBeanRemote teacherService;
	
	@POST
	@Path("/estudiante")
	public Response createStudent(EstudianteDTO newStudentDTO) {
		System.out.println(newStudentDTO);
		ObjectMapper om = new ObjectMapper();
		Estudiante newStudent = om.convertValue(newStudentDTO, Estudiante.class);
		Usuario newUser = newStudent.getUsuario();
		newUser.setNombreUsuario(newStudentDTO.getUsuario().getMailInstitucional().split("@")[0]);
		int exitCode = userService.create(newUser); 
		exitCode = studentService.create(newStudent);
		if(exitCode != 0) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		newUser = userService.selectUserBy(newUser.getNombreUsuario());
		
		om.addMixIn(Set.class, IgnoreType.class);
		UserInDbDTO userInDbDTO = om.convertValue(newUser, UserInDbDTO.class); 
		return Response.status(Response.Status.CREATED).entity(userInDbDTO).build();
	}
	
	@POST
	@Path("/analista")
	public Response createAnalyst(AnalistaDTO newAnalistaDTO) {
		ObjectMapper om = new ObjectMapper();
		Analista newAnalyst = om.convertValue(newAnalistaDTO, Analista.class);
		Usuario newUser = newAnalyst.getUsuario();
		newUser.setNombreUsuario(newAnalistaDTO.getUsuario().getMailInstitucional().split("@")[0]);
		int exitCode = userService.create(newUser); 
		exitCode = analystService.create(newAnalyst);
		if(exitCode != 0) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		newUser = userService.selectUserBy(newUser.getNombreUsuario());
		
		om.addMixIn(Set.class, IgnoreType.class);
		UserInDbDTO userInDbDTO = om.convertValue(newUser, UserInDbDTO.class); 
		return Response.status(Response.Status.CREATED).entity(userInDbDTO).build();
	}

	@POST
	@Path("/tutor")
	public Response createTeacher(TutorDTO newTutorDTO) {
		ObjectMapper om = new ObjectMapper();
		Tutor newTeacher = om.convertValue(newTutorDTO, Tutor.class);
		Usuario newUser = newTeacher.getUsuario();
		newUser.setNombreUsuario(newTutorDTO.getUsuario().getMailInstitucional().split("@")[0]);
		int exitCode = userService.create(newUser); 
		exitCode = teacherService.create(newTeacher);
		if(exitCode != 0) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		newUser = userService.selectUserBy(newUser.getNombreUsuario());
		
		om.addMixIn(Set.class, IgnoreType.class);
		UserInDbDTO userInDbDTO = om.convertValue(newUser, UserInDbDTO.class); 
		return Response.status(Response.Status.CREATED).entity(userInDbDTO).build();
	}
}
