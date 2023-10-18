package com.EventPlanner.repository;

import com.EventPlanner.model.Currency;
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
    @Query("SELECT c FROM Currency c WHERE c.name LIKE %:searchName%")
    List<Currency> findCurrencyByName(@Param("searchName") String searchName);

    @Modifying
    @Query("UPDATE Currency c SET c.status = false WHERE c.id = :id")
    void setStatusInactive(@Param("id") Long id);
}
