package com.EventPlanner.repository;

import com.EventPlanner.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    @Modifying
    @Query("UPDATE Location lo SET lo.status = false WHERE lo.id = :id")
    void setStatusInactive(@Param("id") Long id);
    @Query("SELECT lo FROM Location lo WHERE lo.status = true ORDER BY lo.id DESC")
    List<Location> findAllInDesOrderByIdAndStatus();
}
