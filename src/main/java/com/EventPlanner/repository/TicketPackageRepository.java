package com.EventPlanner.repository;

import com.EventPlanner.model.Tag;
import com.EventPlanner.model.TicketPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketPackageRepository extends JpaRepository<TicketPackage,Long> {
    @Modifying
    @Query("UPDATE TicketPackage tp SET tp.status = false WHERE tp.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT tp FROM TicketPackage tp WHERE tp.status = true ORDER BY tp.id DESC")
    List<TicketPackage> findAllInDesOrderByIdAndStatus();

    @Query("SELECT tp FROM TicketPackage tp WHERE tp.status = true ORDER BY tp.id DESC")
    Page<TicketPackage> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT tp FROM TicketPackage tp WHERE tp.comboType LIKE %:searchName% AND tp.status = true")
    Page<TicketPackage> findTicketPackageByComboType(@Param("searchName") String searchName, Pageable page);
}
