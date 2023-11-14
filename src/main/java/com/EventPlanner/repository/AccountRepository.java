package com.EventPlanner.repository;

import com.EventPlanner.model.Account;
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
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByName(String name);
    @Modifying
    @Query("UPDATE Account ac SET ac.status = false WHERE ac.id = :id")
    void setStatusInactive(@Param("id") Long id);
    @Query("SELECT ac FROM Account ac WHERE ac.status = true ORDER BY ac.id DESC")
    List<Account> findAllInDesOrderByIdAndStatus();
    @Query("SELECT ac FROM Account ac WHERE ac.status = true ORDER BY ac.id DESC")
    Page<Account> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT ac FROM Account ac WHERE ac.name LIKE %:searchName% AND ac.status = true")
    Page<Account> findAccountsByName(@Param("searchName") String searchName, Pageable page);
}
