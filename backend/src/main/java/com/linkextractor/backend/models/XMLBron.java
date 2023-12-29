package com.linkextractor.backend.models;

import jakarta.persistence.*;

import java.util.Set;

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

    @OneToOne(mappedBy = "xmlbron")
    private Rechtssubject rechtssubject;

    @OneToMany(mappedBy = "xmlbron")
    private Set<UserDefinitionXMLTable> userDefinitionXMLTables;

    public XMLBron(int xmlbron_id, String artikel_naam, String link) {
        this.xmlbron_id = xmlbron_id;
        this.artikel_naam = artikel_naam;
        this.link = link;
    }

    public XMLBron() {

    }

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
}
