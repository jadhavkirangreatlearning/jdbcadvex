package com.csi.dao;

import com.csi.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CustomerDaoImpl implements ICustomerDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String INSERT_SQL = "insert into customer(custid, custname, custaddress, custaccbalance, custcontactnumber, custdob, custuid, custpancard, custemailid, custpassword)values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    String SELECT_ALL_SQL = "select * from customer";

    String SELECT_SQL_BY_ID = "select * from customer where custid=?";

    String UPDATE_SQL = "update customer set custname=?, custaddress=?, custaccbalance=?, custcontactnumber=?, custdob=?, custuid=?, custpancard=?, custemailid=?, custpassword=? where custid=?";

    String DELETE_BY_ID_SQL = "delete from customer where custid=?";

    String DELETE_ALL_SQL = "truncate table customer";

    private Customer customer(ResultSet resultSet, int n) throws SQLException {
        return Customer.builder().custId(resultSet.getInt(1)).custName(resultSet.getString(2)).custAddress(resultSet.getString(3)).custAccBalance(resultSet.getDouble(4)).custContactNumber(resultSet.getLong(5)).custDOB(resultSet.getDate(6)).custUID(resultSet.getLong(7)).custPanCard(resultSet.getString(8)).custEmailId(resultSet.getString(9)).custPassword(resultSet.getString(10)).build();
    }

    @Override
    public void signUp(Customer customer) {
        jdbcTemplate.update(INSERT_SQL, customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCustAccBalance(), customer.getCustContactNumber(), customer.getCustDOB(), customer.getCustUID(), customer.getCustPanCard(), customer.getCustEmailId(), customer.getCustPassword());
    }

    @Override
    public void saveAll(List<Customer> customerList) {

        for (Customer customer : customerList) {
            jdbcTemplate.update(INSERT_SQL, customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCustAccBalance(), customer.getCustContactNumber(), customer.getCustDOB(), customer.getCustUID(), customer.getCustPanCard(), customer.getCustEmailId(), customer.getCustPassword());
        }
    }

    @Override
    public boolean signIn(String custEmailId, String custPassword) {

        boolean flag = false;

        for (Customer customer : findAll()) {
            if (customer.getCustEmailId().equals(custEmailId) && customer.getCustPassword().equals(custPassword)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public Customer findById(int custId) {
        return jdbcTemplate.query(SELECT_SQL_BY_ID, this::customer, custId).get(0);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, this::customer);
    }

    @Override
    public void update(int custId, Customer customer) {
        jdbcTemplate.update(UPDATE_SQL, customer.getCustName(), customer.getCustAddress(), customer.getCustAccBalance(), customer.getCustContactNumber(), customer.getCustDOB(), customer.getCustUID(), customer.getCustPanCard(), customer.getCustEmailId(), customer.getCustPassword(), custId);
    }

    @Override
    public void deleteById(int custId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, custId);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL);
    }
}
