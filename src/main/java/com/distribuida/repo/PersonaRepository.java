package com.distribuida.repo;

import com.distribuida.db.Persona;
import io.lettuce.core.RedisClient;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class PersonaRepository implements PanacheRepositoryBase<Persona,Long> {
    @Inject
    RedisClient redisClient;
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
        try(var conn=redisClient.connect()){

        }catch (Exception e){
            e.printStackTrace();

        }

        return this.findByIdOptional(id);
    }
}
