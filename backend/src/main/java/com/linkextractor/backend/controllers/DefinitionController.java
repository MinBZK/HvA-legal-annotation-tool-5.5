package com.linkextractor.backend.controllers;

import com.linkextractor.backend.models.Definitie;
import com.linkextractor.backend.service.DefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("getDefinitions/{xmlBronName}/{username}/{xmlbronDate}")
    public ResponseEntity<List<Definitie>> getDefinitionsByUsernameAndXMLBron(@PathVariable String xmlBronName,
                                                                              @PathVariable String username,
                                                                              @PathVariable LocalDate xmlbronDate) {

        List<Definitie> definitions = definitionService.getDefinitionsByUsernameAndXMLBron(username, xmlBronName, xmlbronDate);

        return new ResponseEntity<>(definitions, HttpStatus.OK);
    }

    @GetMapping("{definitionId}")
    public ResponseEntity<Definitie> getDefinitionById(@PathVariable int definitionId) {
        Optional<Definitie> definition = definitionService.getDefinitionById(definitionId);

        return definition.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("addDefinition/{xmlBronName}/{username}/{xmlbronDate}")
    public ResponseEntity<Definitie> saveDefinition(@RequestBody Definitie definitie,
                                                    @PathVariable String xmlBronName,
                                                    @PathVariable String username,
                                                    @PathVariable LocalDate xmlbronDate) {
        Definitie savedDefinition = definitionService.saveDefinitionAndAssociateWithXMLBron(definitie, xmlBronName, username, xmlbronDate);

        return new ResponseEntity<>(savedDefinition, HttpStatus.CREATED);
    }

    @DeleteMapping("{definitionId}")
    public ResponseEntity<Void> deleteDefinitionById(@PathVariable int definitionId) {
        definitionService.deleteDefinitionById(definitionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}