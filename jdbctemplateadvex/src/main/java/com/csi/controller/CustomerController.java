package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Customer;
import com.csi.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Customer customer) {
        log.info("Trying to signup for Customer: " + customer.getCustName());
        customerService.signUp(customer);
        return ResponseEntity.ok("SignUp Done Successfully");
    }

    @GetMapping("/signin/{custEmailId}/{custPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String custEmailId, @PathVariable String custPassword) {
        return ResponseEntity.ok(customerService.signIn(custEmailId, custPassword));
    }

    @PostMapping("/saveall")
    public ResponseEntity<String> saveAll(@RequestBody List<Customer> customerList) {
        customerService.saveAll(customerList);
        return ResponseEntity.ok("All Data Saved Successfully");
    }

    @GetMapping("/findbyid/{custId}")
    public ResponseEntity<Customer> findById(@PathVariable int custId) {
        return ResponseEntity.ok(customerService.findById(custId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/findbyname/{custName}")
    public ResponseEntity<List<Customer>> findByName(@PathVariable String custName) {
        return ResponseEntity.ok(customerService.findAll().stream().filter(cust -> cust.getCustName().equals(custName)).toList());
    }

    @GetMapping("/findbycontactnumber/{custContactNumber}")
    public ResponseEntity<Customer> findByContactNumber(@PathVariable long custContactNumber) {
        return ResponseEntity.ok(customerService.findAll().stream().filter(cust -> cust.getCustContactNumber() == custContactNumber).toList().get(0));
    }

    @GetMapping("/findbyemailid/{custEmailId}")
    public ResponseEntity<Customer> findByEmail(@PathVariable String custEmailId) {
        return ResponseEntity.ok(customerService.findAll().stream().filter(cust -> cust.getCustEmailId().equals(custEmailId)).toList().get(0));
    }

    @GetMapping("/findbyanyinput/{input}")
    public ResponseEntity<List<Customer>> findByAnyInput(@PathVariable String input) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return ResponseEntity.ok(customerService.findAll().stream().filter(customer -> customer.getCustName().equals(input)
                || customer.getCustEmailId().equals(input)
                || simpleDateFormat.format(customer.getCustDOB()).equals(input)
                || String.valueOf(customer.getCustId()).equals(input)
                || String.valueOf(customer.getCustContactNumber()).equals(input)
                || String.valueOf(customer.getCustUID()).equals(input)
                || customer.getCustPanCard().equals(input)).toList());

    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Customer>> sortById() {
        return ResponseEntity.ok(customerService.findAll().stream().sorted(Comparator.comparing(Customer::getCustId)).toList());
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Customer>> sortByName() {
        return ResponseEntity.ok(customerService.findAll().stream().sorted(Comparator.comparing(Customer::getCustName)).toList());
    }

    @GetMapping("/sortbyaccbalance")
    public ResponseEntity<List<Customer>> sortByAccBalance() {
        return ResponseEntity.ok(customerService.findAll().stream().sorted(Comparator.comparing(Customer::getCustAccBalance)).toList());
    }

    @PutMapping("/update/{custId}")
    public ResponseEntity<String> update(@PathVariable int custId, @RequestBody Customer customer) {

        boolean flag = false;

        if (customerService.findById(custId).getCustId() == custId) {
            customerService.update(custId, customer);
            flag = true;

        }

        if (!flag) {
            throw new RecordNotFoundException("Customer ID Does Not Exist");
        }
        return ResponseEntity.ok("Data Updated Succssfully");


    }

    @DeleteMapping("/deletebyid/{custId}")
    public ResponseEntity<String> deleteById(@PathVariable int custId) {
        customerService.deleteById(custId);
        return ResponseEntity.ok("Data Deleted Successfully");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        customerService.deleteAll();
        return ResponseEntity.ok("All Data Deleted Successfully");
    }

    @GetMapping("/checkloaneligibility/{custId}")
    public ResponseEntity<String> checkLoanEligibility(@PathVariable int custId) {

        String msg = "";
        Customer customer = customerService.findById(custId);
        if (customer.getCustId() == custId && customer.getCustAccBalance() >= 50000.00) {
            msg = "Eligible for loan";
        } else {
            msg = "Not Eligible for loan";
        }

        return ResponseEntity.ok(msg);

    }


}
