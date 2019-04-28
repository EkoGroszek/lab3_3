package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class OrderTest {

    private Order order;
    private OrderItem orderItem;

    @Before public void init() {
        order = new Order();
        orderItem = new OrderItem();
    }

    @Test(expected = OrderExpiredException.class) public void afterExpirationShouldThrowOrderExpiredException() {
        order.addItem(orderItem);
        order.setFakeDate(new DateTime(2019, 4, 1, 0, 0));
        order.submit();
        order.setFakeDate(new DateTime(2019, 4, 30, 0, 0));
        order.confirm();
    }

    @Test public void beforeExpirationStatusShouldNoTBeCancelled() {
        order.addItem(orderItem);
        order.setFakeDate(new DateTime(2019, 4, 1, 0, 0));
        order.submit();
        order.setFakeDate(new DateTime(2019, 4, 1, 5, 0));
        order.confirm();

        Assert.assertThat(order.getOrderState(), is(not(equalTo(Order.State.CANCELLED))));
    }

    @Test public void afterExpirationStatusShouldBeCanceled() {
        order.addItem(orderItem);
        order.setFakeDate(new DateTime(2019, 4, 1, 0, 0));
        order.submit();
        order.setFakeDate(new DateTime(2019, 4, 30, 0, 0));
        try {
            order.confirm();
        } catch (OrderExpiredException e) {

        }
        finally {
            Assert.assertThat(order.getOrderState(), is(Order.State.CANCELLED));
        }
    }

    @Test public void statusShouldBeCreatedAfterAddingNewItem() {
        order.addItem(orderItem);

        Assert.assertThat(order.getOrderState(), is(Order.State.CREATED));
    }

}
