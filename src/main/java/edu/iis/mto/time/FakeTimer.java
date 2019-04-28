package edu.iis.mto.time;

import org.joda.time.DateTime;

public final class FakeTimer {
    private DateTime fakeDateTime;

    public FakeTimer(){
        this.fakeDateTime = new DateTime();
    }
    public void setFakeDateTime(DateTime dateTime){
        this.fakeDateTime = dateTime;
    }

    public DateTime getFakeDateTime(){
        return this.fakeDateTime;

    }
}
