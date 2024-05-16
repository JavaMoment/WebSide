package com.api.app.routers;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.app.misc.IgnoreType;
import com.api.app.schemas.geolocalizaciones.DepartamentoInDbDTO;
import com.api.app.schemas.geolocalizaciones.LocalidadInDbDTO;
import com.api.app.schemas.itr.ItrInDbDTO;
import com.api.app.schemas.users.AnalistaDTO;
import com.api.app.schemas.users.AreaDTO;
import com.api.app.schemas.users.EstudianteDTO;
import com.api.app.schemas.users.TipoTutorDTO;
import com.api.app.schemas.users.TutorDTO;
import com.api.app.schemas.users.UserInDbDTO;
import com.entities.Analista;
import com.entities.Area;
import com.entities.Departamento;
import com.entities.Estudiante;
import com.entities.Itr;
import com.entities.Localidad;
import com.entities.TiposTutor;
import com.entities.Tutor;
import com.entities.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.AnalistaBeanRemote;
import com.services.AreaBeanRemote;
import com.services.DepartamentoBeanRemote;
import com.services.EstudianteBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.TiposTutorBeanRemote;
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
	@EJB
	private DepartamentoBeanRemote depaBeanRemote;
	@EJB
	private LocalidadBeanRemote	localBeanRemote;
	@EJB
	private ItrBeanRemote itrBeanRemote;
	@EJB
	private AreaBeanRemote areaBeanRemote;
	@EJB
	private TiposTutorBeanRemote tiposTutorBeanRemote;
	
	protected final ObjectMapper objectMapper = new ObjectMapper();
	
	
	@POST
	@Path("estudiante")
	public Response createStudent(EstudianteDTO newStudentDTO) {
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
	@Path("analista")
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
	@Path("tutor")
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
	@GET
	@Path("departamentos")
	public Response getDepartamentos() {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		List<Departamento> departamentos = depaBeanRemote.selectAll();
		List<DepartamentoInDbDTO> departamentosInDb = objectMapper.convertValue(departamentos, new TypeReference<List<DepartamentoInDbDTO>>(){});
		
		return Response.ok(departamentosInDb).build();
	}
	
	@GET
	@Path("localidades/{idDepartamento}")
	public Response getLocalidades(@PathParam("idDepartamento") Long idDepartamento) {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		List<Localidad> localidades = localBeanRemote.selectAllBy(idDepartamento);
		List<LocalidadInDbDTO> localidadesInDb = objectMapper.convertValue(localidades, new TypeReference<List<LocalidadInDbDTO>>(){});
		
		return Response.ok(localidadesInDb).build();
	}
	
	@GET
	@Path("itrs")
	public Response getITRs() {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		List<Itr> itrs = itrBeanRemote.selectAll();
		List<ItrInDbDTO> itrsInDb = objectMapper.convertValue(itrs, new TypeReference<List<ItrInDbDTO>>(){});
		
		return Response.ok(itrsInDb).build();
	}
	
	@GET
	@Path("areas")
	public Response getAreas() {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		List<Area> areas = areaBeanRemote.selectAll();
		List<AreaDTO> areasInDb = objectMapper.convertValue(areas, new TypeReference<List<AreaDTO>>(){});
		
		return Response.ok(areasInDb).build();
	}
	
	@GET
	@Path("tiposTutor")
	public Response getTiposTutor() {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		List<TiposTutor> tiposTutor = tiposTutorBeanRemote.selectAll();
		List<TipoTutorDTO> tiposTutorInDb = objectMapper.convertValue(tiposTutor, new TypeReference<List<TipoTutorDTO>>(){});
		
		return Response.ok(tiposTutorInDb).build();
	}

}
