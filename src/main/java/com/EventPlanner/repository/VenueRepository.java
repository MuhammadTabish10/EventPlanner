package com.EventPlanner.repository;

import com.EventPlanner.model.EventType;
import com.EventPlanner.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    Optional<Venue> findByName(String name);

    @Modifying
    @Query("UPDATE Venue v SET v.status = false WHERE v.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT v FROM Venue v WHERE v.name LIKE %:searchName% AND v.status = true")
    List<Venue> findVenueByName(@Param("searchName") String searchName);

    @Query("SELECT v FROM Venue v WHERE v.status = true ORDER BY v.id DESC")
    List<Venue> findAllInDesOrderByIdAndStatus();
}
