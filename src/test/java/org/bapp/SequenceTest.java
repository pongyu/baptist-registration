package org.bapp;

import org.bapp.model.Sequence;
import org.bapp.repository.SequenceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Test
    public void findSequence() {
        // given

        Sequence seq = new Sequence();
        seq.setId(1);
        seq.setEvent("BMPSSTA");
        entityManager.persist(seq);
        entityManager.flush();

        // when
        Optional<Sequence> found = sequenceRepository.findById(1);
        Sequence eSequence = found.get();
        // then
        System.out.print("************ "+eSequence.getEvent()+" : "+seq.getEvent());
    }
}
