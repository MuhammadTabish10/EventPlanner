package com.EventPlanner.repository;

import com.EventPlanner.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);

    @Modifying
    @Query("UPDATE Company c SET c.status = false WHERE c.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT c FROM Company c WHERE c.name LIKE %:searchName% AND c.status = true")
    List<Company> findCompanyByName(@Param("searchName") String searchName);

    @Query("SELECT c FROM Company c WHERE c.status = true ORDER BY c.id DESC")
    List<Company> findAllInDesOrderByIdAndStatus();
}
