package com.EventPlanner.repository;

import com.EventPlanner.model.SubAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubAccountRepository extends JpaRepository<SubAccount, Long> {
}
