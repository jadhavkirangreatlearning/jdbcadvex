package com.csi.service;

import com.csi.dao.ICustomerDao;
import com.csi.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    ICustomerDao customerDao;

    @Override
    public void signUp(Customer customer) {
        customerDao.signUp(customer);
    }

    @Override
    public void saveAll(List<Customer> customerList) {
        customerDao.saveAll(customerList);
    }

    @Override
    public boolean signIn(String custEmailId, String custPassword) {
        return customerDao.signIn(custEmailId, custPassword);
    }

    @Override
    public Customer findById(int custId) {
        return customerDao.findById(custId);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public void update(int custId, Customer customer) {
        customerDao.update(custId, customer);
    }

    @Override
    public void deleteById(int custId) {
        customerDao.deleteById(custId);
    }

    @Override
    public void deleteAll() {
        customerDao.deleteAll();
    }
}
