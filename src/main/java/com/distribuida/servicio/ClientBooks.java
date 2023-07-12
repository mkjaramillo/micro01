package com.distribuida.servicio;

import com.distribuida.DTO.LibroDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;


@Path("/books")
public class ClientBooks {
    private Client client= ClientBuilder.newClient();
    @GET
    @Path("/{id}")
    public LibroDTO buscarLibro(@PathParam("id") Long id) {
        System.out.println("kkkkkkkk");
        String url = "http://localhost:8081/books/" + id;
        LibroDTO libro = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .get(LibroDTO.class);
        return libro;
    }
}
