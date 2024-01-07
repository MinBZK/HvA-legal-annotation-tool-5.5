package com.linkextractor.backend.controllers;


import com.linkextractor.backend.models.Definitie;
import com.linkextractor.backend.models.Label;

import com.linkextractor.backend.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {

    private LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("")
    public ResponseEntity<List<Label>> getAllLabels() {
        List<Label> labels = labelService.getAllLabels();
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

    @GetMapping("getLabels/{xmlBronName}/{username}/{xmlbronDate}")
    public ResponseEntity<List<Label>> getLabelsByUsernameAndXMLBron(@PathVariable String xmlBronName,
                                                                              @PathVariable String username,
                                                                              @PathVariable LocalDate xmlbronDate) {

        List<Label> labels = labelService.getLabelsByUsernameAndXMLBron(username, xmlBronName, xmlbronDate);

        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

    @GetMapping("{labelId}")
    public ResponseEntity<Label> getLabelById(@PathVariable int labelId) {
        Optional<Label> label = labelService.getLabelById(labelId);
        return label.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @PostMapping("addLabel")
//    public ResponseEntity<Label> saveLabel(@RequestBody Label label) {
//        label.setDatum(LocalDateTime.now());
//        Label savedLabel = labelService.saveLabel(label);
//        return new ResponseEntity<>(savedLabel, HttpStatus.CREATED);
//    }

    @PostMapping("addLabel/{xmlBronName}/{username}/{xmlbronDate}")
    public ResponseEntity<Label> saveLabel(@RequestBody Label label,
                                           @PathVariable String xmlBronName,
                                           @PathVariable String username,
                                           @PathVariable LocalDate xmlbronDate) {
        //label.setDatum(LocalDateTime.now());
        Label savedLabel = labelService.saveLabelAndAssociateWithXMLBron(label, xmlBronName, username, xmlbronDate);
        return new ResponseEntity<>(savedLabel, HttpStatus.CREATED);
    }

    @DeleteMapping("{labelId}")
    public ResponseEntity<Void> deleteLabelById(@PathVariable int labelId) {
        labelService.deleteLabelById(labelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
