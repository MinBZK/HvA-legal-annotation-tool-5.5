package com.linkextractor.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Definitie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "definitie_id")
    private int id;

    @Column(name = "woord")
    private String woord;

    @Column(name = "definitie")
    private String definitie;

    @Column(name = "positie_start")
    private int positie_start;

    @Column(name = "positie_end")
    private int positie_end;

    @Column(name = "datum")
    private LocalDateTime date;

    @OneToMany(mappedBy = "definitie")
    private Set<UserDefinitionXMLTable> linkingTables;

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

    public String getDefinitie() {
        return definitie;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDefinitie(String definitie) {
        this.definitie = definitie;
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