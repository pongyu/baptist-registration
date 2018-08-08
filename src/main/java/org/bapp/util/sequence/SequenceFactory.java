package org.bapp.util.sequence;

public class SequenceFactory {

    public SequenceService getEvent(String eventName){
        if(eventName == null){
            return null;
        }
        if(eventName.equalsIgnoreCase("BMPSSTA")){
            return new BmpSstaSequence();
        }else if (eventName.equalsIgnoreCase("BMPSYMP")){
            return new BmpSymposiumSequence();
        }
        return null;
    }

}
