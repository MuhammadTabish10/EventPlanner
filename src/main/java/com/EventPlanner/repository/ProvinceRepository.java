package com.EventPlanner.repository;

import com.EventPlanner.model.Industry;
import com.EventPlanner.model.Province;
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
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByName(String name);

    @Modifying
    @Query("UPDATE Province p SET p.status = false WHERE p.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT p FROM Province p WHERE p.status = true ORDER BY p.id DESC")
    List<Province> findAllInDesOrderByIdAndStatus();

    @Query("SELECT p FROM Province p WHERE p.status = true ORDER BY p.id DESC")
    Page<Province> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT p FROM Province p WHERE p.name LIKE %:searchName% AND p.status = true")
    Page<Province> findProvinceByName(@Param("searchName") String searchName, Pageable page);
}
