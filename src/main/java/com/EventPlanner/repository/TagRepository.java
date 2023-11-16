package com.EventPlanner.repository;

import com.EventPlanner.model.Tag;
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
public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String name);

    @Modifying
    @Query("UPDATE Tag t SET t.status = false WHERE t.id = :id")
    void setStatusInactive(@Param("id") Long id);
    @Query("SELECT t FROM Tag t WHERE t.status = true ORDER BY t.id DESC")
    List<Tag> findAllInDesOrderByIdAndStatus();

    @Query("SELECT t FROM Tag t WHERE t.status = true ORDER BY t.id DESC")
    Page<Tag> findAllInDesOrderByIdAndStatus(Pageable page);

    @Query("SELECT t FROM Tag t WHERE t.name LIKE %:searchName% AND t.status = true")
    Page<Tag> findTagByName(@Param("searchName") String searchName, Pageable page);
}
