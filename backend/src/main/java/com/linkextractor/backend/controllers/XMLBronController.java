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

import com.linkextractor.backend.models.XMLBron;
import com.linkextractor.backend.respositories.XMLBronRepository;

@RestController
@RequestMapping("/XMLBron")
public class XMLBronController {
    private XMLBronRepository xmlBronRepository;

    @Autowired
    public XMLBronController(XMLBronRepository xmlBronRepository) {
        this.xmlBronRepository = xmlBronRepository;
    }

    @GetMapping
    private @ResponseBody Iterable<XMLBron> getXMLBronnen(){
        return xmlBronRepository.findAll();
    }


    @PostMapping
    private ResponseEntity<XMLBron> createXMLBron(@RequestBody XMLBron xmlBron){

        XMLBron toBeSavedXmlBron = xmlBronRepository.save(xmlBron);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toBeSavedXmlBron.getXmlbron_id()).toUri();


        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable int xmlbron_id){
        
    }
}
