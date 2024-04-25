package com.controllers.signup;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

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
import com.enums.Roles;
import com.services.AreaBeanRemote;
import com.services.DepartamentoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.TiposTutorBeanRemote;
import com.services.UsuarioBeanRemote;

@Named("signup")
@ViewScoped
public class SignupView implements Serializable {

	@EJB
	private UsuarioBeanRemote userBeanRemote;
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
	
	private List<Itr> itrs;
	private List<Departamento> depas;
	private List<Localidad> cities;
	private List<Area> areas;
	private List<String> userTypes = Arrays.asList("Analista", "Estudiante", "Tutor");
	private Genres[] genres = Genres.values();
	private List<TiposTutor> roles;
	
	private String selectedGenreName;
	private String selectedDepaName;
	private String selectedCityName;
	private String selectedItrName;
	private String selectedAreaName;
	private String selectedRolName;
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
	}
	
	public void doSignup() {
		String defaultMessage = "No se ha seleccionado: "; 
		if(selectedDepaName == null || selectedDepaName.trim().isBlank()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Ojo!", defaultMessage + "un departamento."));
		}
		else if(selectedCityName == null || selectedCityName.trim().isBlank()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Ojo!", defaultMessage + "una ciudad de residencia."));
		}
		else if(selectedItrName == null || selectedItrName.trim().isBlank()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Ojo!", defaultMessage + "el ITR al que pertenece."));
		}
		else if(selectedGenre == null || (selectedGenreName.trim().isBlank() || selectedGenreName == null)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Ojo!", defaultMessage + "su genero."));
		}
		else if(userType.equals("Tutor") && (selectedAreaName == null || selectedAreaName.trim().isBlank())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Ojo!", defaultMessage + "el area al que pertenece."));
		}
		else if(userType.equals("Tutor") && (selectedRolName == null || selectedRolName.trim().isBlank())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Ojo!", defaultMessage + "el rol que le corresponde."));
		}
		else {
			newUser.setNombreUsuario(newUser.getMailInstitucional().split("@")[0]);
			newUser.setLocalidad(cityBeanRemote.selectBy(selectedCityName));
			newUser.setItr(itrBeanRemote.selectBy(selectedItrName));
			newUser.setGenero(selectedGenre);
			switch(userType) {
				case "Analista":
					Analista newAnalyst = new Analista(newUser);
					newUser.setAnalistas(Set.of(newAnalyst));
					break;
				case "Estudiante":
					newStudent.setUsuario(newUser);
					newUser.setEstudiantes(Set.of(newStudent));
					break;
				case "Tutor":
					Tutor newTeacher = new Tutor(
							newUser,
							areaBean.selectBy(selectedAreaName),
							tiposTutorBeanRemote.selectBy(selectedRolName)
							);
					newUser.setTutores(Set.of(newTeacher));
					break;
				default:
					break;
			}
			int exitCode = userBeanRemote.create(newUser);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Felicidades!", "El usuario ha sido correctamente creado. Espere la habilitación del analista para poder ingresar."));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Oh no!", "Ha ocurrido un error mientras se intentaba crear el usuario. Por favor, intente de nuevo."));
			}
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

	public String getSelectedCityName() {
		return selectedCityName;
	}

	public void setSelectedCityName(String selectedCityName) {
		this.selectedCityName = selectedCityName;
	}

	public String getSelectedItrName() {
		return selectedItrName;
	}

	public void setSelectedItrName(String selectedItrName) {
		this.selectedItrName = selectedItrName;
	}

	public Departamento getSelectedDepa() {
		return selectedDepa;
	}

	public void setSelectedDepa(Departamento selectedDepa) {
		this.selectedDepa = selectedDepa;
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

	public String getSelectedAreaName() {
		return selectedAreaName;
	}

	public void setSelectedAreaName(String selectedAreaName) {
		this.selectedAreaName = selectedAreaName;
	}

	public List<TiposTutor> getRoles() {
		return roles;
	}

	public void setRoles(List<TiposTutor> roles) {
		this.roles = roles;
	}

	public String getSelectedRolName() {
		return selectedRolName;
	}

	public void setSelectedRolName(String selectedRolName) {
		this.selectedRolName = selectedRolName;
	}
}
