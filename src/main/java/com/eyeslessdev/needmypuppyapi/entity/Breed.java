package com.eyeslessdev.needmypuppyapi.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="breeds", schema = "public")
public class Breed implements Serializable {

    public Breed() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _id;
    private String title;
    private String description;
    private String description_full;
    private String image_resource_id;
    private String image_resource_id_big;
    private int obidience;
    private int guard;
    private int agressive;
    private int active;
    private int hardy;
    private int size;
    private int care;
    private String hunt;
    private String weblinc;
    private String weblinc_wiki;
    private int fciid;
    private String hair;
    private String blackorwhite;
    private String noalergy;
    private int favorite;
    private String comment;
    private int for_child;
    private int for_company;
    private int for_running;
    private int for_hunt;
    private int for_obidience;
    private int for_guardterritory;
    private int for_zks;
    private int for_agility;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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

    public String getDescription_full() {
        return description_full;
    }

    public void setDescription_full(String description_full) {
        this.description_full = description_full;
    }


    public String getImage_resource_id() {
        return image_resource_id;
    }

    public void setImage_resource_id(String image_resource_id) {
        this.image_resource_id = image_resource_id;
    }

    public String getImage_resource_id_big() {
        return image_resource_id_big;
    }

    public void setImage_resource_id_big(String image_resource_id_big) {
        this.image_resource_id_big = image_resource_id_big;
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


    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getAgressive() {
        return agressive;
    }

    public void setAgressive(int agressive) {
        this.agressive = agressive;
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

    public String getWeblinc_wiki() {
        return weblinc_wiki;
    }

    public void setWeblinc_wiki(String weblinc_wiki) {
        this.weblinc_wiki = weblinc_wiki;
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

    public int getFor_child() {
        return for_child;
    }

    public void setFor_child(int for_child) {
        this.for_child = for_child;
    }

    public int getFor_company() {
        return for_company;
    }

    public void setFor_company(int for_company) {
        this.for_company = for_company;
    }

    public int getFor_running() {
        return for_running;
    }

    public void setFor_running(int for_running) {
        this.for_running = for_running;
    }

    public int getFor_hunt() {
        return for_hunt;
    }

    public void setFor_hunt(int for_hunt) {
        this.for_hunt = for_hunt;
    }

    public int getFor_obidience() {
        return for_obidience;
    }

    public void setFor_obidience(int for_obidience) {
        this.for_obidience = for_obidience;
    }

    public int getFor_guardterritory() {
        return for_guardterritory;
    }

    public void setFor_guardterritory(int for_guardterritory) {
        this.for_guardterritory = for_guardterritory;
    }

    public int getFor_zks() {
        return for_zks;
    }

    public void setFor_zks(int for_zks) {
        this.for_zks = for_zks;
    }

    public int getFor_agility() {
        return for_agility;
    }

    public void setFor_agility(int for_agility) {
        this.for_agility = for_agility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Breed)) return false;
        Breed breed = (Breed) o;
        return Objects.equals(_id, breed._id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + description.length(); result = prime * result +
                ((title == null) ? 0 : title.hashCode()); return result;
    }

}
