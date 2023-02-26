package org.bekir.constraint;

import org.bekir.BaseCustomer;
import org.bekir.exception.CustomerIdCheckException;

import java.util.ArrayList;
import java.util.List;

public class CustomerConstraint {
    private List<BaseCustomer> customerList = new ArrayList<>();

    public CustomerConstraint(List<BaseCustomer> customerList) {
        this.customerList = customerList;
    }

    public void checkDuplicateId(int id) throws CustomerIdCheckException {
        // Check unique key. Id must be unique
        BaseCustomer customer = this.customerList
                .stream()
                .filter(c -> c.getId() == id)
                .findAny()
                .orElse(null);

        if(customer != null) {
            throw new CustomerIdCheckException("Customer who has id number "+id+" already exist");
        }
    }
}
