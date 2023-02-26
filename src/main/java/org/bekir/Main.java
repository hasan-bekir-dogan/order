package org.bekir;

import org.bekir.exception.CustomerIdCheckException;
import org.bekir.exception.OrderCheckException;

import java.util.List;

public class Main {
    public static void main(String[] args) throws CustomerIdCheckException, OrderCheckException {
        // Create and insert customer objects
        Customer customer1 = new Customer(1, "Bekir", "June");
        Customer customer2 = new Customer(2, "Zeynep", "June");
        Customer customer3 = new Customer(3, "Dogan", "May");
        Customer customer4 = new Customer(4, "Can", "January");
        Customer customer5 = new Customer(5, "Cemre", "September");
        Customer customer6 = new Customer(6, "Mehmet", "June");
        Customer customer7 = new Customer(7, "Aycan", "July");
        Customer customer8 = new Customer(8, "Selim", "May");
        Customer customer9 = new Customer(9, "Tugce", "January");
        Customer customer10 = new Customer(10, "Pelin", "September");
        Customer customer11 = new Customer(11, "Hasan", "July");

        Customer.insert(customer1);
        Customer.insert(customer2);
        Customer.insert(customer3);
        Customer.insert(customer4);
        Customer.insert(customer5);
        Customer.insert(customer6);
        Customer.insert(customer7);
        Customer.insert(customer8);
        Customer.insert(customer9);
        Customer.insert(customer10);
        Customer.insert(customer11);



        // List all customers name
        List<BaseCustomer> customerList = Customer.list();
        System.out.println("All customer name: ");
        int i = 0;
        for(BaseCustomer c : customerList) {
            System.out.print(c.getName());
            if(i++ != customerList.size()-1){
                System.out.print(", ");
            }
        }
        System.out.println("\n");


        // List customer name who has name which includes C
        List<BaseCustomer> filteredCustomerList = Customer.getFilteredList("C");
        System.out.println("All customer name who has name which includes C: ");
        i = 0;
        for(BaseCustomer c : filteredCustomerList) {
            System.out.print(c.getName());
            if(i++ != filteredCustomerList.size()-1){
                System.out.print(", ");
            }
        }
        System.out.println("\n");


        // Create and insert order objects
        Order order1 = new Order(1, 1, "Order 1", 1500, "Firm A", "Technology", "September");
        Order order2 = new Order(2, 2, "Order 2", 3000, "Firm B", "Food", "July");
        Order order3 = new Order(3, 4, "Order 3", 4000, "Firm C", "Cloth", "September");
        Order order4 = new Order(4, 6, "Order 4", 600, "Firm A", "Technology", "June");
        Order order5 = new Order(5, 7, "Order 5", 740, "Firm B", "Food", "June");
        Order order6 = new Order(6, 1, "Order 6", 900, "Firm C", "Cloth", "July");
        Order order7 = new Order(7, 2, "Order 7", 300, "Firm B", "Food", "June");
        Order order8 = new Order(8, 3, "Order 8", 200, "Firm B", "Food", "September");
        Order order9 = new Order(9, 9, "Order 9", 2000, "Firm C", "Cloth", "May");
        Order order10 = new Order(10, 2, "Order 10", 5000, "Firm A", "Technology", "June");
        Order order11 = new Order(11, 1, "Order 11", 450, "Firm B", "Food", "September");
        Order order12 = new Order(12, 2, "Order 12", 250, "Firm A", "Technology", "June");
        Order order13 = new Order(13, 5, "Order 13", 1600, "Firm C", "Cloth", "May");

        Order.insert(order1);
        Order.insert(order2);
        Order.insert(order3);
        Order.insert(order4);
        Order.insert(order5);
        Order.insert(order6);
        Order.insert(order7);
        Order.insert(order8);
        Order.insert(order9);
        Order.insert(order10);
        Order.insert(order11);
        Order.insert(order12);
        Order.insert(order13);


        // Calculate total invoice price that belongs to customer who register in june
        double totalInvoicePrice = Order.getFilteredTotalInvoicePrice(customerList, "June");
        System.out.println("Total invoice price that belongs to customer who register in june: " + totalInvoicePrice + "\n");

        // List all customers name
        List<BaseOrder> orderList = Order.list();
        System.out.println("All order name: ");
        i = 0;
        for(BaseOrder o : orderList) {
            System.out.print(o.getName());
            if(i++ != orderList.size()-1){
                System.out.print(", ");
            }
        }
        System.out.println("\n");

        // Calculate total invoice price that belongs to customer who register in june
        double avgInvoicePrice = Order.getFilteredAvgInvoicePrice(1500);
        System.out.println("Average invoice price that is greater than 1500: " + avgInvoicePrice + "\n");

        // Print invoices that price is greater than 1500
        Order order = new Order();
        order.printFilteredInvoice(customerList, orderList, 1500);

        // Print invoices that price is smaller than 500
        order.printFilteredInvoice(500, customerList, orderList);

        // Print invoices that price is smaller than 750 in june
        order.printFilteredInvoice(750, "June", customerList, orderList);
    }
}