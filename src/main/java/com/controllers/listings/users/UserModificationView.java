package com.controllers.listings.users;

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
import com.services.AnalistaBeanRemote;
import com.services.AreaBeanRemote;
import com.services.DepartamentoBeanRemote;
import com.services.EstudianteBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.TiposTutorBeanRemote;
import com.services.TutorBeanRemote;
import com.services.UsuarioBeanRemote;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Named(value = "userModification")
@ViewScoped
public class UserModificationView implements Serializable {
	
	@EJB
	private UsuarioBeanRemote usuarioBeanRemote;
	@EJB
	private EstudianteBeanRemote studentBean;
	@EJB
	private TutorBeanRemote teacherBean;
	@EJB
	private AnalistaBeanRemote analystBean;
	@EJB
	private ItrBeanRemote itrBeanRemote;
	@EJB
	private LocalidadBeanRemote cityBeanRemote;
	@EJB
	private AreaBeanRemote areaBean;
	@EJB
	private DepartamentoBeanRemote depaBeanRemote;
	@EJB
	private TiposTutorBeanRemote tiposTutorBeanRemote;
	
	private Usuario selectedUser;
	private Estudiante selectedStudent;
	private Tutor selectedTeacher;
	private Analista selectedAnalyst;
	
	private List<String> userTypes = Arrays.asList("Analista", "Estudiante", "Tutor");
	private String[] usersStatus = {"Activo", "Inactivo"};
	private List<Itr> itrs;
	private List<Departamento> depas;
	private List<Localidad> cities;
	private List<Area> areas;
	private Genres[] genres = Genres.values();
	private List<TiposTutor> roles;
	
	private String selectedDepaName;
	private String selectedCityName;
	private String selectedItrName;
	private String selectedGenreName;
	private String selectedAreaName;
	private String selectedRolName;
	private String selectedGeneracion;
	private Departamento selectedDepa;
	private Localidad selectedCity;
	private Itr selectedItr;
	private Character selectedGenre;
	private String newPwd;
	private final String currentYear = String.valueOf(LocalDate.now().getYear());
	
	@PostConstruct
	public void init() {
		itrs = itrBeanRemote.selectAll();
		areas = areaBean.selectAll();
		depas = depaBeanRemote.selectAll();
		roles = tiposTutorBeanRemote.selectAll();
	}
	
	public void onDepartamentoChanged() {
		if((selectedDepa == null & selectedDepaName != null) || !selectedDepa.getNombre().equals(selectedDepaName)) {
		        selectedDepa = depaBeanRemote.selectByName(selectedDepaName);
		        cities = cityBeanRemote.selectAllByObject(selectedDepa);
		        PrimeFaces.current().ajax().update("dialogs:ciudades");
		}
	}
	
	public void onCityChanged() {
		if((selectedCity == null & selectedCityName != null) || !selectedCity.getNombre().equals(selectedCityName)) {
			selectedCity = cityBeanRemote.selectBy(selectedCityName);
		}
    }
	
	public void onItrChanged() {
		if((selectedItr == null & selectedItrName != null) || !selectedItr.getNombre().equals(selectedItrName)) {
			selectedItr = itrBeanRemote.selectBy(selectedItrName);
		}
    }

	public void onGenreChanged() {
		if((selectedGenre == null & selectedGenreName != null) || !selectedGenreName.startsWith(selectedGenre.toString())) {
			selectedGenre = selectedGenreName.equals(Genres.Masculino.toString()) ? 'M' : selectedGenreName.equals(Genres.Femenino.toString()) ? 'F' : 'O';
		}
    }
	
