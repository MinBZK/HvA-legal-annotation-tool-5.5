package com.linkextractor.backend.respositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.linkextractor.backend.models.Brondefinitie;

public interface BrondefinitieRepository extends CrudRepository<Brondefinitie, Integer>{
    
    List<Brondefinitie> findAll();

    Brondefinitie findById(int brondefinitie_id);

    Brondefinitie save(Brondefinitie brondefinitie);

    void deleteById(int brondefinitie_id);
}
