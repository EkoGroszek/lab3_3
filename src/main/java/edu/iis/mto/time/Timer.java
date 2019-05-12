package edu.iis.mto.time;

import org.joda.time.DateTime;

public final class Timer {
    private DateTime DateTime;

    public Timer(){
        this.DateTime = new DateTime();
    }
    public void setDateTime(DateTime dateTime){
        this.DateTime = dateTime;
    }

    public DateTime getDateTime(){
        return this.DateTime;

    }
}
