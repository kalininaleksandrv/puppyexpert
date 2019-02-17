package com.eyeslessdev.needmypuppyapi.repositories;

import com.eyeslessdev.needmypuppyapi.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BreedRepo extends JpaRepository<Breed, Long> {

    List<Breed> findAllByOrderByTitle();

    Breed findBreedByid(long id);

    Optional<List<Breed>> findByBlackorwhite(final String s);

    //SELECT * FROM public.breeds WHERE forhunt = 1 and forobidience = 1;

    @Query(value = "SELECT * FROM public.breeds WHERE forhunt = :forhunt and forobidience = :forobidience" , nativeQuery = true)
    Optional<List<Breed>> findQuery(@Param("forhunt") int forhunt, @Param("forobidience") int forobidence);
}

