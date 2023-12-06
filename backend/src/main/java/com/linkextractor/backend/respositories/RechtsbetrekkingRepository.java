package com.linkextractor.backend.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.linkextractor.backend.models.Rechtsbetrekking;

import java.lang.String;
import java.util.List;
import java.util.Optional;

public interface RechtsbetrekkingRepository extends CrudRepository<Rechtsbetrekking, String> {
    List<Rechtsbetrekking> findAll();

    Optional<Rechtsbetrekking> findById(String rb_code);

    Rechtsbetrekking save(Rechtsbetrekking rechtsbetrekking);

    void deleteById(String rb_code);

}
