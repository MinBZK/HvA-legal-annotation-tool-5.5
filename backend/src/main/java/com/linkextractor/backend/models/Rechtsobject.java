package com.linkextractor.backend.models;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Rechtsobject {
    @Id
    @Column(name = "ro_code")
    private String ro_code;

    @Column(name = "ro_naam")
    private String ro_naam;

    @Column(name = "specalisatie")
    private boolean specalisatie;

    @Column(name = "ro_definitie",nullable = true)
    private String ro_definitie;

    @ManyToOne
    private Rechtsobject rechtsobject;

    @OneToMany(mappedBy = "rechtsobject")
    private ArrayList<Rechtsobject> rechtsobjecten;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rechtsbetrekkingcode", referencedColumnName = "rb_code")
    private Rechtsbetrekking rechtsbetrekking_ro;

    public Rechtsobject(String ro_code, String ro_naam) {
        this.ro_code = ro_code;
        this.ro_naam = ro_naam;
    }

    public Rechtsobject() {
    }

    public String getRo_code() {
        return ro_code;
    }

    public void setRo_code(String ro_code) {
        this.ro_code = ro_code;
    }

    public String getRo_naam() {
        return ro_naam;
    }

    public void setRo_naam(String ro_naam) {
        this.ro_naam = ro_naam;
    }

    
}
