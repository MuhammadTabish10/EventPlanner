package com.EventPlanner.repository;

import com.EventPlanner.model.Contact;
import com.EventPlanner.model.ContactType;
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
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {

    Optional<ContactType> findByType(String name);

    @Modifying
    @Query("UPDATE ContactType ct SET ct.status = false WHERE ct.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT ct FROM ContactType ct WHERE ct.status = true ORDER BY ct.id DESC")
    List<ContactType> findAllInDesOrderByIdAndStatus();

    @Query("SELECT ct FROM ContactType ct WHERE ct.status = true ORDER BY ct.id DESC")
    Page<ContactType> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT ct FROM ContactType ct WHERE ct.type LIKE %:searchName% AND ct.status = true")
    Page<ContactType> findContactTypeByType(@Param("searchName") String searchName, Pageable page);
}
