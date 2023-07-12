package com.distribuida.rest;

import com.distribuida.db.Persona;
import com.distribuida.repo.PersonaRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/persona")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class PersonaRest {
    @Inject
    PersonaRepository repo;
    Logger logger= LoggerFactory.getLogger(PersonaRest.class);

    @GET

    public List<Persona> listarPersonas() {
        return Persona.listAll();
    }

    @POST
    //@Path("/crear")

    public Response crearPersona(Persona persona) {

        repo.persist(persona);
        return Response.status(Response.Status.CREATED)
                .entity("Persona creada exitosamente")
                .build();
    }

    @PUT
    @Path("/{id}")

    public Response actualizarPersona(@PathParam("id") Long id, Persona personaActualizada) {
        Persona personaExistente = repo.findById(id);

        if (personaExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Persona no encontrada")
                    .build();
        }

        personaExistente.setName(personaActualizada.getName());


        personaExistente.persist();

        return Response.status(Response.Status.OK)
                .entity("Persona actualizada exitosamente")
                .build();
    }

    @GET
    @Path("/{id}")

    public Response obtenerPersonaPorId(@PathParam("id") Long id) {
        logger.debug("consultando persona con el id={}",id);
        var persona = repo.findById(id);

        if (persona == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Persona no encontrada")
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity(persona)
                .build();
    }

    @DELETE
    @Path("/{id}")

    public Response eliminarPersona(@PathParam("id") Long id) {
        logger.debug("eliminando persona con el id={}",id);
        Persona persona = repo.findById(id);
        if (persona == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Persona no encontrada")
                    .build();
        }

        persona.delete();

        return Response.status(Response.Status.OK)
                .entity("Persona borrada exitosamente")
                .build();

    }
}
