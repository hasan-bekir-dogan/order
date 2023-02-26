package org.bekir;

import org.bekir.exception.CustomerIdCheckException;

public class Customer extends BaseCustomer{

    public Customer(int id, String name, String createdate) throws CustomerIdCheckException {
        super(id, name, createdate);
    }

}
