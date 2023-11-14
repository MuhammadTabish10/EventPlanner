package com.EventPlanner.repository;

import com.EventPlanner.model.EmailTemplate;
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
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    Optional<EmailTemplate> findByName(String name);

    @Modifying
    @Query("UPDATE EmailTemplate et SET et.status = false WHERE et.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT et FROM EmailTemplate et WHERE et.status = true ORDER BY et.id DESC")
    List<EmailTemplate> findAllInDesOrderByIdAndStatus();

    @Query("SELECT et FROM EmailTemplate et WHERE et.status = true ORDER BY et.id DESC")
    Page<EmailTemplate> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT et FROM EmailTemplate et WHERE et.name LIKE %:searchName% AND et.status = true")
    Page<EmailTemplate> findEmailTemplateByName(@Param("searchName") String searchName, Pageable page);
}
