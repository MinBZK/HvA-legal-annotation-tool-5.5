package com.linkextractor.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class XMLBron {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "xmlbron_id")
    private int xmlbron_id;

    @Column(name = "artikel_naam")
    private String artikel_naam;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "xmlbron_date")
    private LocalDate xmlbron_date;

    @Column(name = "link")
    private String link;

    @OneToOne(mappedBy = "xmlbron")
    private Rechtssubject rechtssubject;

    @OneToMany(mappedBy = "xmlbron")
    private Set<UserDefinitionXMLTable> userDefinitionXMLTables;

    public XMLBron() {

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDate getDate() {
        return xmlbron_date;
    }

    public void setDate(LocalDate date) {
        this.xmlbron_date = date;
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

    @Override
    public String toString() {
        return "XMLBron{" +
                "xmlbron_id=" + xmlbron_id +
                ", artikel_naam='" + artikel_naam + '\'' +
                ", xmlbron_date=" + xmlbron_date +
                ", link='" + link + '\'' +
                ", rechtssubject=" + rechtssubject +
                ", userDefinitionXMLTables=" + userDefinitionXMLTables +
                '}';
    }
}
