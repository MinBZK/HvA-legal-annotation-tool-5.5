package com.linkextractor.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Brondefinitie {
    @Id
    @Column(name = "brondefinitie_id")
    private int brondefinitie_id;

    @Column(name = "link")
    private String link;

    @Column(name = "naam")
    private String naam;

    public Brondefinitie(int brondefinitie_id, String link, String naam) {
        this.brondefinitie_id = brondefinitie_id;
        this.link = link;
        this.naam = naam;
    }

    public Brondefinitie() {
    }

    public int getBrondefinitie_id() {
        return brondefinitie_id;
    }

    public void setBrondefinitie_id(int brondefinitie_id) {
        this.brondefinitie_id = brondefinitie_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
    
}
