package com.EventPlanner.repository;

import com.EventPlanner.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
    @Query("SELECT con FROM Country con WHERE con.name LIKE %:searchName%")
    List<Country> findCountriesByName(@Param("searchName") String searchName);

    @Modifying
    @Query("UPDATE Country con SET con.status = false WHERE con.id = :id")
    void setStatusInactive(@Param("id") Long id);
}
