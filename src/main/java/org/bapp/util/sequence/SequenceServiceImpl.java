package org.bapp.util.sequence;

import org.bapp.model.Sequence;
import org.bapp.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private SequenceRepository sequenceRepository;

    public Integer getSequence(Integer eventId, String eventName){
        Sequence sequence = sequenceRepository.findSequenceById(eventId);
        if(sequence == null){
            Sequence seq = new Sequence();
            seq.setEvent(eventName);
            seq.setSequence(1);
            seq.setYear(Integer.valueOf(year));
            sequenceRepository.save(seq);
            return seq.getSequence();
        } else {
            sequence.setSequence(sequence.getSequence()+1);
            sequenceRepository.save(sequence);
            return sequence.getSequence();
        }
    }


    @Override
    public Integer setEventId() {
        return null;
    }

    @Override
    public String setEventName() {
        return null;
    }

    @Override
    public Integer getSequence() {
        return null;
    }

    @Override
    public String addZero() {
        return null;
    }

    @Override
    public String generateSequenceId() {
        return null;
    }
}
