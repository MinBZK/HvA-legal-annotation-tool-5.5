package com.linkextractor.backend.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linkextractor.backend.models.Rechtsbetrekking;
import com.linkextractor.backend.models.Rechtsfeit;
import com.linkextractor.backend.respositories.RechtsbetrekkingRepository;

@RestController
@RequestMapping("/rechtsbetrekking")
@CrossOrigin("*")
public class RechtsbetrekkingController {
    private RechtsbetrekkingRepository rechtsbetrekkingRepository;

    @Autowired
    public RechtsbetrekkingController(RechtsbetrekkingRepository rechtsbetrekkingRepository) {
        this.rechtsbetrekkingRepository = rechtsbetrekkingRepository;
    }

    @GetMapping
    private @ResponseBody Iterable<Rechtsbetrekking> getRechtsbetrekkingen(){
        return rechtsbetrekkingRepository.findAll();
    }

    @PostMapping
    private ResponseEntity<Rechtsbetrekking> createRechtsbetrekking(@RequestBody Rechtsbetrekking rechtsbetrekking){
        Rechtsbetrekking toBeSavedRechtsbetrekking = rechtsbetrekkingRepository.save(rechtsbetrekking);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toBeSavedRechtsbetrekking.getRb_code()).toUri();


        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable String rb_code){
        
    }
    
}
