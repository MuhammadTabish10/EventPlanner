package com.EventPlanner.repository;

import com.EventPlanner.model.Company;
import com.EventPlanner.model.Contact;
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
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByCustomer(String name);

    @Modifying
    @Query("UPDATE Contact c SET c.status = false WHERE c.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT c FROM Contact c WHERE c.status = true ORDER BY c.id DESC")
    List<Contact> findAllInDesOrderByIdAndStatus();

    @Query("SELECT c FROM Contact c WHERE c.status = true ORDER BY c.id DESC")
    Page<Contact> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT c FROM Contact c WHERE c.customer LIKE %:searchName% AND c.status = true")
    Page<Contact> findContactByCustomer(@Param("searchName") String searchName, Pageable page);
}
