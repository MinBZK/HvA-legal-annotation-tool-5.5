package com.linkextractor.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.linkextractor.backend.views.Views;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "xmlbron")
public class XMLBron {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "xmlbron_id")
    private int xmlBronId;

    @Column(name = "artikel_naam")
    private String artikelNaam;

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

    public int getXmlBronId() {
        return xmlBronId;
    }

    public void setXmlBronId(int xmlbron_id) {
        this.xmlBronId = xmlbron_id;
    }

    public String getArtikelNaam() {
        return artikelNaam;
    }

    public void setArtikelNaam(String artikel_naam) {
        this.artikelNaam = artikel_naam;
    }

    @Override
    public String toString() {
        return "XMLBron{" +
                "xmlbron_id=" + xmlBronId +
                ", artikel_naam='" + artikelNaam + '\'' +
                ", xmlbron_date=" + xmlbron_date +
                ", link='" + link + '\'' +
                ", rechtssubject=" + rechtssubject +
                ", userDefinitionXMLTables=" + userDefinitionXMLTables +
                '}';
    }
}
