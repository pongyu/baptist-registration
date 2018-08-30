package org.bapp.repository;


import org.bapp.model.Codetable;
import org.bapp.model.CodetableId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodetableRepository extends JpaRepository<Codetable, CodetableId>{

    Page<Codetable> findAllById_CodeName(String codename, Pageable pageable);

    List<Codetable> findAllById_CodeName(String codename);

    Codetable findById_CodeNameAndId_CodeValue(String codename, String codevalue);

}
