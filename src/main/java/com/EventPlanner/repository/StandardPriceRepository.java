package com.EventPlanner.repository;

import com.EventPlanner.model.StandardPrice;
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
public interface StandardPriceRepository extends JpaRepository<StandardPrice,Long> {
    Optional<StandardPrice> findByTicketName(String ticketName);

    @Modifying
    @Query("UPDATE StandardPrice sp SET sp.status = false WHERE sp.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT sp FROM StandardPrice sp WHERE sp.status = true ORDER BY sp.id DESC")
    List<StandardPrice> findAllInDesOrderByIdAndStatus();

    @Query("SELECT sp FROM StandardPrice sp WHERE sp.status = true ORDER BY sp.id DESC")
    Page<StandardPrice> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT sp FROM StandardPrice sp WHERE sp.ticketName LIKE %:searchName% AND sp.status = true")
    Page<StandardPrice> findStandardPriceByTicketName(@Param("searchName") String searchName, Pageable page);
}
