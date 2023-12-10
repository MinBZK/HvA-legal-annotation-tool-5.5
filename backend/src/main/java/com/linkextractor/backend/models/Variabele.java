package com.linkextractor.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Variabele {
    @Id
    @Column(name = "var_id")
    private int var_id;

    @Column(name = "var_naam")
    private String var_naam;

    @Column(name = "var_definitie",nullable = true)
    private String var_definitie;

    @ManyToOne
    @JoinColumn(name = "ar_id", nullable = false)
    private Afleidingsregel afleidingsregel_var;

    public Variabele(int var_id, String var_naam) {
        this.var_id = var_id;
        this.var_naam = var_naam;
    }

    public Variabele() {
    }

    public int getVar_id() {
        return var_id;
    }

    public void setVar_id(int var_id) {
        this.var_id = var_id;
    }

    public String getVar_naam() {
        return var_naam;
    }

    public void setVar_naam(String var_naam) {
        this.var_naam = var_naam;
    }

    
}
