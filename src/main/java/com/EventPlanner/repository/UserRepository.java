package com.EventPlanner.repository;

import com.EventPlanner.model.TicketType;
import com.EventPlanner.model.User;
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
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);

    @Modifying
    @Query("UPDATE User u SET u.status = false WHERE u.id = :id")
    void setStatusInactive(@Param("id") Long id);
    @Query("SELECT u FROM User u WHERE u.status = true ORDER BY u.id DESC")
    List<User> findAllInDesOrderByIdAndStatus();

    @Query("SELECT u FROM User u WHERE u.status = true ORDER BY u.id DESC")
    Page<User> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:searchName% AND u.status = true")
    Page<User> findUserByName(@Param("searchName") String searchName, Pageable page);
}
