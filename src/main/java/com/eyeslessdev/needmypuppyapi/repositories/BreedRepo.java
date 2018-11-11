package com.eyeslessdev.needmypuppyapi.repositories;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepo extends JpaRepository<Breed, Long> {
}