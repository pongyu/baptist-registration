package org.bapp.repository;

import org.bapp.model.Registrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrantRepository extends JpaRepository <Registrant, Long>{
}
