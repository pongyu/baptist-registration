package org.bapp.repository;

import org.bapp.model.Church;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChurchRepository extends JpaRepository<Church, String> {
}
