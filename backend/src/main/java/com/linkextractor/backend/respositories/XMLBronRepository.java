package com.linkextractor.backend.respositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.linkextractor.backend.models.XMLBron;

public interface XMLBronRepository extends CrudRepository<XMLBron, Integer>{
    
    List<XMLBron> findAll();

    XMLBron findById(int xmlbron_id);

    XMLBron save(XMLBron xmlbron);

    void deleteById(int xmlbron_id);
}