package org.bekir;

import org.bekir.constraint.OrderConstraint;
import org.bekir.exception.OrderCheckException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.function.Function;

public abstract class BaseOrder implements IOrder {
    private int id;
    private int customerId;
    private String name;
    private double price;
    private String firmName;
    private String sectorType;
    private String invoicedate;
    private final static List<BaseOrder> orderList = new ArrayList<>();

    public BaseOrder(int id, int customerId, String name, double price, String firmName, String sectorType, String invoicedate) throws OrderCheckException {
        // this function is required to check unique order id
        OrderConstraint orderConstraint = new OrderConstraint(orderList);
        orderConstraint.checkDuplicateId(id);

        // this function is required to check order price
        orderConstraint.checkPrice(price);

        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.price = price;
        this.firmName = firmName;
        this.sectorType = sectorType;
        this.invoicedate = invoicedate;
    }

    public BaseOrder() {}

    public int getId() {
        return this.id;
    }

    public void setId(int id) throws OrderCheckException {
        // this function is required to check unique order id
        OrderConstraint orderConstraint = new OrderConstraint(orderList);
        orderConstraint.checkDuplicateId(id);

        this.id = id;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) throws OrderCheckException {
        // this function is required to check order price
        OrderConstraint orderConstraint = new OrderConstraint(orderList);
        orderConstraint.checkPrice(price);

        this.price = price;
    }

    public String getFirmName() {
        return this.firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getSectorType() {
        return this.sectorType;
    }

    public void setSectorType(String sectorType) {
        this.sectorType = sectorType;
    }

    public String getInvoicedate() {
        return this.invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    // insert order
    public static void insert(BaseOrder order) {
        orderList.add(order);
    }

    // list orders
    public static List<BaseOrder> list() {
        return orderList;
    }

    // Filter and calculate total price of invoices. It is filtered by createdate
    public static double getFilteredTotalInvoicePrice(List<BaseCustomer> customerList, String createdate) {
        AtomicReference<Double> totalInvoicePrice = new AtomicReference<>((double) 0);

        customerList.stream()
                .filter(c -> c.getCreatedate().toLowerCase().contains(createdate.toLowerCase()))
                .forEach(c -> {
                    orderList.stream()
                            .filter(o -> o.getCustomerId() == c.getId())
                            .forEach(o -> {
                                totalInvoicePrice.updateAndGet(v -> new Double((double) (v + o.getPrice())));
                            });
                });

        return totalInvoicePrice.get();
    }

    // Filter and calculate total price of invoices. It is filtered orders that is greater than parameter
    public static double getFilteredAvgInvoicePrice(double price) {
        double avgInvoicePrice = orderList
                .stream()
                .filter(o -> o.getPrice() > price)
                .mapToDouble(o->o.getPrice())
                .average()
                .orElse(0.0);

        BigDecimal bd = new BigDecimal(avgInvoicePrice).setScale(2, RoundingMode.HALF_DOWN);

        return bd.doubleValue();
    }

    // Filter order list that price is greater than parameter
    // Overload
    @Override
    public void printFilteredInvoice(List<BaseCustomer> customerList, List<BaseOrder> orderList, double greaterThanPrice){
        System.out.println("**************** Order list that price is greater than " + greaterThanPrice + " ****************");

        orderList.stream()
                .filter(o -> o.getPrice() > greaterThanPrice)
                .forEach(o -> {
                    System.out.println("Order Id: " + o.getId());
                    System.out.println("Order Name: " + o.getName());
                    customerList.stream()
                            .filter(c -> c.getId() == o.getCustomerId())
                            .forEach(c -> {
                                System.out.println("Customer name: " + c.getName());
                            });
                    System.out.println("Order Price: " + o.getPrice());
                    System.out.println("Firm Name: " + o.getFirmName());
                    System.out.println("Sector Type: " + o.getSectorType());
                    System.out.println();
                });
    }

    // Filter order list that price is smaller than parameter
    // Overload
    @Override
    public void printFilteredInvoice(double smallerThanPrice, List<BaseCustomer> customerList, List<BaseOrder> orderList){
        System.out.println("\n" + "**************** Customer name that has order price which is smaller than " + smallerThanPrice + " ****************");

        orderList.stream()
                .filter(o -> o.getPrice() < smallerThanPrice)
                .forEach(o -> {
                    customerList.stream()
                            .filter(c -> c.getId() == o.getCustomerId())
                            .forEach(c -> {
                                System.out.println("Customer name: " + c.getName());
                            });
                });
    }

    // Filter order list that price is greater than parameter in invoice date
    // Overload
    @Override
    public void printFilteredInvoice(double smallerThanPrice, String invoicedate, List<BaseCustomer> customerList, List<BaseOrder> orderList){
        System.out.println("\n" + "**************** Sector type that has order average price which is smaller than " + smallerThanPrice + " ****************");

        Map<String, Map<String, Double>> groupedList = orderList.stream()
                                                    .collect(Collectors.groupingBy(
                                                            BaseOrder::getFirmName,
                                                            Collectors.groupingBy(
                                                                BaseOrder::getInvoicedate,
                                                                Collectors.averagingDouble(f -> f.getPrice())
                                                            )
                                                    ));

        Map<String, Double> dateList;
        Optional<String> findList;

        for(Map.Entry item : groupedList.entrySet()){
            dateList = (Map<String, Double>) item.getValue();
            findList = dateList.entrySet().stream()
                    .filter(o -> o.getValue() < smallerThanPrice && o.getKey().toLowerCase().contains(invoicedate.toLowerCase()))
                    .map(Map.Entry::getKey)
                    .findFirst();
            if(findList.isPresent()) {
                String sectorType = getSectorTypeOfFirm(String.valueOf(item.getKey()));
                System.out.println("Sector Type: " + sectorType);
            }
        }

    }

    // get sector type of firm
    public final String getSectorTypeOfFirm(String firmName) {
        String sectorType = "";

        Optional<BaseOrder> data = orderList
                .stream()
                .filter(o -> o.getFirmName() == firmName)
                .findFirst();

        if(data.isPresent()) {
            sectorType = data.get().getSectorType();
        }

        return sectorType;
    }
}
