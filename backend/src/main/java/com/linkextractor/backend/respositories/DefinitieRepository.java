package com.linkextractor.backend.respositories;

import com.linkextractor.backend.models.Definitie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DefinitieRepository extends JpaRepository<Definitie, Integer> {
    List<Definitie> findAll();

    Optional<Definitie> findById(int definitionId);

    Definitie save(Definitie definitie);

    void deleteById(int definitionId);
}

