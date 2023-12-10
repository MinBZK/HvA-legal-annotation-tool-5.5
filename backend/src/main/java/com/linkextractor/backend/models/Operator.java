package com.linkextractor.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Operator {
    @Id
    @Column(name = "op_id")
    private int op_id;

    @Column(name = "op_naam")
    private String op_naam;

    @Column(name = "op_type")
    private String op_type;

    @Column(name = "op_definitie",nullable = true)
    private String op_definitie;

    @ManyToOne
    @JoinColumn(name = "ar_id", nullable = false)
    private Afleidingsregel afleidingsregel_op;

    public Operator(int op_id, String op_naam, String op_type) {
        this.op_id = op_id;
        this.op_naam = op_naam;
        this.op_type = op_type;
    }

    public Operator() {
    }

    public int getOp_id() {
        return op_id;
    }

    public void setOp_id(int op_id) {
        this.op_id = op_id;
    }

    public String getOp_naam() {
        return op_naam;
    }

    public void setOp_naam(String op_naam) {
        this.op_naam = op_naam;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    
}
