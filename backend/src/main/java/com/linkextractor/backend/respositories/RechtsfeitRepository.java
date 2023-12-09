package com.linkextractor.backend.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.linkextractor.backend.models.Rechtsfeit;

import java.util.List;
import java.util.Optional;

public interface RechtsfeitRepository extends CrudRepository<Rechtsfeit, String>{

    List<Rechtsfeit> findAll();

    Optional<Rechtsfeit> findById(String rf_code);

    Rechtsfeit save(Rechtsfeit rechtsfeit);

    void deleteById(String rf_code);

}
