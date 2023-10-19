package com.EventPlanner.repository;

import com.EventPlanner.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String name);
    @Query("SELECT t FROM Tag t WHERE t.name LIKE %:searchName%")
    List<Tag> findTagByName(@Param("searchName") String searchName);
    @Modifying
    @Query("UPDATE Tag t SET t.status = false WHERE t.id = :id")
    void setStatusInactive(@Param("id") Long id);
    @Query("SELECT t FROM Tag t WHERE t.status = true ORDER BY t.id DESC")
    List<Tag> findAllInDesOrderByIdAndStatus();
}
