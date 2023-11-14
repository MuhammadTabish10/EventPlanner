package com.EventPlanner.repository;

import com.EventPlanner.model.SponsorType;
import com.EventPlanner.model.SubAccount;
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
public interface SubAccountRepository extends JpaRepository<SubAccount, Long> {

    @Query("SELECT sub FROM SubAccount sub WHERE sub.name = :name")
    Optional<SubAccount> findByName(@Param("name")String name);

    @Modifying
    @Query("UPDATE SubAccount sub SET sub.status = false WHERE sub.id = :id")
    void setStatusInactive(@Param("id") Long id);
    @Query("SELECT sub FROM SubAccount sub WHERE sub.status = true ORDER BY sub.id DESC")
    List<SubAccount> findAllInDesOrderByIdAndStatus();

    @Query("SELECT sub FROM SubAccount sub WHERE sub.status = true ORDER BY sub.id DESC")
    Page<SubAccount> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT sub FROM SubAccount sub WHERE sub.name LIKE %:searchName% AND sub.status = true")
    Page<SubAccount> findSubAccountByName(@Param("searchName") String searchName, Pageable page);

}
