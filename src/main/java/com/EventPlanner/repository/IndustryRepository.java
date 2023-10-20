package com.EventPlanner.repository;

import com.EventPlanner.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndustryRepository extends JpaRepository<Industry,Long> {
    Optional<Industry> findByName(String name);
    @Modifying
    @Query("UPDATE Industry i SET i.status = false WHERE i.id = :id")
    void setStatusInactive(@Param("id") Long id);
    @Query("SELECT i FROM Industry i WHERE i.name LIKE %:searchName% AND i.status = true")
    List<Industry> findIndustryByName(@Param("searchName") String searchName);
    @Query("SELECT i FROM Industry i WHERE i.status = true ORDER BY i.id DESC")
    List<Industry> findAllInDesOrderByIdAndStatus();
}
