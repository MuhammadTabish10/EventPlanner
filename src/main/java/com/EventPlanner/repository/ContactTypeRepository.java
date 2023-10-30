package com.EventPlanner.repository;

import com.EventPlanner.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {

    Optional<ContactType> findByType(String name);

    @Modifying
    @Query("UPDATE ContactType ct SET ct.status = false WHERE ct.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT ct FROM ContactType ct WHERE ct.type LIKE %:searchName% AND ct.status = true")
    List<ContactType> findContactTypeByType(@Param("searchName") String searchName);

    @Query("SELECT ct FROM ContactType ct WHERE ct.status = true ORDER BY ct.id DESC")
    List<ContactType> findAllInDesOrderByIdAndStatus();
}
