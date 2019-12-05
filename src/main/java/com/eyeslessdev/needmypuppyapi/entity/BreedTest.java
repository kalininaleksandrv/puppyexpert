package com.eyeslessdev.needmypuppyapi.entity;

import com.eyeslessdev.needmypuppyapi.entity.Breed;

public class BreedTest extends Breed {
    public BreedTest() {
    }

    public BreedTest(Long i, String dog1, String firstdog) {
        this.id = i;
        this.title = dog1;
        this.description = firstdog;
    }

    private Long id;
    private String title;
    private String description;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
