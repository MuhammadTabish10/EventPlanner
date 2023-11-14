package com.EventPlanner.repository;

import com.EventPlanner.model.Tag;
import com.EventPlanner.model.TicketType;
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
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
    Optional<TicketType> findByType(String type);

    @Modifying
    @Query("UPDATE TicketType t SET t.status = false WHERE t.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT t FROM TicketType t WHERE t.status = true ORDER BY t.id DESC")
    List<TicketType> findAllInDesOrderByIdAndStatus();

    @Query("SELECT t FROM TicketType t WHERE t.status = true ORDER BY t.id DESC")
    Page<TicketType> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT t FROM TicketType t WHERE t.type LIKE %:searchName% AND t.status = true")
    Page<TicketType> findTicketTypeByType(@Param("searchName") String searchName, Pageable page);
}
