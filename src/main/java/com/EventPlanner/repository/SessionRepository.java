package com.EventPlanner.repository;

import com.EventPlanner.model.Session;
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
public interface SessionRepository  extends JpaRepository<Session, Long> {
    Optional<Session> findByName(String name);

    @Modifying
    @Query("UPDATE Session s SET s.status = false WHERE s.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT s FROM Session s WHERE s.status = true ORDER BY s.id DESC")
    List<Session> findAllInDesOrderByIdAndStatus();

    @Query("SELECT s FROM Session s WHERE s.status = true ORDER BY s.id DESC")
    Page<Session> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT s FROM Session s WHERE s.name LIKE %:searchName% AND s.status = true")
    Page<Session> findSessionByName(@Param("searchName") String searchName, Pageable page);
}
