package com.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.entities.Usuario;
import com.services.UsuarioBeanRemote;

import java.io.Serializable;

@Named("index")
@ViewScoped
public class index implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private String password;
    @EJB
    private UsuarioBeanRemote ubr;

    @PostConstruct
    public void init() {
        Usuario u = ubr.selectAll().get(1);
        name = u.getNombre1();
        email = u.getMailPersonal();
        password = u.getContrasenia();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

} 