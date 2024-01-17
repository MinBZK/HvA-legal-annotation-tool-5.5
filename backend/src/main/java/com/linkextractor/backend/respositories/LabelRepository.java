package com.linkextractor.backend.respositories;

import com.linkextractor.backend.models.Definitie;
import com.linkextractor.backend.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Integer> {

    List<Label> findAll();

    Optional<Label> findById(int definitionId);

    Label save(Label label);

    void deleteById(int labelId);
}
