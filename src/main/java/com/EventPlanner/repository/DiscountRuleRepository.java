package com.EventPlanner.repository;

import com.EventPlanner.model.DiscountRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRuleRepository extends JpaRepository<DiscountRule, Long> {
    @Modifying
    @Query("UPDATE DiscountRule dr SET dr.status = false WHERE dr.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT dr FROM DiscountRule dr WHERE dr.status = true ORDER BY dr.id DESC")
    List<DiscountRule> findAllInDesOrderByIdAndStatus();

    @Query("SELECT dr FROM DiscountRule dr WHERE dr.status = true ORDER BY dr.id DESC")
    Page<DiscountRule> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT dr FROM DiscountRule dr WHERE dr.discountCode LIKE %:searchName% AND dr.status = true")
    Page<DiscountRule> findDiscountRuleByDiscountCode(@Param("searchName") String searchName, Pageable page);
}
