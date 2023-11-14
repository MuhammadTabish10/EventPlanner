package com.EventPlanner.repository;

import com.EventPlanner.model.Industry;
import com.EventPlanner.model.Questions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    Optional<Questions> findByQuestion(String question);

    @Modifying
    @Query("UPDATE Questions q SET q.status = false WHERE q.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT q FROM Questions q WHERE q.status = true ORDER BY q.id DESC")
    List<Questions> findAllInDesOrderByIdAndStatus();

    @Query("SELECT q FROM Questions q WHERE q.status = true ORDER BY q.id DESC")
    Page<Questions> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT q FROM Questions q WHERE q.question LIKE %:searchName% AND q.status = true")
    Page<Questions> findQuestionsByQuestion(@Param("searchName") String searchName, Pageable page);
}
