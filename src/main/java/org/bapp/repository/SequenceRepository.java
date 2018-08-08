package org.bapp.repository;

import org.bapp.model.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SequenceRepository extends JpaRepository <Sequence, Integer>{

    Sequence findSequenceByYearAndEvent(Integer year, String event);

    Sequence findSequenceByEvent(String eventName);

    Sequence findSequenceById(Integer id);
}
