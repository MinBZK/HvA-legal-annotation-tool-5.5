package com.linkextractor.backend.service;


import com.linkextractor.backend.models.Definitie;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.models.XMLBron;
import com.linkextractor.backend.respositories.DefinitieRepository;
import com.linkextractor.backend.respositories.UserRepository;
import com.linkextractor.backend.respositories.XMLBronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefinitionService {

    @Autowired
    private DefinitieRepository definitieRepository;

    @Autowired
    private XMLBronRepository xmlBronRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Definitie> getAllDefinitions() {
        return definitieRepository.findAll();
    }

    public Optional<Definitie> getDefinitionById(int definitionId) {
        return definitieRepository.findById(definitionId);
    }

    public Definitie saveDefinition(Definitie definitie) {
        return definitieRepository.save(definitie);
    }

    public void saveDefinitionAndAssociateWithXMLBron(Definitie definitie, int xmlBronId, String username) {
        // Step 1: Save the Definitie
        Definitie savedDefinitie = definitieRepository.save(definitie);

        // Step 2: Retrieve the XMLBron
        XMLBron xmlBron = xmlBronRepository.findById(xmlBronId);

        // Step 3: Retrieve the user
        Optional<User> user = userRepository.findByUsername(username);

        // Step 4: Add the new Definitie to the set in XMLBron
        xmlBron.getDefinities().add(savedDefinitie);


        // Step 5: Save the modified XMLBron entity
        xmlBronRepository.save(xmlBron);

    }

    public void deleteDefinitionById(int definitionId) {
        definitieRepository.deleteById(definitionId);
    }
}
