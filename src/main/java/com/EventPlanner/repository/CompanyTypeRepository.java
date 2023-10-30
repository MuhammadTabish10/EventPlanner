package com.EventPlanner.repository;

import com.EventPlanner.model.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
    Optional<CompanyType> findByType(String type);

    @Modifying
    @Query("UPDATE CompanyType ct SET ct.status = false WHERE ct.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT ct FROM CompanyType ct WHERE ct.type LIKE %:searchName% AND ct.status = true")
    List<CompanyType> findCompanyTypeByType(@Param("searchName") String searchName);

    @Query("SELECT ct FROM CompanyType ct WHERE ct.status = true ORDER BY ct.id DESC")
    List<CompanyType> findAllInDesOrderByIdAndStatus();
}
