package com.distribuida.repo;

import com.distribuida.db.Persona;
import com.distribuida.rest.PersonaRest;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.vertx.http.runtime.devmode.Json;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

import java.util.Optional;

@ApplicationScoped
public class PersonaRepository implements PanacheRepositoryBase<Persona,Long> {
    @Inject
    RedisClient redisClient;

    Logger logger= LoggerFactory.getLogger(PersonaRepository.class);
    public Optional<Persona> findByIdOptionalCache(Long id){
        /**
         * buscar en el cache
         * esta en el cache
         * retornar la instancia
         * si no esta
         * busca en la base
         * poner en el cache
         * retornar la instancia
         *
         * */

        //validar coneccion
        try(StatefulRedisConnection<String, String> conn=redisClient.connect())
        {
            RedisCommands<String, String> syncCommands = conn.sync();
            String value = syncCommands.get(Long.toString(id));

            var gson = new Gson();
            if (value != null) {
                System.out.println("Registro con id encontrado en memoria");
                Persona personaCACHE = gson.fromJson(value, Persona.class);
                return Optional.of(personaCACHE);
                /*
                Persona ret = new Persona();
                ret.id  = id;
                ret.setName(value);

                String personaJSON = gson.toJson(ret);
                System.out.println("Registro con id encontrado en memoria");
                */
            }
            System.out.println("Registro con id ese no encontrado en memoria");
            System.out.println("Buscando en la base....");
            Optional<Persona> personaDB = findByIdOptional(id);
            if (personaDB.isPresent()) {
                syncCommands.set(Long.toString(personaDB.get().id), gson.toJson(personaDB.get()));
            }

            return personaDB;

        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }


        //return this.findByIdOptional(id);
    }

    public Persona findByIdOptionalCacheAsyn(Long id){
        try(StatefulRedisConnection<String, String> conn=redisClient.connect()) {
            RedisAsyncCommands<String, String> commands = conn.async();
            RedisFuture<String> future = commands.get(Long.toString(id));
            String value = future.get();
            var gson = new Gson();
            if(value!=null){
                System.out.println("Registro con id encontrado en memoria asin");
                Persona personaCACHE = gson.fromJson(value, Persona.class);
                return personaCACHE;
            }
            System.out.println("Registro con id ese no encontrado en memoria asin");
            System.out.println("Buscando en la base....");
            Persona persona= findById(id);
            commands.set(Long.toString(id),gson.toJson(persona));
            return persona;


        }catch (Exception e){

            e.printStackTrace();
        }
        return null;
    }
}
