package com.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.services.DepartamentoBeanRemote;
import com.services.LocalidadBeanRemote;
import com.entities.Departamento;
import com.entities.Localidad;



@Path("/signup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class SignUpService {
	
	@EJB
	private DepartamentoBeanRemote depaBeanRemote;
	
	@EJB
	private LocalidadBeanRemote	localBeanRemote;
	
	@GET
	public Response getDepartamentos() {
		List<Departamento> departamentosAll = depaBeanRemote.selectAll();
		List<Map<String, Object>> depsInfo = new ArrayList<>();
		for (Departamento dep : departamentosAll){
            Map<String, Object> depInfo = new HashMap<>();
            depInfo.put("id", dep.getIdDepartamento());
            depInfo.put("nombre", dep.getNombre());
            depsInfo.add(depInfo);
		}
		return Response.ok(depsInfo).build();
	}
	
	@GET
	@Path("/{departamento}")
	public Response getLocalidades(@PathParam("departamento") Long departamento) {
		List<Localidad> localidadesAll = localBeanRemote.selectAllBy(departamento);
		List<Map<String, Object>> localsInfo = new ArrayList<>();
		for (Localidad loc : localidadesAll){
            Map<String, Object> localInfo = new HashMap<>();
            localInfo.put("id", loc.getIdLocalidad());
            localInfo.put("nombre", loc.getNombre());
            localsInfo.add(localInfo);
		}
		return Response.ok(localsInfo).build();
	}
	
}
