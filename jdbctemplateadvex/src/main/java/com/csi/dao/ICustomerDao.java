package com.csi.dao;

import com.csi.model.Customer;

import java.util.List;

public interface ICustomerDao {

    void signUp(Customer customer);

    void saveAll(List<Customer> customerList);

    boolean signIn(String custEmailId, String custPassword);

    Customer findById(int custId);

    List<Customer> findAll();

    void update(int custId, Customer customer);

    void deleteById(int custId);

    void deleteAll();
}
