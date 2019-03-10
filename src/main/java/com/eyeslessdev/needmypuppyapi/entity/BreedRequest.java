package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.stereotype.Component;

import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
public class BreedRequest {

    public BreedRequest() {

    }


    //inner parameters
    private int time;
    private int exp;
    private int age;
    private int athlet;
    private int cynologist;
    private int walk;
    private int family;
    private int grummer;

    //outerparrameters
    private int obidience;
    private int guard;
    private int agressive;
    private int active;
    private int size;
    private int care;

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

    @Override
    public String toString() {
        return "BreedResponce{" +
                "obidience=" + obidience +
                ", guard=" + guard +
                ", agressive=" + agressive +
                ", active=" + active +
                ", size=" + size +
                ", care=" + care +
                '}';
    }

    public String getrequestandresponceparamsAsString() {

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
                this.toString() +
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