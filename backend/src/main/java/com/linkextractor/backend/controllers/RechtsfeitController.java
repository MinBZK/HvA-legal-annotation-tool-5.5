package com.linkextractor.backend.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linkextractor.backend.models.Rechtsfeit;
import com.linkextractor.backend.respositories.RechtsfeitRepository;

@RestController
@RequestMapping("/rechtsfeit")
public class RechtsfeitController {
    private RechtsfeitRepository rechtsfeitRepository;

    @Autowired
    public RechtsfeitController(RechtsfeitRepository rechtsfeitRepository) {
        this.rechtsfeitRepository = rechtsfeitRepository;
    }

    @GetMapping
    private @ResponseBody Iterable<Rechtsfeit> getRechtsfeiten(){
        return rechtsfeitRepository.findAll();
    }

    @PostMapping
    private ResponseEntity<Rechtsfeit> createRechtsFeit(@RequestBody Rechtsfeit rechtsfeit){

        Rechtsfeit toBeSavedRechtsfeit = rechtsfeitRepository.save(rechtsfeit);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toBeSavedRechtsfeit.getRf_code()).toUri();


        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable String rF_code){
        
    }
}
