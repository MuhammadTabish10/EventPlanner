package com.EventPlanner.repository;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.ChartOfAccount;
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
public interface ChartOfAccountRepository extends JpaRepository<ChartOfAccount,Long> {
    Optional<ChartOfAccount> findByName(String name);

    @Modifying
    @Query("UPDATE ChartOfAccount coa SET coa.status = false WHERE coa.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT coa FROM ChartOfAccount coa WHERE coa.status = true ORDER BY coa.id DESC")
    List<ChartOfAccount> findAllInDesOrderByIdAndStatus();

    @Query("SELECT coa FROM ChartOfAccount coa WHERE coa.status = true ORDER BY coa.id DESC")
    Page<ChartOfAccount> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT coa FROM ChartOfAccount coa WHERE coa.name LIKE %:searchName% AND coa.status = true")
    Page<ChartOfAccount> findChartOfAccountByName(@Param("searchName") String searchName, Pageable page);
}
