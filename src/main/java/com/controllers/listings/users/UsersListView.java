package com.controllers.listings.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.entities.Usuario;
import com.services.UsuarioBeanRemote;

/**
 * Session Bean implementation class usuarioController
 */
@Named(value = "dtUsersView")
@ViewScoped
public class UsersListView implements Serializable {

	@EJB
	private UsuarioBeanRemote usuarioBeanRemote;
	private Usuario selectedUser;
	private List<Usuario> users;
	private List<Usuario> selectedUsers;
	private List<String> userTypes;
	
	@PostConstruct
	public void init() {
		users = usuarioBeanRemote.selectAll();
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

    public void openNew() {
        this.selectedUser = new Usuario();
    }

    public void deleteProduct() {
        this.users.remove(this.selectedUser);
        this.selectedUsers.remove(this.selectedUser);
        this.selectedUser = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
    }

    public String getDeleteButtonMessage() {
        if (this.selectedUsers != null && !this.selectedUsers.isEmpty()) {
            int size = this.selectedUsers.size();
            return size > 1 ? size + " usuarios seleccionados" : "1 usuario seleccionado";
        }

        return "Eliminar";
    }

    public void deleteSelectedUsers() {
        this.users.removeAll(this.selectedUsers);
        this.selectedUsers = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("users Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
        PrimeFaces.current().executeScript("PF('dtusers').clearFilters()");
    }

	public List<String> getUserTypes() {
		userTypes = new ArrayList<>();
		userTypes.add("Analista");
		userTypes.add("Estudiante");
		userTypes.add("Tutor");
		return userTypes;
	}
}