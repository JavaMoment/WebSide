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

import org.jboss.security.config.IdentityTrustInfo;

import com.services.AreaBeanRemote;
import com.services.DepartamentoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.TiposTutorBeanRemote;
import com.entities.Departamento;
import com.entities.Itr;
import com.entities.Localidad;
import com.entities.Area;
import com.entities.TiposTutor;



@Path("/signup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class SignUpService {
	
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
	
	@GET
	@Path("/itr")
	public Response getITRs() {
		List<Itr> itrs = itrBeanRemote.selectAll();
		List<Map<String, Object>> itrsInfo = new ArrayList<>();
		for (Itr itr : itrs){
            Map<String, Object> itrInfo = new HashMap<>();
            itrInfo.put("id", itr.getIdItr());
            itrInfo.put("nombre", itr.getNombre());
            itrsInfo.add(itrInfo);
		}
		return Response.ok(itrsInfo).build();
	}
	
	@GET
	@Path("/areas")
	public Response getAreas() {
		List<Area> areas = areaBeanRemote.selectAll();
		List<Map<String, Object>> areasInfo = new ArrayList<>();
		for (Area area : areas){
            Map<String, Object> areaInfo = new HashMap<>();
            areaInfo.put("id", area.getIdArea());
            areaInfo.put("nombre", area.getNombre());
            areasInfo.add(areaInfo);
		}
		return Response.ok(areasInfo).build();
	}
	
	@GET
	@Path("/tiposTutor")
	public Response getTiposTutor() {
		List<TiposTutor> tiposTutor = tiposTutorBeanRemote.selectAll();
		List<Map<String, Object>> tiposInfo = new ArrayList<>();
		for (TiposTutor tipo : tiposTutor){
            Map<String, Object> tipoInfo = new HashMap<>();
            tipoInfo.put("id", tipo.getIdTipoTutor());
            tipoInfo.put("nombre", tipo.getNombre());
            tiposInfo.add(tipoInfo);
		}
		return Response.ok(tiposInfo).build();
	}
	
	
	
	
}
