package com.linkextractor.backend.models;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Label {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "definitie_id")
    private int id;

    @Column(name = "woord")
    private String woord;

    @Column(name = "definitie")
    private String label;

    @Column(name = "positie_start")
    private int positie_start;

    @Column(name = "positie_end")
    private int positie_end;


    public int getId() {
        return id;
    }

    public String getWoord() {
        return woord;
    }

    public String getLabel() {
        return label;
    }

    public int getPositie_start() {
        return positie_start;
    }

    public int getPositie_end() {
        return positie_end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWoord(String woord) {
        this.woord = woord;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPositie_start(int positie_start) {
        this.positie_start = positie_start;
    }

    public void setPositie_end(int positie_end) {
        this.positie_end = positie_end;
    }
}
