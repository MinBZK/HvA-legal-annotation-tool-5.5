package com.linkextractor.backend.service;

import com.linkextractor.backend.models.XMLBron;
import com.linkextractor.backend.respositories.XMLBronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XMLBronService {

    private XMLBronRepository xmlBronRepository;

    @Autowired
    public XMLBronService(XMLBronRepository xmlBronRepository) {
        this.xmlBronRepository = xmlBronRepository;
    }

    public XMLBron saveXMLBron(XMLBron xmlBron) {
        return xmlBronRepository.save(xmlBron);
    }

    public void deleteXMLBronById(int id) {
        xmlBronRepository.deleteById(id);
    }
}
