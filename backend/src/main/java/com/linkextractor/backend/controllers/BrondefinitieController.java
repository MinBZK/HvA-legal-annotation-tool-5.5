<<<<<<< HEAD:backend/src/main/java/com/linkextractor/backend/controllers/BrondefinitieController.java
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

import com.linkextractor.backend.models.Brondefinitie;
import com.linkextractor.backend.models.Rechtssubject;
import com.linkextractor.backend.respositories.BrondefinitieRepository;
import com.linkextractor.backend.respositories.RechtssubjectRepository;

@RestController
@RequestMapping("/brondefinitie")
public class BrondefinitieController {
    
    private BrondefinitieRepository brondefinitieRepository;

    @Autowired
    public BrondefinitieController(BrondefinitieRepository brondefinitieRepository) {
        this.brondefinitieRepository = brondefinitieRepository;
    }

    @GetMapping
    private @ResponseBody Iterable<Brondefinitie> getBrondefinities(){
        return brondefinitieRepository.findAll();
    }


    @PostMapping
    private ResponseEntity<Brondefinitie> createBrondefinitie(@RequestBody Brondefinitie brondefinitie){
        Brondefinitie toBeSavedBrondefinitie = brondefinitieRepository.save(brondefinitie);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toBeSavedBrondefinitie.getBrondefinitie_id()).toUri();


        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable int id){
        
    }
}
=======
//package com.linkextractor.controllers;
//
//import java.net.URI;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import com.linkextractor.models.Brondefinitie;
//import com.linkextractor.models.Rechtssubject;
//import com.linkextractor.respositories.BrondefinitieRepository;
//import com.linkextractor.respositories.RechtssubjectRepository;
//
//@RestController
//@RequestMapping("/brondefinitie")
//public class BrondefinitieController {
//    private BrondefinitieRepository brondefinitieRepository;
//
//    @Autowired
//    public BrondefinitieController(BrondefinitieRepository brondefinitieRepository) {
//        this.brondefinitieRepository = brondefinitieRepository;
//    }
//
//    @GetMapping
//    private @ResponseBody Iterable<Brondefinitie> getBrondefinities(){
//        return brondefinitieRepository.findAll();
//    }
//
//
//    @PostMapping
//    private ResponseEntity<Brondefinitie> createBrondefinitie(@RequestBody Brondefinitie brondefinitie){
//
//        Brondefinitie toBeSavedBrondefinitie = brondefinitieRepository.save(brondefinitie)
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(toBeSavedBrondefinitie.getBrondefinitie_id()).toUri();
//
//
//        return ResponseEntity.created(location).build();
//    }
//
//    @DeleteMapping("/{id}")
//    private void deleteById(@PathVariable String rs_code){
//
//    }
//}
>>>>>>> origin/login-signup-flow:backend/src/main/java/com/linkextractor/controllers/BrondefinitieController.java
