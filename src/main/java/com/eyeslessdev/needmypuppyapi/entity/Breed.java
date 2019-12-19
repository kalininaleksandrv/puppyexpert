package com.eyeslessdev.needmypuppyapi.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="breeds", schema = "public")
public class Breed implements Serializable {

    public Breed() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String descriptionfull;
    private String imageresourceid;
    private String imageresourceidbig;
    private int obidience;
    private int guard;
    private int agressive;
    private int active;
    private int hardy;
    private int size;
    private int care;
    private String hunt;
    private String weblinc;
    private String weblincwiki;
    private int fciid;
    private String hair;
    private String blackorwhite;
    private String noalergy;
    private int favorite;
    private String comment;
    private int forchild;
    private int forcompany;
    private int forrunning;
    private int forhunt;
    private int forobidience;
    private int forguardterritory;
    private int forzks;
    private int foragility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionfull() {
        return descriptionfull;
    }

    public void setDescriptionfull(String descriptionfull) {
        this.descriptionfull = descriptionfull;
    }

    public String getImageresourceid() {
        return imageresourceid;
    }

    public void setImageresourceid(String imageresourceid) {
        this.imageresourceid = imageresourceid;
    }

    public String getImageresourceidbig() {
        return imageresourceidbig;
    }

    public void setImageresourceidbig(String imageresourceidbig) {
        this.imageresourceidbig = imageresourceidbig;
    }

    public int getObidience() {
        return obidience;
    }

    public void setObidience(int obidience) {
        this.obidience = obidience;
    }

    public int getGuard() {
        return guard;
    }

    public void setGuard(int guard) {
        this.guard = guard;
    }

    public int getAgressive() {
        return agressive;
    }

    public void setAgressive(int agressive) {
        this.agressive = agressive;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getHardy() {
        return hardy;
    }

    public void setHardy(int hardy) {
        this.hardy = hardy;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCare() {
        return care;
    }

    public void setCare(int care) {
        this.care = care;
    }

    public String getHunt() {
        return hunt;
    }

    public void setHunt(String hunt) {
        this.hunt = hunt;
    }

    public String getWeblinc() {
        return weblinc;
    }

    public void setWeblinc(String weblinc) {
        this.weblinc = weblinc;
    }

    public String getWeblincwiki() {
        return weblincwiki;
    }

    public void setWeblincwiki(String weblincwiki) {
        this.weblincwiki = weblincwiki;
    }

    public int getFciid() {
        return fciid;
    }

    public void setFciid(int fciid) {
        this.fciid = fciid;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getBlackorwhite() {
        return blackorwhite;
    }

    public void setBlackorwhite(String blackorwhite) {
        this.blackorwhite = blackorwhite;
    }

    public String getNoalergy() {
        return noalergy;
    }

    public void setNoalergy(String noalergy) {
        this.noalergy = noalergy;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public int getForrunning() {
        return forrunning;
    }

    public void setForrunning(int forrunning) {
        this.forrunning = forrunning;
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

    public int getForguardterritory() {
        return forguardterritory;
    }

    public void setForguardterritory(int forguardterritory) {
        this.forguardterritory = forguardterritory;
    }

    public int getForzks() {
        return forzks;
    }

    public void setForzks(int forzks) {
        this.forzks = forzks;
    }

    public int getForagility() {
        return foragility;
    }

    public void setForagility(int foragility) {
        this.foragility = foragility;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Breed other = (Breed) obj;
        if (!id.equals(other.getId()))
            return false;
        return title.equals(other.getTitle());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode()) + ((description == null) ? 0 : description.hashCode());
        return result;
    }

}
