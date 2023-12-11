package com.linkextractor.backend.controllers;

import com.linkextractor.backend.models.Definitie;
import com.linkextractor.backend.service.DefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/define")
public class DefinitionController {
    private DefinitionService definitionService;

    @Autowired
    public DefinitionController(DefinitionService definitionService) {
        this.definitionService = definitionService;
    }

    @GetMapping("")
    public ResponseEntity<List<Definitie>> getAllDefinitions() {
        List<Definitie> definitions = definitionService.getAllDefinitions();
        return new ResponseEntity<>(definitions, HttpStatus.OK);
    }

    @GetMapping("{definitionId}")
    public ResponseEntity<Definitie> getDefinitionById(@PathVariable int definitionId) {
        Optional<Definitie> definition = definitionService.getDefinitionById(definitionId);
        return definition.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("addDefinition")
    public ResponseEntity<Definitie> saveDefinition(@RequestBody Definitie definitie) {
        Definitie savedDefinition = definitionService.saveDefinition(definitie);
        return new ResponseEntity<>(savedDefinition, HttpStatus.CREATED);
    }

    @DeleteMapping("{definitionId}")
    public ResponseEntity<Void> deleteDefinitionById(@PathVariable int definitionId) {
        definitionService.deleteDefinitionById(definitionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}