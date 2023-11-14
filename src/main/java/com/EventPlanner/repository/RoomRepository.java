package com.EventPlanner.repository;

import com.EventPlanner.model.Questions;
import com.EventPlanner.model.Room;
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
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);

    @Modifying
    @Query("UPDATE Room r SET r.status = false WHERE r.id = :id")
    void setStatusInactive(@Param("id") Long id);

    @Query("SELECT r FROM Room r WHERE r.status = true ORDER BY r.id DESC")
    List<Room> findAllInDesOrderByIdAndStatus();

    @Query("SELECT r FROM Room r WHERE r.status = true ORDER BY r.id DESC")
    Page<Room> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT r FROM Room r WHERE r.name LIKE %:searchName% AND r.status = true")
    Page<Room> findRoomByName(@Param("searchName") String searchName, Pageable page);
}
