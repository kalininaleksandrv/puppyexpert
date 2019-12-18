package com.eyeslessdev.needmypuppyapi.entity;

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
    private int favorite;

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

    @Override
    public void setFavorite(int favorite) {
        this.favorite ++;
    }

    @Override
    public int getFavorite() {
        return favorite;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode()) + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "BreedTest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
