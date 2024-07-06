package com.controllers.signup;

import java.io.Serializable;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.api.app.misc.IgnoreType;
import com.api.app.schemas.users.AnalistaDTO;
import com.api.app.schemas.users.EstudianteDTO;
import com.api.app.schemas.users.TutorDTO;
import com.entities.Analista;
import com.entities.Area;
import com.entities.Departamento;
import com.entities.Estudiante;
import com.entities.Itr;
import com.entities.Localidad;
import com.entities.TiposTutor;
import com.entities.Tutor;
import com.entities.Usuario;
import com.enums.Genres;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.AnalistaBeanRemote;
import com.services.AreaBeanRemote;
import com.services.DepartamentoBeanRemote;
import com.services.EstudianteBeanRemote;
import com.services.HttpRequestDispatcher;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.TiposTutorBeanRemote;
import com.services.TutorBeanRemote;
import com.services.UsuarioBeanRemote;

@Named("signup")
@ViewScoped
public class SignupView implements Serializable {

	@EJB
	private UsuarioBeanRemote userBeanRemote;
	@EJB
	private AnalistaBeanRemote analistaBeanRemote;
	@EJB
	private EstudianteBeanRemote estudianteBeanRemote;
	@EJB
	private TutorBeanRemote tutorBeanRemote;
	@EJB
	private ItrBeanRemote itrBeanRemote;
	@EJB
	private DepartamentoBeanRemote depaBeanRemote;
	@EJB
	private LocalidadBeanRemote cityBeanRemote;
	@EJB
	private AreaBeanRemote areaBean;
	@EJB
	private TiposTutorBeanRemote tiposTutorBeanRemote;
	
	private Usuario newUser;
	private Estudiante newStudent;
	private Tutor newTeacher;
	private Analista newAnalyst;
	
	private List<Itr> itrs;
	private List<Departamento> depas;
	private List<Localidad> cities;
	private List<Area> areas;
	private List<String> userTypes = Arrays.asList("Analista", "Estudiante", "Tutor");
	private Genres[] genres = Genres.values();
	private List<TiposTutor> roles;
	
	private String selectedGenreName;
	private String selectedDepaName;
	private Departamento selectedDepa;
	private Character selectedGenre;
	private String userType;
	private final String currentYear = String.valueOf(LocalDate.now().getYear());
	
	@PostConstruct
	public void init() {
		itrs = itrBeanRemote.selectAll();
		depas = depaBeanRemote.selectAll();
		areas = areaBean.selectAll();
		roles = tiposTutorBeanRemote.selectAll();
		
		newUser = new Usuario();
		newStudent = new Estudiante();
		newTeacher = new Tutor();
		newAnalyst = new Analista();
		newStudent.setUsuario(newUser);
		newTeacher.setUsuario(newUser);
		newAnalyst.setUsuario(newUser);
	}
	
	public void doSignup() {
		// Ruta a donde esta el post de usuarios
		int exitCode = -1;
		newUser.setNombreUsuario(newUser.getMailInstitucional().split("@")[0]);
		exitCode = userBeanRemote.create(newUser);
		Usuario userInDb = userBeanRemote.selectUserBy(newUser.getNombreUsuario());
		try {
			// Creamos el DTO para mandar como JSON dependiendo del tipo de usuario a crear.
			switch(userType.toUpperCase()) {
			case "ANALISTA":
				newAnalyst.setUsuario(userInDb);
				exitCode = analistaBeanRemote.create(newAnalyst);
				break;
			case "TUTOR":
				newTeacher.setUsuario(userInDb);
				tutorBeanRemote.create(newTeacher);
				break;
			case "ESTUDIANTE":
				newStudent.setUsuario(userInDb);
				estudianteBeanRemote.create(newStudent);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(exitCode == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Felicidades!", "El usuario ha sido correctamente creado. Espere la habilitación del analista para poder ingresar."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Oh no!", "Ha ocurrido un error mientras se intentaba crear el usuario. Por favor, intente de nuevo."));
		}
	}
	
	public void onDepartamentoChanged() {
		if((selectedDepa == null & selectedDepaName != null) || !selectedDepa.getNombre().equals(selectedDepaName)) {
			selectedDepa = depaBeanRemote.selectByName(selectedDepaName);
			cities = cityBeanRemote.selectAllByObject(selectedDepa);
			PrimeFaces.current().ajax().update("signUpScrollPane:ciudades");
		}
	}
	
	public void onGenreChanged() {
		if((selectedGenre == null & selectedGenreName != null) || !selectedGenreName.startsWith(selectedGenre.toString())) {
			selectedGenre = selectedGenreName.equals(Genres.Masculino.toString()) ? 'M' : selectedGenreName.equals(Genres.Femenino.toString()) ? 'F' : 'O';
			newUser.setGenero(selectedGenreName.equals(Genres.Masculino.toString()) ? 'M' : selectedGenreName.equals(Genres.Femenino.toString()) ? 'F' : 'O');
		}
    }
	
	public List<Itr> getItrs() {
		return itrs;
	}

	public void setItrs(List<Itr> itrs) {
		this.itrs = itrs;
	}

	public List<Departamento> getDepas() {
		return depas;
	}

	public void setDepas(List<Departamento> depas) {
		this.depas = depas;
	}

	public List<Localidad> getCities() {
		return cities;
	}

	public void setCities(List<Localidad> cities) {
		this.cities = cities;
	}

	public List<String> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<String> userTypes) {
		this.userTypes = userTypes;
	}

	public Usuario getNewUser() {
		return newUser;
	}

	public void setNewUser(Usuario newUser) {
		this.newUser = newUser;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Genres[] getGenres() {
		return genres;
	}

	public void setGenres(Genres[] genres) {
		this.genres = genres;
	}

	public String getSelectedGenreName() {
		return selectedGenreName;
	}

	public void setSelectedGenreName(String selectedGenreName) {
		this.selectedGenreName = selectedGenreName;
	}

	public String getSelectedDepaName() {
		return selectedDepaName;
	}

	public void setSelectedDepaName(String selectedDepaName) {
		this.selectedDepaName = selectedDepaName;
	}

	public Departamento getSelectedDepa() {
		return selectedDepa;
	}

	public Character getSelectedGenre() {
		return selectedGenre;
	}

	public void setSelectedGenre(Character selectedGenre) {
		this.selectedGenre = selectedGenre;
	}

	public Estudiante getNewStudent() {
		return newStudent;
	}

	public void setNewStudent(Estudiante newStudent) {
		this.newStudent = newStudent;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public List<TiposTutor> getRoles() {
		return roles;
	}

	public void setRoles(List<TiposTutor> roles) {
		this.roles = roles;
	}

	public Analista getNewAnalyst() {
		return newAnalyst;
	}

	public void setNewAnalyst(Analista newAnalyst) {
		this.newAnalyst = newAnalyst;
	}

	public Tutor getNewTeacher() {
		return newTeacher;
	}

	public void setNewTeacher(Tutor newTeacher) {
		this.newTeacher = newTeacher;
	}
}
