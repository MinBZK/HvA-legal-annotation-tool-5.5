package com.linkextractor.backend.controllers;

import com.linkextractor.backend.dto.XmlBronTimeLineDto;
import com.linkextractor.backend.models.XMLBron;
import com.linkextractor.backend.respositories.XMLBronRepository;
import com.linkextractor.backend.service.XMLBronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

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

    @GetMapping("/api/v1/timelinedata/{artikelNaam}")
    private Iterable<XmlBronTimeLineDto> getAllTimeLineData(@PathVariable String artikelNaam) {
        System.out.println(artikelNaam);
        return xmlBronRepository.findXmlBronDetailsByArtikelNaam(artikelNaam);
    }

    @GetMapping("/api/v1/paginated/")
    private Page<XMLBron> getAllXmlBronnen(Pageable pageable) {
        return xmlBronRepository.findAll(pageable);
    }

    @GetMapping("/byName/paginated/{articleName}")
    private Page<XMLBron> getXmlBronnenByArticleNamePaginated(
            @PathVariable String articleName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return xmlBronRepository.findByArtikelNaam(articleName, pageable);
    }

    @GetMapping("/byName/v1/{articleName}")
    private @ResponseBody
    Iterable<XMLBron> getXMLBronnenByArticleName(@PathVariable String articleName) {
        return xmlBronRepository.findByArticlesNameAndDate(articleName);
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

    @GetMapping("/byName/{artikelNaam}/{xmlbronDate}")
    public ResponseEntity<XMLBron> getXMLBronByArtikelNaam(@PathVariable String artikelNaam, @PathVariable LocalDate xmlbronDate) {
        XMLBron xmlBron = xmlBronRepository.findByArtikelNaamAndDate(artikelNaam, xmlbronDate);

        if (xmlBron != null) {
            return ResponseEntity.ok(xmlBron);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/v1/")
    private ResponseEntity<XMLBron> createXMLBron(@RequestBody XMLBron xmlBron) {
        System.out.println(xmlBron.toString());
        XMLBron toBeSavedXmlBron = xmlBronRepository.save(xmlBron);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toBeSavedXmlBron.getXmlBronId()).toUri();


        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable int xmlbron_id) {

    }
}
