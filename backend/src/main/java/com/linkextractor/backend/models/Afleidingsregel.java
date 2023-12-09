package com.linkextractor.backend.models;

import java.util.ArrayList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Afleidingsregel {
    @Id
    @Column(name = "ar_id")
    private int ar_id;

    @Column(name = "ar_naam")
    private String ar_naam;

    @OneToMany(mappedBy = "afleidingsregel_var")
    private ArrayList<Variabele> variabelen;

    @OneToMany(mappedBy = "afleidingsregel_op")
    private ArrayList<Operator> operators;

    public Afleidingsregel(int ar_id, String ar_naam) {
        this.ar_id = ar_id;
        this.ar_naam = ar_naam;
    }

    public Afleidingsregel() {
    }

    public int getAr_id() {
        return ar_id;
    }

    public void setAr_id(int ar_id) {
        this.ar_id = ar_id;
    }

    public String getAr_naam() {
        return ar_naam;
    }

    public void setAr_naam(String ar_name) {
        this.ar_naam = ar_name;
    }

    
}
