package com.linkextractor.backend.models;

import java.util.ArrayList;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Rechtssubject {
    @Id
    @GenericGenerator(name = "rs_code", strategy = "com.linkextractor.backend.config.CustomIdGenerator")
    @GeneratedValue(generator = "rs_code")
    @Column(name = "rs_code")
    private String rs_code;

    @Column(name = "rs_naam")
    private String rs_naam;

    @Column(name = "specalisatie")
    private boolean specalisatie;

    @ManyToOne
    private Rechtssubject rechtssubject;

    @OneToMany(mappedBy = "rechtssubject")
    private ArrayList<Rechtssubject> rechtsubjecten;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rechtsbetrekkingcode", referencedColumnName = "rb_code")
    private Rechtsbetrekking rechtsbetrekking;

    @OneToMany(mappedBy = "rechtssubject_vw")
    private ArrayList<Voorwaarde> voorwaardes;

    public Rechtssubject(String rs_code, String rs_naam, boolean specalisatie) {
        this.rs_code = rs_code;
        this.rs_naam = rs_naam;
        this.specalisatie = specalisatie;
    }

    public Rechtssubject() {
    }

    public String getRs_code() {
        return rs_code;
    }

    public void setRs_code(String rs_code) {
        this.rs_code = rs_code;
    }

    public String getRs_naam() {
        return rs_naam;
    }

    public void setRs_naam(String rs_naam) {
        this.rs_naam = rs_naam;
    }

    public boolean isSpecalisatie() {
        return specalisatie;
    }

    public void setSpecalisatie(boolean specalisatie) {
        this.specalisatie = specalisatie;
    }
    
}
