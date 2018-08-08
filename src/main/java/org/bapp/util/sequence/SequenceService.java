package org.bapp.util.sequence;

import java.util.Calendar;

public interface SequenceService {


    Integer eventId();

    String eventName();

    String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2);

    Integer getSequence();

    String addSequence();

    String generateSequenceId();

}

