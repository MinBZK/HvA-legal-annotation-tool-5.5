package com.linkextractor.backend.service;

import com.linkextractor.backend.models.*;
import com.linkextractor.backend.respositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private XMLBronRepository xmlBronRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkingTableUserLabelXMLRepository userLabelXMLTable;


    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    public Optional<Label> getLabelById(int labelId) {
        return labelRepository.findById(labelId);
    }

    public Label saveLabel(Label label) {
        return labelRepository.save(label);
    }

    public void deleteLabelById(int labelId) {
        labelRepository.deleteById(labelId);
    }

    public List<Label> getLabelsByUsernameAndXMLBron(String username, String xmlBronNaam, LocalDate xmlbronDate) {
        // Step 1: Retrieve the XMLBron
        XMLBron xmlbron = xmlBronRepository.findByArtikelNaamAndDate(xmlBronNaam, xmlbronDate);

        // Step 2: Retrieve the user
        User user = userRepository.findUserByUsername(username);

        // Step 3: Query the UserLabelXMLTable (or LinkingTable) entity
        List<UserLabelXMLTable> linkingTables = userLabelXMLTable.findByUserAndXmlbron(user, xmlbron);

        // Step 4: Extract labels from the linkingTables and return them
        return linkingTables.stream()
                .map(UserLabelXMLTable::getLabel)
                .peek(label -> label.setUsername(username))
                .collect(Collectors.toList());
    }

    public Label saveLabelAndAssociateWithXMLBron(Label label, String xmlBronNaam, String username, LocalDate xmlbronDate) {
        // Step 1: Save the Definitie
        Label savedLabel = labelRepository.save(label);

        // Step 2: Retrieve the XMLBron
        XMLBron xmlBron = xmlBronRepository.findByArtikelNaamAndDate(xmlBronNaam, xmlbronDate);

        // Step 3: Retrieve the user
        User user = userRepository.findUserByUsername(username);

        // Step 4: Create a new LinkingTable entity
        UserLabelXMLTable linkingTable = new UserLabelXMLTable();
        linkingTable.setLabel(savedLabel);
        linkingTable.setUser(user);
        linkingTable.setXmlBron(xmlBron);

        // Step 5: Save the userDefinitionXMLTable entity
        userLabelXMLTable.save(linkingTable);

        return savedLabel;
    }
}
