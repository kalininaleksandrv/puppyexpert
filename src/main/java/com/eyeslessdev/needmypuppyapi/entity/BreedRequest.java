package com.eyeslessdev.needmypuppyapi.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="breedrequests", schema = "public")
public class BreedRequest implements Serializable {

    public BreedRequest() {

    }

    BreedRequest(int time, int exp, int age, int athlet, int cynologist, int walk,
                 int family, int grummer, int foragility, int forchild, int forcompany,
                 int forguardter, int forhunt, int forobidience, int forruning, int forzks,
                 String hairsize, String blackorwhite, int sizeconstraintmin,
                 int sizeconstraintmax, String rare) {

        this.thetime = time;
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
    private int thetime;
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
                "time=" + thetime +
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    int getThetime() {
        return thetime;
    }

    public void setThetime(int thetime) {
        this.thetime = thetime;
    }

    int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    int getAthlet() {
        return athlet;
    }

    public void setAthlet(int athlet) {
        this.athlet = athlet;
    }

    int getCynologist() {
        return cynologist;
    }

    public void setCynologist(int cynologist) {
        this.cynologist = cynologist;
    }

    int getWalk() {
        return walk;
    }

    public void setWalk(int walk) {
        this.walk = walk;
    }

    int getFamily() {
        return family;
    }

    public void setFamily(int family) {
        this.family = family;
    }

    int getGrummer() {
        return grummer;
    }

    public void setGrummer(int grummer) {
        this.grummer = grummer;
    }

    public int getForagility() {
        return foragility;
    }

    public void setForagility(int foragility) {
        this.foragility = foragility;
    }

    public int getForchild() {
        return forchild;
    }

    public void setForchild(int forchild) {
        this.forchild = forchild;
    }

    public int getForcompany() {
        return forcompany;
    }

    public void setForcompany(int forcompany) {
        this.forcompany = forcompany;
    }

    public int getForguardter() {
        return forguardter;
    }

    public void setForguardter(int forguardter) {
        this.forguardter = forguardter;
    }

    public int getForhunt() {
        return forhunt;
    }

    public void setForhunt(int forhunt) {
        this.forhunt = forhunt;
    }

    public int getForobidience() {
        return forobidience;
    }

    public void setForobidience(int forobidience) {
        this.forobidience = forobidience;
    }

    public int getForruning() {
        return forruning;
    }

    public void setForruning(int forruning) {
        this.forruning = forruning;
    }

    public int getForzks() {
        return forzks;
    }

    public void setForzks(int forzks) {
        this.forzks = forzks;
    }

    String getHairsize() {
        return hairsize;
    }

    public void setHairsize(String hairsize) {
        this.hairsize = hairsize;
    }

    String getBlackorwhite() {
        return blackorwhite;
    }

    public void setBlackorwhite(String blackorwhite) {
        this.blackorwhite = blackorwhite;
    }

    public int getSizeconstraintmin() {
        return sizeconstraintmin;
    }

    public void setSizeconstraintmin(int sizeconstraintmin) {
        this.sizeconstraintmin = sizeconstraintmin;
    }

    public int getSizeconstraintmax() {
        return sizeconstraintmax;
    }

    public void setSizeconstraintmax(int sizeconstraintmax) {
        this.sizeconstraintmax = sizeconstraintmax;
    }

    String getRare() {
        return rare;
    }

    public void setRare(String rare) {
        this.rare = rare;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BreedRequest breedRequest = new BreedRequest();

        public BreedRequest build() {
            BreedRequest result = breedRequest;
            breedRequest = new BreedRequest();
            return result;
        }


        public Builder time(int time) {
            breedRequest.thetime = time;
            return this;
        }
        public Builder exp(int exp) {
            breedRequest.exp = exp;
            return this;
        }
        public Builder age(int age) {
            breedRequest.age = age;
            return this;
        }
        public Builder athlet(int athlet) {
            breedRequest.athlet = athlet;
            return this;
        }
        public Builder cynologist(int cynologist) {
            breedRequest.cynologist = cynologist;
            return this;
        }
        public Builder walk(int walk) {
            breedRequest.walk = walk;
            return this;
        }
        public Builder family(int family) {
            breedRequest.family = family;
            return this;
        }
        public Builder grummer(int grummer) {
            breedRequest.grummer = grummer;
            return this;
        }
        public Builder foragility(int foragility) {
            breedRequest.foragility = foragility;
            return this;
        }
        public Builder forchild(int forchild) {
            breedRequest.forchild = forchild;
            return this;
        }
        public Builder forcompany(int forcompany) {
            breedRequest.forcompany = forcompany;
            return this;
        }
        public Builder forguardter(int forguardter) {
            breedRequest.forguardter = forguardter;
            return this;
        }
        public Builder forhunt(int forhunt) {
            breedRequest.forhunt = forhunt;
            return this;
        }
        public Builder forobidience(int forobidience) {
            breedRequest.forobidience = forobidience;
            return this;
        }
        public Builder forruning(int forruning) {
            breedRequest.forruning = forruning;
            return this;
        }
        public Builder forzks(int forzks) {
            breedRequest.forzks = forzks;
            return this;
        }
        public Builder hairsize(String hairsize) {
            breedRequest.hairsize = hairsize;
            return this;
        }
        public Builder blackorwhite(String blackorwhite) {
            breedRequest.blackorwhite = blackorwhite;
            return this;
        }
        public Builder sizeconstraintmin(int sizeconstraintmin) {
            breedRequest.sizeconstraintmin = sizeconstraintmin;
            return this;
        }
        public Builder sizeconstraintmax(int sizeconstraintmax) {
            breedRequest.sizeconstraintmax = sizeconstraintmax;
            return this;
        }
        public Builder rare(String rare) {
            breedRequest.rare = rare;
            return this;
        }


    }

}