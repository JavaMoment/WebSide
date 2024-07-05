package com.controllers.listings.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

/**
 * Session Bean implementation class usuarioController
 */
@Named(value = "dtUsersView")
@ViewScoped
public class UsersListView implements Serializable {

	@EJB
	private UsuarioBeanRemote usuarioBeanRemote;
	@EJB
	private ItrBeanRemote itrBeanRemote;
	private Usuario selectedUser;
	private List<Usuario> users;
	private List<Usuario> selectedUsers;
	private List<Itr> itrs;
	private List<String> userTypes = Arrays.asList("Analista", "Estudiante", "Tutor");
	private String[] usersStatus = {"Activo", "Inactivo"};
	
	@PostConstruct
	public void init() {
		users = usuarioBeanRemote.selectAll();
		itrs = itrBeanRemote.selectAll();
	}
	
	public void onToggleSwitchChangeActive() {
	    Usuario user = getselectedUser(); // Asegúrate de que el usuario seleccionado se esté pasando correctamente.
	    String username = user.getNombreUsuario();
	    int exitCode;
	    if (user.isActive()) {
	        user.setActivo((byte) 0); // Cambia el estado
	        exitCode = usuarioBeanRemote.logicalDeleteByUsername(username);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El usuario " + username + " ha sido correctamente dado de baja."));
	    } else {
	        user.setActivo((byte) 1); // Cambia el estado
	        exitCode = usuarioBeanRemote.activeUserBy(username);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El usuario " + username + " ha sido correctamente activado."));
	    }
	    if (exitCode != 0) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ha ocurrido un error y el estado del usuario no ha podido ser modificado."));
	    }
	    PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
	}
	
	public void revertUserActiveState() {
	    if (selectedUser != null) {
	        // esto simplemente revertirá el cambio visual ya que el cambio no debería haber sido guardado aún
	        selectedUser.setActivo(selectedUser.isActive() ? (byte) 0 : (byte) 1);
	        PrimeFaces.current().ajax().update("form:dt-users"); // Actualiza los componentes necesarios en la UI
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "Acción cancelada con éxito."));
	        System.out.println("RevertUserActiveState funciona");


	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se ha seleccionado ningún usuario."));
	    }
	}
	
	public List<Usuario> getUsers() {
		return users;
	}
	
	public Usuario getselectedUser() {
        return selectedUser;
    }

    public void setselectedUser(Usuario selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Usuario> getSelectedUsers() {
        return selectedUsers;
    }

    public void setselectedUsers(List<Usuario> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

	public List<String> getUserTypes() {
		return userTypes;
	}

	public List<Itr> getItrs() {
		return itrs;
	}

	public void setItrs(List<Itr> itrs) {
		this.itrs = itrs;
	}

	public String[] getUsersStatus() {
		return usersStatus;
	}

	public void setUsersStatus(String[] usersStatus) {
		this.usersStatus = usersStatus;
	}
}