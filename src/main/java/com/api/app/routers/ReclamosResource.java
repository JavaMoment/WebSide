package com.api.app.routers;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.app.misc.IgnoreType;
import com.api.app.misc.MediaTypes;
import com.api.app.patchImpl.ObjectPatch;

import com.api.app.schemas.claims.ClaimsCreateDTO;
import com.api.app.schemas.claims.StatusReclamoDTO;
import com.api.app.schemas.events.EventInDbDTO;
import com.api.app.schemas.claims.ClaimInDbDTO;
import com.entities.Evento;
import com.entities.Reclamo;
import com.entities.StatusReclamo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.EventoBeanRemote;
import com.services.ReclamoBeanRemote;
import com.services.StatusReclamoBeanRemote;

@Path("reclamos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReclamosResource {

	@EJB
	protected ReclamoBeanRemote reclamoService;
	@EJB
	protected StatusReclamoBeanRemote statusReclamoService;
	@EJB
	protected EventoBeanRemote eventoService;
	
	protected final ObjectMapper objectMapper = new ObjectMapper();
	
	@GET
	public Response getClaims() {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		List<Reclamo> claims = reclamoService.selectAll();
		List<ClaimInDbDTO> claimsInDb = objectMapper.convertValue(claims, new TypeReference<List<ClaimInDbDTO>>(){});
		
		return Response.ok(claimsInDb).build();
	}
	
	@GET
	@Path("estudiante/{username}")
	public Response getClaimsBy(@PathParam("username") String username) {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		List<Reclamo> claims = reclamoService.selectAllBy(username);
		List<ClaimInDbDTO> claimsInDb = objectMapper.convertValue(claims, new TypeReference<List<ClaimInDbDTO>>(){});
		
		return Response.ok(claimsInDb).build();
	}
	
	@PATCH
	@Path("{idReclamo}")
	@Consumes({MediaTypes.APPLICATION_JSON_PATCH, MediaType.APPLICATION_JSON})
	public Response partialUpdateReclamo(@PathParam("idReclamo") Long idReclamo, ObjectPatch patch) {
		Reclamo r = reclamoService.selectById(idReclamo);
		if(r == null) {
			throw new NotFoundException();
		}
		
		// Settear lo atributos modificados (van a ser detectados en tiempo de ejecución)
		patch.apply(r);
		
		// Actualizar en BDD
		int exitCode = reclamoService.update(r);
		if(exitCode != 0) {
			return Response.status(Response.Status.NOT_MODIFIED).build();
		}
		
		return Response.ok(r).build();
	}

	@POST
	@Path("new")
	public Response createClaim(ClaimsCreateDTO newClaimDTO) {
		ObjectMapper om = new ObjectMapper();
		Reclamo newClaim = om.convertValue(newClaimDTO, Reclamo.class);
		int exitCode = reclamoService.create(newClaim); 

		if(exitCode != 0) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		
		om.addMixIn(Set.class, IgnoreType.class);
		ClaimInDbDTO claimInDbDTO = om.convertValue(newClaim, ClaimInDbDTO.class); 
		return Response.status(Response.Status.CREATED).entity(claimInDbDTO).build();
	}
	
	@GET
	@Path("statuses")
	public Response getStatusReclamo() {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		List<StatusReclamo> statusReclamo = statusReclamoService.selectAll();
		List<StatusReclamoDTO> status = objectMapper.convertValue(statusReclamo, new TypeReference<List<StatusReclamoDTO>>(){});
		
		return Response.ok(status).build();
	}
	
	@GET
	@Path("{idReclamo}")
	public Response getClaimBy(@PathParam("idReclamo") Long idReclamo) {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);
		
		Reclamo claim = reclamoService.selectById(idReclamo);
		ClaimInDbDTO claimInDb = objectMapper.convertValue(claim, ClaimInDbDTO.class);
		
		return Response.ok(claimInDb).build();
	}
	
	@GET
	@Path("eventos")
	public Response getEvents() {
		objectMapper.addMixIn(Set.class, IgnoreType.class);
		objectMapper.addMixIn(List.class, IgnoreType.class);

		List<Evento> eventos =  eventoService.selectAll();
		List<EventInDbDTO> eventosInDb = objectMapper.convertValue(eventos, new TypeReference<List<EventInDbDTO>>() {});


		return Response.ok(eventosInDb).build();
	}

}
