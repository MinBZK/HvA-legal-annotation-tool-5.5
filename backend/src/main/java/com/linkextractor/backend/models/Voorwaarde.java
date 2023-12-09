package com.linkextractor.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Voorwaarde {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "naam")
    private int naam;

    @Column(name = "voldaan")
    private Boolean voldaan;

    @Column(name = "vw_definitie",nullable = true)
    private String vw_definitie;

    @ManyToOne
    @JoinColumn(name = "rs_code")
    private Rechtssubject rechtssubject_vw;

    @ManyToOne
    @JoinColumn(name = "rf_code")
    private Rechtsfeit rechtsfeit_vw;

    public Voorwaarde(int id, int naam, Boolean voldaan) {
        this.id = id;
        this.naam = naam;
        this.voldaan = voldaan;
    }

    
    public Voorwaarde() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNaam() {
        return naam;
    }

    public void setNaam(int naam) {
        this.naam = naam;
    }

    public Boolean getVoldaan() {
        return voldaan;
    }

    public void setVoldaan(Boolean voldaan) {
        this.voldaan = voldaan;
    }

    
}
