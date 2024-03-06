package com.jwks.repositories;

import com.jwks.models.PID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PIDdao {
    public static final String HASH_KEY = "Pid";

    private RedisTemplate template;

    public PIDdao(RedisTemplate template) {
        this.template = template;
    }

    public PID save(PID pid){
        template.opsForHash().put(HASH_KEY, pid.getId(), pid);
        return pid;
    }

    public PID findPidById(int id){
        return (PID)template.opsForHash().get(HASH_KEY, id);
    }
}
