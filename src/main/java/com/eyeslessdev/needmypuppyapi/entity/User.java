package com.eyeslessdev.needmypuppyapi.entity;

import com.sun.prism.shader.DrawSemiRoundRect_ImagePattern_AlphaTest_Loader;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="usr", schema = "public")
public class User implements Serializable {

    public User() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String externalid;
    private String name;
    private String userpic;

    @NotNull
    @Size(min = 3, max = 256)
    private String password;

    @NotNull
    @Email
    private String email;
    private String locale;
    private Long lastvisit;
    private Long registrationtime;
    private int isregistered;

    @ElementCollection (targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUsername(){
        return name;
    }


    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExternalid() {
        return externalid;
    }

    public void setExternalid(String externalid) {
        this.externalid = externalid;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }




}
