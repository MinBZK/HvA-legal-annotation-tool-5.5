package com.linkextractor.backend.respositories;

import com.linkextractor.backend.dto.XmlBronTimeLineDto;
import com.linkextractor.backend.models.XMLBron;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface XMLBronRepository extends CrudRepository<XMLBron, Integer> {
    List<XMLBron> findAll();

    XMLBron findById(int xmlbron_id);

    @Query("SELECT new com.linkextractor.backend.dto.XmlBronTimeLineDto(" +
    "xm.xmlBronId, xm.artikelNaam, xm.xmlbron_date, " +
    "us.firstname, us.lastname, us.userId) " +
    "FROM XMLBron xm " +
    "INNER JOIN xm.userDefinitionXMLTables ud " +
    "INNER JOIN ud.user us "+
    "WHERE xm.artikelNaam = :artikelNaam")
    List<XmlBronTimeLineDto> findXmlBronDetailsByArtikelNaam(@Param("artikelNaam") String artikelNaam);

    @Query("SELECT x FROM XMLBron x WHERE x.artikelNaam = :artikelNaam")
    XMLBron findByArtikelNaam(@Param("artikelNaam") String artikelNaam);

    @Query("SELECT x FROM XMLBron x WHERE x.artikelNaam = :artikelNaam AND x.xmlbron_date = :xmlbronDate")
    XMLBron findByArtikelNaamAndDate(@Param("artikelNaam") String artikelNaam, @Param("xmlbronDate") LocalDate xmlbronDate);

    @Query("SELECT x FROM XMLBron x WHERE x.artikelNaam = :artikelNaam")
    List<XMLBron> findByArticlesNameAndDate(@Param("artikelNaam") String artikelNaam);

    XMLBron save(XMLBron xmlbron);

    void deleteById(int xmlbron_id);

    Page<XMLBron> findAll(Pageable pageable);

    @Query("SELECT x FROM XMLBron x WHERE x.artikelNaam = :artikelNaam")
    Page<XMLBron> findByArtikelNaam(@Param("artikelNaam") String artikelNaam, Pageable pageable);
}