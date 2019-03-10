package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="breedrequests", schema = "public")
public class BreedRequest implements Serializable {

    public BreedRequest() {

    }

    public BreedRequest(int time, int exp, int age, int athlet, int cynologist, int walk,
                        int family, int grummer, int foragility, int forchild, int forcompany,
                        int forguardter, int forhunt, int forobidience, int forruning, int forzks,
                        String hairsize, String blackorwhite, int sizeconstraintmin,
                        int sizeconstraintmax, String rare) {

        this.time = time;
        this.exp = exp;
        this.age = age;
        this.athlet = athlet;
        this.cynologist = cynologist;
        this.walk = walk;
        this.family = family;
        this.grummer = grummer;
        this.foragility = foragility;
        this.forchild = forchild;
        this.forcompany = forcompany;
        this.forguardter = forguardter;
        this.forhunt = forhunt;
        this.forobidience = forobidience;
        this.forruning = forruning;
        this.forzks = forzks;
        this.hairsize = hairsize;
        this.blackorwhite = blackorwhite;
        this.sizeconstraintmin = sizeconstraintmin;
        this.sizeconstraintmax = sizeconstraintmax;
        this.rare = rare;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //inner parameters
    private int time;
    private int exp;
    private int age;
    private int athlet;
    private int cynologist;
    private int walk;
    private int family;
    private int grummer;

    //constraintparameters
    private int foragility;
    private int forchild;
    private int forcompany;
    private int forguardter;
    private int forhunt;
    private int forobidience;
    private int forruning;
    private int forzks;

    //exterier and other
    private String hairsize;
    private String blackorwhite;
    private int sizeconstraintmin;
    private int sizeconstraintmax;
    private String rare;


    public String getRequestParamsAsString() {

        return "-------\n" +
                "BreedRequest{" +
                "time=" + time +
                ", exp=" + exp +
                ", age=" + age +
                ", athlet=" + athlet +
                ", cynologist=" + cynologist +
                ", walk=" + walk +
                ", family=" + family +
                ", grummer=" + grummer +
                '}' +
                "\n" +
                "--------\n" +
                "Constraints{" +
                "foragility=" + foragility +
                ", forchild=" + forchild +
                ", forcompany=" + forcompany +
                ", forguardter=" + forguardter +
                ", forhunt=" + forhunt +
                ", forobidience=" + forobidience +
                ", forruning=" + forruning +
                ", forzks=" + forzks +
                '}' +
                "\n" +
                "--------\n" +
                "Exterier{" +
                "hairsize=" + hairsize +
                ", blackorwhite=" + blackorwhite +
                ", sizeconstraintmin=" + sizeconstraintmin +
                ", sizeconstraintmax=" + sizeconstraintmax +
                ", rare=" + rare +
                '}' +
                "\n"
                ;
    }


}