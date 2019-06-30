package com.eyeslessdev.needmypuppyapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="usr", schema = "public")
public class User implements Serializable {

    @Id
    private String id;
    private String name;
    private String userpic;
    private String email;
    private String locale;
    private Long lastvisit;
    private Long registrationtime;
    private int isregistered;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Long getLastvisit() {
        return lastvisit;
    }

    public void setLastvisit(Long lastvisit) {
        this.lastvisit = lastvisit;
    }

    public Long getRegistrationtime() {
        return registrationtime;
    }

    public void setRegistrationtime(Long registrationtime) {
        this.registrationtime = registrationtime;
    }

    public int getIsregistered() {
        return isregistered;
    }

    public void setIsregistered(int isregistered) {
        this.isregistered = isregistered;
    }


}
