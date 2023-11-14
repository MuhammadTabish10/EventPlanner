package com.EventPlanner.repository;

import com.EventPlanner.model.Country;
import com.EventPlanner.model.Currency;
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
public interface CurrencyRepository extends JpaRepository<Currency,Long> {
    Optional<Currency> findByName(String name);

    @Modifying
    @Query("UPDATE Currency c SET c.status = false WHERE c.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT c FROM Currency c WHERE c.status = true ORDER BY c.id DESC")
    List<Currency> findAllInDesOrderByIdAndStatus();

    @Query("SELECT c FROM Currency c WHERE c.status = true ORDER BY c.id DESC")
    Page<Currency> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT c FROM Currency c WHERE c.name LIKE %:searchName% AND c.status = true")
    Page<Currency> findCurrencyByName(@Param("searchName") String searchName, Pageable page);
}
