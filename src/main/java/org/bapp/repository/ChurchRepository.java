package org.bapp.repository;

import org.bapp.model.Church;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChurchRepository extends JpaRepository<Church, String> {

    Church findByAppStatusAndChurchId(String status, String churchId);

    Church findByChurchId(String churchId);

    List<Church> findByAppStatusAndEventNameAndChurchNameContaining(String appStatus, String eventName, String churchName);
}
