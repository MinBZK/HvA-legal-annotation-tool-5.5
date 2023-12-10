package com.linkextractor.backend.models;

import com.linkextractor.backend.config.CustomIdGenerator;
import com.linkextractor.backend.models.enums.Hoofdsoort;
import com.linkextractor.backend.models.enums.Ondersoort;

import java.lang.String;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Rechtsbetrekking{
    @Id
    @GenericGenerator(name = "rb_code", strategy =  "com.linkextractor.backend.config.CustomIdGenerator")
    @GeneratedValue(generator = "rb_code")
    @Column(name = "rb_code")
    private String rb_code;

    @Column(name = "rb_naam")
    private String rb_naam;

    @Column(name = "rb_definitie", nullable = true)
    private String rb_definitie;

    @Column(name = "hoofdsoort")
    private Hoofdsoort hoofdsoort;

    @Column(name = "ondersoort")
    private Ondersoort ondersoort;

    @OneToOne(mappedBy = "rechtsbetrekking")
    private Rechtssubject rechtssubject;

    @OneToOne(mappedBy = "rechtsbetrekking_ro")
    private Rechtsobject rechtsobject;

    public Rechtsbetrekking(String rb_code, String rb_naam, Hoofdsoort hoofdsoort,
            Ondersoort ondersoort) {
        this.rb_code = rb_code;
        this.rb_naam = rb_naam;
        this.hoofdsoort = hoofdsoort;
        this.ondersoort = ondersoort;
    }
    
    public Rechtsbetrekking() {
    }

    public String getRb_code() {
        return rb_code;
    }

    public void setRb_code(String rb_code) {
        this.rb_code = rb_code;
    }

    public String getRb_naam() {
        return rb_naam;
    }

    public void setRb_naam(String rb_naam) {
        this.rb_naam = rb_naam;
    }

    public Hoofdsoort getHoofdsoort() {
        return hoofdsoort;
    }

    public void setHoofdsoort(Hoofdsoort hoofdsoort) {
        this.hoofdsoort = hoofdsoort;
    }

    public Ondersoort getOndersoort() {
        return ondersoort;
    }

    public void setOndersoort(Ondersoort ondersoort) {
        this.ondersoort = ondersoort;
    }

    
}
