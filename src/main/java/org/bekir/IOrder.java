package org.bekir;

import java.util.List;

public interface IOrder {

    // Filter order list that price is greater than parameter
    void printFilteredInvoice(List<BaseCustomer> customerList, List<BaseOrder> orderList, double greaterThanPrice);

    // Filter order list that price is smaller than parameter
    void printFilteredInvoice(double smallerThanPrice, List<BaseCustomer> customerList, List<BaseOrder> orderList);

    // Filter order list that price is greater than parameter in invoice date
    void printFilteredInvoice(double smallerThanPrice, String invoicedate, List<BaseCustomer> customerList, List<BaseOrder> orderList);
}
