package com.linkextractor.backend.models;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Rechtsfeit {
    @Id
    @GenericGenerator(name = "rf_code", strategy =  "com.linkextractor.backend.config.CustomIdGenerator")
    @GeneratedValue(generator = "rf_code")
    @Column(name = "rf_code")
    private String rf_code;

    @Column(name = "rf_naam")
    private String rf_naam;
    @Column(name = "tijds_aanduiding")
    private String tijds_aanduiding;
    @Column(name = "plaats_aanduiding")
    private String plaats_aanduiding;

    @Column(name = "rf_definitie", nullable = true)
    private String rf_definitie;

    @OneToMany(mappedBy = "rechtsfeit_vw")
    private ArrayList<Voorwaarde> voorwaardes;

    public Rechtsfeit(String rf_code, String rf_naam, String tijds_aanduiding, String plaats_aanduiding) {
        this.rf_code = rf_code;
        this.rf_naam = rf_naam;
        this.tijds_aanduiding = tijds_aanduiding;
        this.plaats_aanduiding = plaats_aanduiding;
    }

    
    public Rechtsfeit() {
    }

    public String getRf_code() {
        return rf_code;
    }

    public void setRf_code(String rf_code) {
        this.rf_code = rf_code;
    }

    public String getRf_naam() {
        return rf_naam;
    }

    public void setRf_naam(String rf_naam) {
        this.rf_naam = rf_naam;
    }

    public String getTijds_aanduiding() {
        return tijds_aanduiding;
    }

    public void setTijds_aanduiding(String tijds_aanduiding) {
        this.tijds_aanduiding = tijds_aanduiding;
    }

    public String getPlaats_aanduiding() {
        return plaats_aanduiding;
    }

    public void setPlaats_aanduiding(String plaats_aanduiding) {
        this.plaats_aanduiding = plaats_aanduiding;
    }

    

}
