package com.EventPlanner.repository;

import com.EventPlanner.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    Optional<Event> findByName(String name);

    @Modifying
    @Query("UPDATE Event e SET e.status = false WHERE e.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT e FROM Event e WHERE e.name LIKE %:searchName% AND e.status = true")
    List<Event> findEventByName(@Param("searchName") String searchName);

    @Query("SELECT e FROM Event e WHERE e.status = true ORDER BY e.id DESC")
    List<Event> findAllInDesOrderByIdAndStatus();
}
