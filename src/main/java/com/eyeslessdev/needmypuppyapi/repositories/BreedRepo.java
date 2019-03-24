package com.eyeslessdev.needmypuppyapi.repositories;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BreedRepo extends JpaRepository<Breed, Long>, JpaSpecificationExecutor<Breed> {

    List<Breed> findAllByOrderByTitle();

    Breed findBreedByid(long id);

    Optional<List<Breed>> findByBlackorwhite(final String s);

    List<Breed> findAll(Specification spec);
}

