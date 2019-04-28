package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    private Order order;
    private OrderItem orderItem;

    @Before public void init() {
        order = new Order();
        orderItem = new OrderItem();
    }

    @Test (expected = OrderExpiredException.class)
        public void afterExpirationShouldThrowOrderExpiredException() {
        order.addItem(orderItem);
        order.setFakeDate(new DateTime(2019, 4, 1, 0, 0));
        order.submit();
        order.setFakeDate(new DateTime(2019, 4, 30, 0, 0));
        order.confirm();
    }

}
