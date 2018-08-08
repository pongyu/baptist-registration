package org.bapp.util.sequence;

import java.util.Calendar;

interface SequenceService {


    Integer setEventId();

    String setEventName();

    String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2);

    Integer getSequence();

//    String zero = String.format("%03", getSequence());
    String addZero();

    String generateSequenceId();

}

