package com.eyeslessdev.needmypuppyapi.repositories;

import com.eyeslessdev.needmypuppyapi.entity.BreedRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRequestRepo extends JpaRepository <BreedRequest, Long> {
}
