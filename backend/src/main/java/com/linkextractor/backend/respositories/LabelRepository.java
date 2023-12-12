package com.linkextractor.backend.respositories;

import com.linkextractor.backend.models.Definitie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Definitie, Long> {

    List<Definitie> findAll();

    Optional<Definitie> findById(int definitionId);

    Definitie save(Definitie definitie);

    void deleteById(int definitionId);
}
