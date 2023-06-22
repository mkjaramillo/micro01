package com.distribuida.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
@ApplicationScoped
public class RedisConfig {
    @Inject
    @ConfigProperty(name="redis.host")
     private String host;
    @Inject
    @ConfigProperty(name="redis.port")
     private Integer port;
    @Inject
    @ConfigProperty(name="redis.password")
     private String password;
    @ApplicationScoped //para llamar al metodo en otra clase
    public RedisClient client(){
        String url=String.format("redis://%s@%s:%d",password,host,port);
        System.out.println("url redis"+ url);


        return RedisClient.create(url);
    }


}
