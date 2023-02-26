package org.bekir;

import org.bekir.constraint.CustomerConstraint;
import org.bekir.exception.CustomerIdCheckException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseCustomer{
    private int id;
    private String name;
    private String createdate;
    private final static List<BaseCustomer> customerList = new ArrayList<>();

    public BaseCustomer(int id, String name, String createdate) throws CustomerIdCheckException {
        // this function is required to check unique customer Id
        CustomerConstraint customerConstraint = new CustomerConstraint(customerList);
        customerConstraint.checkDuplicateId(id);

        this.id = id;
        this.name = name;
        this.createdate = createdate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) throws CustomerIdCheckException {
        // this function is required to check unique customer Id
        CustomerConstraint customerConstraint = new CustomerConstraint(customerList);
        customerConstraint.checkDuplicateId(id);

        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    // insert customer
    public static void insert(BaseCustomer customer) {
        customerList.add(customer);
    }

    // list customers
    public static List<BaseCustomer> list() {
        return customerList;
    }

    // Filter customer list
    public static List<BaseCustomer> getFilteredList(String name) {
        List<BaseCustomer> filteredCustomerList = customerList
                .stream()
                .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return filteredCustomerList;
    }
}
