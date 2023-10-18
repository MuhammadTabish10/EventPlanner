package com.EventPlanner.repository;

import com.EventPlanner.model.SubAccount;
import com.EventPlanner.model.SubAccountPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubAccountRepository extends JpaRepository<SubAccount, SubAccountPK> {

    @Query("SELECT sub FROM SubAccount sub WHERE sub.id.name = :name")
    Optional<SubAccount> findByName(@Param("name")String name);

    @Query("SELECT sub FROM SubAccount sub WHERE sub.id.accountId = :accountId AND sub.id.name = :name")
    Optional<SubAccount> findByCompositeId(@Param("accountId") Long accountId, @Param("name") String name);
    @Query("SELECT sub FROM SubAccount sub WHERE sub.id.name LIKE %:searchName%")
    List<SubAccount> findSubAccountByName(@Param("searchName") String searchName);

    @Modifying
    @Query("UPDATE SubAccount sub SET sub.status = false WHERE sub.id.accountId = :accountId AND sub.id.name = :name")
    void setStatusInactive(@Param("accountId") Long accountId, @Param("name") String name);

}