	public void doUpdateUser() {
		if(selectedCityName != null && !selectedCityName.equals(selectedUser.getLocalidad().getNombre())) {
			selectedUser.setLocalidad(selectedCity);
			selectedCity = null;
		}
		if(selectedItrName != null && !selectedItrName.equals(selectedUser.getItr().getNombre())) {
			selectedUser.setItr(selectedItr);
			selectedItr = null;
		}
		if(selectedGenre != null && !selectedGenre.equals(selectedUser.getGenero())) {
			selectedUser.setGenero(selectedGenre);
			selectedGenre = null;
		}
		if(newPwd != null && !newPwd.trim().isBlank()) {
			selectedUser.setContrasenia(newPwd);
			newPwd = null;
		}
		
		final String defaultMessage = "No se ha seleccionado: ";
		if(selectedUser.getTipoUsuario().equals("Tutor") && (selectedAreaName == null || selectedAreaName.trim().isBlank())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Ojo!", defaultMessage + "el area al que pertenece."));
		}
		else if(selectedUser.getTipoUsuario().equals("Tutor") && (selectedRolName == null || selectedRolName.trim().isBlank())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Ojo!", defaultMessage + "el rol que le corresponde."));
		} else {
			String username = selectedUser.getNombreUsuario();
			
			Analista analyst = analystBean.selectUserBy(username);
			Tutor teacher = teacherBean.selectUserBy(username);
			Estudiante student = studentBean.selectUserBy(username);
			
			selectedUser.removeAnalista(analyst);
			selectedUser.removeEstudiante(student);
			selectedUser.removeTutor(teacher);
			
			switch(selectedUser.getTipoUsuario()) {
			case "Estudiante":
				if(student == null) {
					student = new Estudiante(selectedUser, selectedGeneracion);
				}
				selectedUser.addEstudiante(student);
				break;
			case "Tutor":
				if(teacher == null) {
					teacher = new Tutor(
							selectedUser,
							areaBean.selectBy(selectedAreaName),
							tiposTutorBeanRemote.selectBy(selectedRolName)
							);
				}
				selectedUser.addTutor(teacher);
				break;
			case "Analista":
				if(analyst == null) {
					analyst = new Analista(selectedUser);
				}
				
				selectedUser.addAnalista(analyst);
				break;
			default:
				break;
			}
			
			int exitCode = usuarioBeanRemote.update(selectedUser);
			if(exitCode != 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Que mal..", "El usuario " + username + " no ha sido correctamente modificado."));
				PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Que bien!", "El usuario " + username + " ha sido correctamente modificado."));
				PrimeFaces.current().ajax().update("dialogs:messages", "form:dt-users");
				PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
			}
		}
	}
	
	public UsuarioBeanRemote getUsuarioBeanRemote() {
		return usuarioBeanRemote;
	}
	public void setUsuarioBeanRemote(UsuarioBeanRemote usuarioBeanRemote) {
		this.usuarioBeanRemote = usuarioBeanRemote;
	}
	public Usuario getSelectedUser() {
		return selectedUser;
	}
	public void setSelectedUser(Usuario selectedUser) {
		this.selectedUser = selectedUser;
	}
	public List<String> getUserTypes() {
		return userTypes;
	}
	public void setUserTypes(List<String> userTypes) {
		this.userTypes = userTypes;
	}
	public String[] getUsersStatus() {
		return usersStatus;
	}
	public void setUsersStatus(String[] usersStatus) {
		this.usersStatus = usersStatus;
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

	public String getSelectedCityName() {
		return selectedCityName;
	}

	public void setSelectedCityName(String selectedCityName) {
		this.selectedCityName = selectedCityName;
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

	public void setSelectedDepa(Departamento selectedDepa) {
		this.selectedDepa = selectedDepa;
	}

	public Genres[] getGenres() {
		return genres;
	}

	public void setGenres(Genres[] genres) {
		this.genres = genres;
	}

	public String getSelectedItrName() {
		return selectedItrName;
	}

	public void setSelectedItrName(String selectedItrName) {
		this.selectedItrName = selectedItrName;
	}

	public String getSelectedGenreName() {
		return selectedGenreName;
	}

	public void setSelectedGenreName(String selectedGenreName) {
		this.selectedGenreName = selectedGenreName;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public Estudiante getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Estudiante selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public Analista getSelectedAnalyst() {
		return selectedAnalyst;
	}

	public void setSelectedAnalyst(Analista selectedAnalyst) {
		this.selectedAnalyst = selectedAnalyst;
	}

	public Tutor getSelectedTeacher() {
		return selectedTeacher;
	}

	public void setSelectedTeacher(Tutor selectedTeacher) {
		this.selectedTeacher = selectedTeacher;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public String getSelectedAreaName() {
		return selectedAreaName;
	}

	public void setSelectedAreaName(String selectedAreaName) {
		this.selectedAreaName = selectedAreaName;
	}

	public String getSelectedRolName() {
		return selectedRolName;
	}

	public void setSelectedRolName(String selectedRolName) {
		this.selectedRolName = selectedRolName;
	}

	public List<TiposTutor> getRoles() {
		return roles;
	}

	public void setRoles(List<TiposTutor> roles) {
		this.roles = roles;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public String getSelectedGeneracion() {
		return selectedGeneracion;
	}

	public void setSelectedGeneracion(String selectedGeneracion) {
		this.selectedGeneracion = selectedGeneracion;
	}
}
