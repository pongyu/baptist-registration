package org.bapp.util.sequence;

import org.bapp.model.Sequence;
import org.bapp.repository.SequenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SequenceServiceImpl implements SequenceService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SequenceRepository sequenceRepository;

    private Integer eventId;

    private String eventName;

    @Override
    public Integer eventId() {
        return this.eventId;
    }

    @Override
    public String eventName() {
        return this.eventName;
    }

    @Override
    public Integer getSequence() {
        try {
            Optional<Sequence> sequence = sequenceRepository.findById(eventId());
            if(!sequence.isPresent()){
                Sequence seq = new Sequence();
                seq.setId(eventId());
                seq.setEvent(eventName());
                seq.setSequence(2);
                seq.setYear(Integer.valueOf(year));
                sequenceRepository.save(seq);
                logger.info("saved new event ", eventName());
                return 1;
            } else {
                Sequence eSequence = sequence.get();
                Integer s = eSequence.getSequence();
                eSequence.setSequence(s+1);
                sequenceRepository.save(eSequence);
                logger.info("sequence updated to ", eSequence.getSequence());
                return s;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String addSequence() {
        return String.format("%03d", getSequence());
    }

    @Override
    public String generateSequenceId() {
        String id = eventId()+year+addSequence();
        return id;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
