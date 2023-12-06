package com.linkextractor.backend.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.linkextractor.backend.models.Rechtssubject;

import java.util.List;
import java.util.Optional;

public interface RechtssubjectRepository extends CrudRepository<Rechtssubject, String>{
    List<Rechtssubject> findAll();

    Optional<Rechtssubject> findById(String rs_code);

    Rechtssubject save(Rechtssubject rechtssubject);

    void deleteById(String rs_code);
}
