package com.linkextractor.backend.controllers;

import com.linkextractor.backend.models.XMLBron;
import com.linkextractor.backend.respositories.XMLBronRepository;
import com.linkextractor.backend.service.XMLBronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/XMLBron")
public class XMLBronController {
    private XMLBronRepository xmlBronRepository;
    private XMLBronService xmlBronService;

    @Autowired
    public XMLBronController(XMLBronRepository xmlBronRepository, XMLBronService xmlBronService) {
        this.xmlBronRepository = xmlBronRepository;
        this.xmlBronService = xmlBronService;
    }

    @GetMapping("/api/v1/")
    private @ResponseBody
    Iterable<XMLBron> getXMLBronnen() {
        return xmlBronRepository.findAll();
    }

    @GetMapping("/byName/{artikelNaam}")
    public ResponseEntity<XMLBron> getXMLBronByArtikelNaam(@PathVariable String artikelNaam) {
        XMLBron xmlBron = xmlBronRepository.findByArtikelNaam(artikelNaam);
        if (xmlBron != null) {
            return ResponseEntity.ok(xmlBron);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/v1/")
    private ResponseEntity<XMLBron> createXMLBron(@RequestBody XMLBron xmlBron) {
        XMLBron toBeSavedXmlBron = xmlBronRepository.save(xmlBron);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toBeSavedXmlBron.getXmlbron_id()).toUri();


        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable int xmlbron_id) {

    }
}
