package com.linkextractor.backend.service;


import com.linkextractor.backend.models.Definitie;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.models.UserDefinitionXMLTable;
import com.linkextractor.backend.models.XMLBron;
import com.linkextractor.backend.respositories.DefinitieRepository;
import com.linkextractor.backend.respositories.LinkingTableUserDefinitionXMLRepository;
import com.linkextractor.backend.respositories.UserRepository;
import com.linkextractor.backend.respositories.XMLBronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefinitionService {

    @Autowired
    private DefinitieRepository definitieRepository;

    @Autowired
    private XMLBronRepository xmlBronRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkingTableUserDefinitionXMLRepository userDefinitionXMLTable;

    public List<Definitie> getAllDefinitions() {
        return definitieRepository.findAll();
    }

    public List<Definitie> getDefinitionsByUsernameAndXMLBron(String username, String xmlBronNaam) {
        // Step 1: Retrieve the XMLBron
        XMLBron xmlbron = xmlBronRepository.findByArtikelNaam(xmlBronNaam);

        // Step 2: Retrieve the user
        User user = userRepository.findUserByUsername(username);

        // Step 3: Query the UserDefinitionXMLTable (or LinkingTable) entity
        List<UserDefinitionXMLTable> linkingTables = userDefinitionXMLTable.findByUserAndXmlbron(user, xmlbron);

        // Step 4: Extract definitions from the linkingTables and return them
        return linkingTables.stream()
                .map(UserDefinitionXMLTable::getDefinitie)
                .collect(Collectors.toList());
    }

    public Optional<Definitie> getDefinitionById(int definitionId) {
        return definitieRepository.findById(definitionId);
    }

    public Definitie saveDefinition(Definitie definitie) {
        return definitieRepository.save(definitie);
    }

    public Definitie saveDefinitionAndAssociateWithXMLBron(Definitie definitie, String xmlBronNaam, String username) {
        // Step 1: Save the Definitie
        Definitie savedDefinitie = definitieRepository.save(definitie);

        // Step 2: Retrieve the XMLBron
        XMLBron xmlBron = xmlBronRepository.findByArtikelNaam(xmlBronNaam);

        // Step 3: Retrieve the user
        User user = userRepository.findUserByUsername(username);

        // Step 4: Create a new LinkingTable entity
        UserDefinitionXMLTable linkingTable = new UserDefinitionXMLTable();
        linkingTable.setDefinitie(savedDefinitie);
        linkingTable.setUser(user);
        linkingTable.setXmlBron(xmlBron);

        // Step 5: Save the userDefinitionXMLTable entity
        userDefinitionXMLTable.save(linkingTable);

        return savedDefinitie;
    }

    public void deleteDefinitionById(int definitionId) {
        definitieRepository.deleteById(definitionId);
    }
}
