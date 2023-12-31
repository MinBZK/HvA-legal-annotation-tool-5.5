package com.linkextractor.backend.models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Label {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "label_id")
    private int id;

    @Column(name = "woord")
    private String woord;

    @Column(name = "label")
    private String label;

    @Column(name = "positie_start")
    private int positie_start;

    @Column(name = "positie_end")
    private int positie_end;

    @Column(name = "datum")
    private LocalDateTime date;

    public LocalDateTime getDatum() {
        return date;
    }

    public void setDatum(LocalDateTime datum) {
        this.date = datum;
    }

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
