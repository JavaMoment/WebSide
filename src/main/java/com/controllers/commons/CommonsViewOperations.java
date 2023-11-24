package com.controllers.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.entities.Departamento;
import com.entities.Itr;
import com.entities.Localidad;
import com.entities.Usuario;
import com.services.DepartamentoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.UsuarioBeanRemote;
import java.io.Serializable;

@Named("commonsOperations")
@RequestScoped
public class CommonsViewOperations implements Serializable {
	
	@EJB
	private UsuarioBeanRemote usuarioBeanRemote;
	@EJB
	private ItrBeanRemote itrBeanRemote;
	@EJB
	private DepartamentoBeanRemote depaBeanRemote;
	@EJB
	private LocalidadBeanRemote cityBeanRemote;
	
    private List<Usuario> users;
	private List<Itr> itrs;
	private List<String> userTypes = Arrays.asList("Analista", "Estudiante", "Tutor");
	private String[] usersStatus = {"Activo", "Inactivo"};
	private List<Departamento> depas;
	private List<Localidad> cities;
	private Map<String, Departamento> nameDepa = new HashMap<>();
	private Map<String, List<Localidad>> depaNameLocalidad = new HashMap<>();
	private Map<String, Localidad> nameCity = new HashMap<>();
	
    @PostConstruct
	public void init() {
		users = usuarioBeanRemote.selectAll();
		itrs = itrBeanRemote.selectAll();
		depas = depaBeanRemote.selectAll();
		
		for(Departamento d : depas) {
			nameDepa.put(d.getNombre(), d);
			List<Localidad> relatedCities = cityBeanRemote.selectAllByObject(d);
			depaNameLocalidad.put(d.getNombre(), relatedCities);
			for(Localidad l : relatedCities) {
				nameCity.put(l.getNombre(), l);
			}
		}
	}
    
    public void onDepartamentoChanged(Usuario user) {
    	Departamento departamento = user.getDepartamento();
    	if(departamento != null && !"".equals(departamento.toString())) {
			cities = cityBeanRemote.selectAllByObject(departamento);
		} else {
			cities = null;
		}
//    	PrimeFaces.current().ajax().update("dialogs:manage-user-content");
    }

	public List<Usuario> getUsers() {
		return users;
	}

	public void setUsers(List<Usuario> users) {
		this.users = users;
	}

	public List<Itr> getItrs() {
		return itrs;
	}

	public void setItrs(List<Itr> itrs) {
		this.itrs = itrs;
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
    

}
