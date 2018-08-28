package org.bapp.repository;


import org.bapp.model.Codetable;
import org.bapp.model.CodetableId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodetableRepository extends JpaRepository<Codetable, CodetableId>{

    @Query(value = "SELECT * from Codetable c where c.codename = :codename", nativeQuery = true)
    List<Codetable> findAllById(@Param("codename")String codename);
}
