package com.linkextractor.backend.models;

import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Definitie {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "definitie_id")
    private int id;

    @Column(name = "woord")
    private String woord;

    @Column(name = "definitie")
    private String definite;
    
    @Column(name = "positie_start")
    private int positie_start;

    @Column(name = "positie_end")
    private int positie_end;

    public Definitie() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWoord() {
        return woord;
    }

    public void setWoord(String woord) {
        this.woord = woord;
    }

    public String getDefinite() {
        return definite;
    }

    public void setDefinite(String definite) {
        this.definite = definite;
    }

    public int getPositie_start() {
        return positie_start;
    }

    public void setPositie_start(int positie_start) {
        this.positie_start = positie_start;
    }

    public int getPositie_end() {
        return positie_end;
    }

    public void setPositie_end(int positie_end) {
        this.positie_end = positie_end;
    }
    
    
}