package com.linkextractor.backend.dto;

import java.time.LocalDate;

public class XmlBronTimeLineDto {
    private Integer xmlbron_id;
    private String artikel_naam;
    private LocalDate xmlbronDate;
    private String firstname;
    private String lastname;
    private Integer userId;
    
    public XmlBronTimeLineDto(int xmlbron_id, String artikel_naam, LocalDate xmlbronDate, String firstname,
            String lastname, int userId) {
        this.xmlbron_id = xmlbron_id;
        this.artikel_naam = artikel_naam;
        this.xmlbronDate = xmlbronDate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userId = userId;
    }

    

    public XmlBronTimeLineDto() {
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

    // Constructor, getters and setters

    
}


