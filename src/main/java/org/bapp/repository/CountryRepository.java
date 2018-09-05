package org.bapp.repository;


import org.bapp.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long>{

    /**
     * List State by country code
     */
    @Query(value = "SELECT * from COUNTRY c WHERE c.code = :ccode GROUP BY c.stateprovince asc", nativeQuery = true)
    List<Country> findAllByCode(@Param("ccode") String code);

    /**
     * List City by country code & state province
     */

    List<Country> findAllByCodeAndStateprovince(String code, String state);

    @Query(value = "SELECT * from COUNTRY c GROUP BY c.country asc", nativeQuery = true)
    List<Country> listCountries();

}
