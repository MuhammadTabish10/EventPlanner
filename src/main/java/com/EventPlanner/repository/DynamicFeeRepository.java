package com.EventPlanner.repository;

import com.EventPlanner.model.DynamicFee;
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
public interface DynamicFeeRepository extends JpaRepository<DynamicFee,Long> {
    Optional<DynamicFee> findByTicketName(String ticketName);

    @Modifying
    @Query("UPDATE DynamicFee df SET df.status = false WHERE df.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT df FROM DynamicFee df WHERE df.status = true ORDER BY df.id DESC")
    List<DynamicFee> findAllInDesOrderByIdAndStatus();

    @Query("SELECT df FROM DynamicFee df WHERE df.status = true ORDER BY df.id DESC")
    Page<DynamicFee> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT df FROM DynamicFee df WHERE df.ticketName LIKE %:searchName% AND df.status = true")
    Page<DynamicFee> findDynamicFeeByTicketName(@Param("searchName") String searchName, Pageable page);
}
