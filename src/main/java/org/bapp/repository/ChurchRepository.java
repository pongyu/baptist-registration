package org.bapp.repository;

import org.bapp.model.Church;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChurchRepository extends JpaRepository<Church, String> {

    Church findByChurchId(String churchId);

}
