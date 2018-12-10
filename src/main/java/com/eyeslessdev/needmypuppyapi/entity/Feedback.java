package com.eyeslessdev.needmypuppyapi.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

    @Entity
    @Table(name="feedback", schema = "public")
    public class Feedback implements Serializable {

        public Feedback() {
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;


        private Long dogid;

        @NotNull
        private String title;

        @NotNull
        private String description;

        @NotNull
        @Email
        private String email;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getDogid() {
            return dogid;
        }

        public void setDogid(Long dogid) {
            this.dogid = dogid;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }