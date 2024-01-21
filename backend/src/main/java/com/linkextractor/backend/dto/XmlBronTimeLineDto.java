package com.linkextractor.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class XmlBronTimeLineDto {
    private Integer xmlbron_id;
    private String artikel_naam;
    private LocalDate xmlbronDate;
    private String firstname;
    private String lastname;
    private Integer userId;
    private LocalDateTime date;
    private String woord;
    private String definitie;
    private Integer positie_end;
    private Integer positie_start;

    public XmlBronTimeLineDto(Integer xmlbron_id, String artikel_naam, LocalDate xmlbronDate, String firstname,
            String lastname, Integer userId, LocalDateTime date, String woord, String definitie, Integer positie_end,
            Integer positie_start) {
        this.xmlbron_id = xmlbron_id;
        this.artikel_naam = artikel_naam;
        this.xmlbronDate = xmlbronDate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userId = userId;
        this.date = date;
        this.woord = woord;
        this.definitie = definitie;
        this.positie_end = positie_end;
        this.positie_start = positie_start;
    }


    public XmlBronTimeLineDto() {
    }

    
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getXmlbronId() {
        return xmlbron_id;
    }

    public void setXmlbronId(int xmlBronId) {
        this.xmlbron_id = xmlBronId;
    }

    public String getArtikelNaam() {
        return artikel_naam;
    }

    public void setArtikel_naam(String artikelNaam) {
        this.artikel_naam = artikelNaam;
    }

    public LocalDate getXmlbronDate() {
        return xmlbronDate;
    }

    public void setXmlbronDate(LocalDate xmlbronDate) {
        this.xmlbronDate = xmlbronDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getWoord() {
        return woord;
    }


    public void setWoord(String woord) {
        this.woord = woord;
    }


    public String getDefinitie() {
        return definitie;
    }


    public void setDefinitie(String definitie) {
        this.definitie = definitie;
    }


    public Integer getPositie_end() {
        return positie_end;
    }


    public void setPositie_end(Integer positie_end) {
        this.positie_end = positie_end;
    }


    public Integer getPositie_start() {
        return positie_start;
    }


    public void setPositie_start(Integer positie_start) {
        this.positie_start = positie_start;
    }

    

    // Constructor, getters and setters

    
}


