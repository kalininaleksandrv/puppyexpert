package com.eyeslessdev.needmypuppyapi.repositories;

import com.eyeslessdev.needmypuppyapi.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

    List<Feedback> findTop10ByDogidOrderByCommenttimeDesc(Long id);

    List<Feedback> findByIsmoderated(int i);

    @Transactional
    @Modifying
    @Query("UPDATE Feedback f set f.ismoderated = :ismoderated where f.id IN :ids")
    Integer updateFeedbackById(@Param("ismoderated") Integer ismoderated, @Param("ids") Collection<Long> ids);

    @Transactional
    @Modifying
    @Query("DELETE Feedback f where f.id IN :ids")
    Integer deleteFeedbackById(@Param("ids") Collection<Long> ids);
}
