package com.linkextractor.backend.service;


import com.linkextractor.backend.models.Definitie;
import com.linkextractor.backend.respositories.DefinitieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefinitionService {
    @Autowired
    private DefinitieRepository definitieRepository;

    public List<Definitie> getAllDefinitions() {
        return definitieRepository.findAll();
    }

    public Optional<Definitie> getDefinitionById(int definitionId) {
        return definitieRepository.findById(definitionId);
    }

    public Definitie saveDefinition(Definitie definitie) {
        return definitieRepository.save(definitie);
    }

    public void deleteDefinitionById(int definitionId) {
        definitieRepository.deleteById(definitionId);
    }
}