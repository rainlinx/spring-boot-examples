package com.rainlin.service;

import com.rainlin.model.Customer;

import java.util.List;

public interface CustomersInterface {

    public List<Customer> searchCity(Integer pageNumber, Integer pageSize, String searchContent);


}
