package com.EventPlanner.repository;

import com.EventPlanner.model.Session;
import com.EventPlanner.model.Sponsor;
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
public interface SponsorRepository extends JpaRepository<Sponsor,Long> {

    @Modifying
    @Query("UPDATE Sponsor s SET s.status = false WHERE s.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT s FROM Sponsor s WHERE s.status = true ORDER BY s.id DESC")
    List<Sponsor> findAllInDesOrderByIdAndStatus();

    @Query("SELECT s FROM Sponsor s WHERE s.status = true ORDER BY s.id DESC")
    Page<Sponsor> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT s FROM Sponsor s JOIN s.sponsorCompany c WHERE c.name LIKE %:searchName% AND s.status = true")
    Page<Sponsor> findSponsorByName(@Param("searchName") String searchName, Pageable page);
}
