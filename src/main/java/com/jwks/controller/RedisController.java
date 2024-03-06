package com.jwks.controller;

import com.jwks.models.PID;
import com.jwks.repositories.PIDdao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pid")
public class RedisController {
    private PIDdao dao;

    public RedisController(PIDdao dao) {
        this.dao = dao;
    }

    @PostMapping
    public PID save(@RequestBody PID pid){
        return dao.save(pid);
    }

    @GetMapping("/{id}")
    public PID getPID(@PathVariable int id){
        return dao.findPidById(id);
    }

}
