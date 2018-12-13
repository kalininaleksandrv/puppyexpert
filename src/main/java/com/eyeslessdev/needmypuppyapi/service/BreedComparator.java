package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import org.springframework.context.annotation.Bean;

import java.util.Comparator;

public class BreedComparator implements Comparator <Breed> {

    @Override
    public int compare(Breed breed1, Breed breed2) {
        return (int) (breed1.get_id()-breed2.get_id());
    }
}
