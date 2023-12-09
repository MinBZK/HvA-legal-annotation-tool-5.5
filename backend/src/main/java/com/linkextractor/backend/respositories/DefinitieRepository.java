package com.linkextractor.backend.respositories;

import com.linkextractor.backend.models.Definitie;

import java.util.List;
import java.util.Optional;

public interface DefinitieRepository {
    List<Definitie> findAll();

    Optional<Definitie> findById(int definitionId);

    Definitie save(Definitie definitie);

    void deleteById(int definitionId);
}

