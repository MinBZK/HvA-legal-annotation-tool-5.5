package com.linkextractor.backend.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class XMLBron {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "xmlbron_id")
    private int xmlbron_id;

    @Column(name = "artikel_naam")
    private String artikel_naam;

    @Column(name = "link")
    private String link;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "XMLbron_definitie_junction",
        joinColumns = {@JoinColumn(name="xmlbron_id")},
        inverseJoinColumns = {@JoinColumn(name="definitie_id")}
    )
    private Set<Definitie> definities;

    @OneToOne(mappedBy = "xmlbron")
    private Rechtssubject rechtssubject;


    public XMLBron(int xmlbron_id, String artikel_naam, String link){
        this.xmlbron_id = xmlbron_id;
        this.artikel_naam = artikel_naam;
        this.link = link;
    }

    public XMLBron(){

    }

    

    // public XMLBron() {
    //     super();
    //     definities = new HashSet<>();
    //     rechtssubject = new Rechtssubject();
    // }


    public int getXmlbron_id() {
        return xmlbron_id;
    }

    public void setXmlbron_id(int xmlbron_id) {
        this.xmlbron_id = xmlbron_id;
    }

    public String getArtikel_naam() {
        return artikel_naam;
    }

    public void setArtikel_naam(String artikel_naam) {
        this.artikel_naam = artikel_naam;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<Definitie> getDefinities() {
        return definities;
    }

    public void setDefinities(Set<Definitie> definities) {
        this.definities = definities;
    }
}
