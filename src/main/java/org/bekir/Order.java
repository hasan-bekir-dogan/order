package org.bekir;

import org.bekir.exception.OrderCheckException;

public class Order extends BaseOrder implements IOrder{

    public Order(int id, int customerId, String name, double price, String firmName, String sectorType, String invoicedate) throws OrderCheckException {
        super(id, customerId, name, price, firmName, sectorType, invoicedate);
    }
    public Order() throws OrderCheckException {
        super();
    }
}
