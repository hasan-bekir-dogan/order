package org.bekir.constraint;

import org.bekir.BaseOrder;
import org.bekir.exception.OrderCheckException;

import java.util.ArrayList;
import java.util.List;

public class OrderConstraint {
    private List<BaseOrder> orderList = new ArrayList<>();

    public OrderConstraint(List<BaseOrder> orderList) {
        this.orderList = orderList;
    }

    public void checkDuplicateId(int id) throws OrderCheckException {
        // Check unique key. Id must be unique
        BaseOrder order = this.orderList
                .stream()
                .filter(o -> o.getId() == id)
                .findAny()
                .orElse(null);

        if(order != null) {
            throw new OrderCheckException("Order that has id number "+id+" already exist");
        }
    }

    public void checkPrice(double price) throws OrderCheckException {
        // Check price is greater than zero or equals to zero
        if(price < 0) {
            throw new OrderCheckException("Order price must be greater than zero or equals to zero");
        }
    }
}
