package com.distribuida;
import com.distribuida.db.Persona;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


class Message{
    private String msg="hola mundo";



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

    @Path("/personas")
    public class GreetingResource {

        @GET
        @Path("/list")
        @Produces(MediaType.APPLICATION_JSON)
        public List<Persona> listarPersonas() {
            return Persona.listAll();
        }

        @POST
        @Path("/crear")
        @Consumes(MediaType.APPLICATION_JSON)
        @Transactional
        public Response crearPersona(Persona persona) {

            persona.persist();
            return Response.status(Response.Status.CREATED)
                    .entity("Persona creada exitosamente")
                    .build();
        }

        @PUT
        @Path("/actualizada/{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Transactional
        public Response actualizarPersona(@PathParam("id") Integer id, Persona personaActualizada) {
            Persona personaExistente = Persona.findById(id);

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
        @Produces(MediaType.APPLICATION_JSON)
        public Response obtenerPersonaPorId(@PathParam("id") int id) {
            Persona persona = Persona.findById(id);

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
        @Path("/borrar/{id}")
        @Transactional
        public Response eliminarPersona(@PathParam("id") Long id) {
            Persona persona = Persona.findById(id);
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

