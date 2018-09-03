package org.bapp.repository;


import org.bapp.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long>{

    /**
     * List State by country code
     */
    List<Country> findAllByCountrycode(String countrycode);

    /**
     * List City by country code & state province
     */
    List<Country> findAllByCountrycodeAndStateprovince(String countrycode, String state);
}
