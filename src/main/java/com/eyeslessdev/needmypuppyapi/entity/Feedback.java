package com.eyeslessdev.needmypuppyapi.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
        @Size(min = 4, max = 64)
        private String title;

        @NotNull
        @Size(min = 5, max = 500)
        private String description;

        @Email
        private String email;

        private String username;

        private Long commenttime;

        private String commenttimestr;

        private Integer ismoderated;

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

        public String getUsername() {return username;}

        public void setUsername(String username) {this.username = username;}

        public Long getCommenttime() {return commenttime;}

        public void setCommenttime(Long commenttime) {this.commenttime = commenttime;}

        public String getCommenttimestr() {return commenttimestr;}

        public void setCommenttimestr(String commenttimestr) {this.commenttimestr = commenttimestr;}

        public Integer isModerated() {return ismoderated;}

        public void setIsModerated(Integer isModerated) {this.ismoderated = isModerated;}
    }