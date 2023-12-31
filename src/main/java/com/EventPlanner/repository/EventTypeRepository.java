package com.EventPlanner.repository;

import com.EventPlanner.model.Event;
import com.EventPlanner.model.EventType;
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
public interface EventTypeRepository extends JpaRepository<EventType, Long> {

    Optional<EventType> findByName(String name);

    @Modifying
    @Query("UPDATE EventType et SET et.status = false WHERE et.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT et FROM EventType et WHERE et.status = true ORDER BY et.id DESC")
    List<EventType> findAllInDesOrderByIdAndStatus();

    @Query("SELECT et FROM EventType et WHERE et.status = true ORDER BY et.id DESC")
    Page<EventType> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT et FROM EventType et WHERE et.name LIKE %:searchName% AND et.status = true")
    Page<EventType> findEventTypeByName(@Param("searchName") String searchName, Pageable page);
}
