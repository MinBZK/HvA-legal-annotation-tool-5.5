package com.linkextractor.backend.models;

import jakarta.persistence.*;

@Entity
public class UserDefinitionXMLTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "link_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "definitie_id")
    private Definitie definitie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "xmlbron_id")
    private XMLBron xmlbron;

    public int getId() {
        return id;
    }

    public void setId(int linkId) {
        this.id = linkId;
    }

    public Definitie getDefinitie() {
        return definitie;
    }

    public void setDefinitie(Definitie definitie) {
        this.definitie = definitie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public XMLBron getXmlBron() {
        return xmlbron;
    }

    public void setXmlBron(XMLBron xmlbron) {
        this.xmlbron = xmlbron;
    }
}

