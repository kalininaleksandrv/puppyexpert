package com.eyeslessdev.needmypuppyapi.repositories;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BreedRepo extends JpaRepository<Breed, Long> {

    List<Breed> findAllByOrderByTitle();
}