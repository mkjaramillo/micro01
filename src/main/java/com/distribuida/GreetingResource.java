package com.distribuida;
import com.distribuida.db.Persona;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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

    @Path("/hello")
    public class GreetingResource {

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public List<Persona> listarPersonas() {
            return Persona.listAll();
        }
    }

