//package com.linkextractor.models;
//
//import com.linkextractor.models.enums.Hoofdsoort;
//import com.linkextractor.models.enums.Ondersoort;
//
//import java.lang.String;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//
//@Entity
//public class Rechtsbetrekking{
//
//    @Id
//    @Column(name = "rb_code")
//    private String rb_code;
//
//    @Column(name = "rb_naam")
//    private String rb_naam;
//
//    @Column(name = "hoofdsoort")
//    private Hoofdsoort hoofdsoort;
//
//    @Column(name = "ondersoort")
//    private Ondersoort ondersoort;
//
//    public Rechtsbetrekking(String rb_code, String rb_naam, Hoofdsoort hoofdsoort,
//            Ondersoort ondersoort) {
//        this.rb_code = rb_code;
//        this.rb_naam = rb_naam;
//        this.hoofdsoort = hoofdsoort;
//        this.ondersoort = ondersoort;
//    }
//
//
//    public Rechtsbetrekking() {
//    }
//
//
//    public String getRb_code() {
//        return rb_code;
//    }
//
//    public void setRb_code(String rb_code) {
//        this.rb_code = rb_code;
//    }
//
//    public String getRb_naam() {
//        return rb_naam;
//    }
//
//    public void setRb_naam(String rb_naam) {
//        this.rb_naam = rb_naam;
//    }
//
//    public Hoofdsoort getHoofdsoort() {
//        return hoofdsoort;
//    }
//
//    public void setHoofdsoort(Hoofdsoort hoofdsoort) {
//        this.hoofdsoort = hoofdsoort;
//    }
//
//    public Ondersoort getOndersoort() {
//        return ondersoort;
//    }
//
//    public void setOndersoort(Ondersoort ondersoort) {
//        this.ondersoort = ondersoort;
//    }
//
//
//}
