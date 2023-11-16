package com.EventPlanner.repository;

import com.EventPlanner.model.WaitingList;
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
public interface WaitingListRepository extends JpaRepository<WaitingList,Long> {
    Optional<WaitingList> findByFirstName(String firstName);

    @Modifying
    @Query("UPDATE WaitingList wl SET wl.status = false WHERE wl.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT wl FROM WaitingList wl WHERE wl.status = true ORDER BY wl.id DESC")
    List<WaitingList> findAllInDesOrderByIdAndStatus();

    @Query("SELECT wl FROM WaitingList wl WHERE wl.status = true ORDER BY wl.id DESC")
    Page<WaitingList> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT wl FROM WaitingList wl WHERE wl.firstName LIKE %:searchName% AND wl.status = true")
    Page<WaitingList> searchByFirstName(@Param("searchName") String searchName, Pageable page);
}
