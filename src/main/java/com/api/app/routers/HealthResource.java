package com.api.app.routers;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.app.schemas.users.UserCreateDTO;

@Path("health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

	@GET
	public Response checkHealth() {
		Map<String, String> healthStatus = new HashMap<>();
		healthStatus.put("Estoy", "bien");
		return Response.ok(healthStatus).build();
	}
	
}
