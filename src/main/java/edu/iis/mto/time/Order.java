package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private static final int VALID_PERIOD_HOURS = 24;
    private State orderState;
    private List<OrderItem> items = new ArrayList<OrderItem>();
    private DateTime subbmitionDate;

    private Timer fakeDate;

    public Order() {
        orderState = State.CREATED;
        fakeDate = new Timer();
    }

    public void setDate(DateTime dateTime) {
        fakeDate.setDateTime(dateTime);
    }

    public void addItem(OrderItem item) {
        requireState(State.CREATED, State.SUBMITTED);

        items.add(item);
        orderState = State.CREATED;

    }

    public void submit() {
        requireState(State.CREATED);

        orderState = State.SUBMITTED;
        subbmitionDate = fakeDate.getDateTime();
    }

    public void confirm() {
        requireState(State.SUBMITTED);
        int hoursElapsedAfterSubmittion = Hours.hoursBetween(subbmitionDate, fakeDate.getDateTime()).getHours();
        if (hoursElapsedAfterSubmittion > VALID_PERIOD_HOURS) {
            orderState = State.CANCELLED;
            throw new OrderExpiredException();
        }
    }

    public void realize() {
        requireState(State.CONFIRMED);
        orderState = State.REALIZED;
    }

    State getOrderState() {
        return orderState;
    }

    private void requireState(State... allowedStates) {
        for (State allowedState : allowedStates) {
            if (orderState == allowedState)
                return;
        }

        throw new OrderStateException(
                "order should be in state " + allowedStates + " to perform required  operation, but is in " + orderState);

    }

    public static enum State {
        CREATED, SUBMITTED, CONFIRMED, REALIZED, CANCELLED
    }
}
