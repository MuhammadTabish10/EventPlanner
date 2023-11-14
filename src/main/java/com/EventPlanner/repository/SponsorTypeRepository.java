package com.EventPlanner.repository;

import com.EventPlanner.model.SponsorType;
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
public interface SponsorTypeRepository extends JpaRepository<SponsorType, Long> {
    Optional<SponsorType> findByType(String type);

    @Modifying
    @Query("UPDATE SponsorType s SET s.status = false WHERE s.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT s FROM SponsorType s WHERE s.status = true ORDER BY s.id DESC")
    List<SponsorType> findAllInDesOrderByIdAndStatus();

    @Query("SELECT s FROM SponsorType s WHERE s.status = true ORDER BY s.id DESC")
    Page<SponsorType> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT s FROM SponsorType s WHERE s.type LIKE %:searchName% AND s.status = true")
    Page<SponsorType> findSponsorTypeByType(@Param("searchName") String searchName, Pageable page);
}
