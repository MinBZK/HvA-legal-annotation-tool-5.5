package com.linkextractor.backend.respositories;

import com.linkextractor.backend.models.XMLBron;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface XMLBronRepository extends CrudRepository<XMLBron, Integer> {

    List<XMLBron> findAll();

    XMLBron findById(int xmlbron_id);

    @Query("SELECT x FROM XMLBron x WHERE x.artikel_naam = :artikelNaam")
    XMLBron findByArtikelNaam(@Param("artikelNaam") String artikelNaam);

    @Query("SELECT x FROM XMLBron x WHERE x.artikel_naam = :artikelNaam AND x.xmlbron_date = :xmlbronDate")
    XMLBron findByArtikelNaamAndDate(@Param("artikelNaam") String artikelNaam, @Param("xmlbronDate") LocalDate xmlbronDate);

    XMLBron save(XMLBron xmlbron);

    void deleteById(int xmlbron_id);
}