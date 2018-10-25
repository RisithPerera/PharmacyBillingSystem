/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client.impl;

import com.base.client.CustomerClient;
import com.base.file.FileManager;
import com.base.list.ListConnection;
import com.manifest.Data;
import com.manifest.Symbol;
import com.model.child.Customer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;
import javafx.collections.ObservableList;

/**
 *
 * @author RISITH-PC
 */
public class CustomerClientImpl implements CustomerClient{ 
    
    private static CustomerClientImpl customerClient;
    private static ObservableList<Customer> customerList;

    private CustomerClientImpl() {
        customerList = (ObservableList<Customer>) ListConnection.getInstance().getCustomerList();
    }

    public static CustomerClientImpl getInstance() {
        if (customerClient == null) {
            customerClient = new CustomerClientImpl();
        }
        return customerClient;
    }
    
    @Override
    public  boolean add(Customer customer) throws IOException {
        customerList.add(customer);
        String query = "Insert into member values(?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);

        state.setObject(1 , member.getMemId());
        state.setObject(2 , member.getName());
        state.setObject(3 , member.getAddress());
        state.setObject(4 , member.getDob());
        state.setObject(5 , member.getNic());
        state.setObject(6 , member.getContact());
        state.setObject(7 , member.isGender());
        state.setObject(8 , member.isIsMember());
        state.setObject(9 , member.isIsActive());
        state.setObject(10, member.isIsApplicant());
        state.setObject(11, member.getParentId());
        if (state.executeUpdate() > 0){
            return true;
        }
        return false;
    }

    @Override
    public  boolean update(Customer customer) throws IOException {
        int index = customerList.indexOf(customer);
        if (index != -1) {
            customerList.set(index, customer);
            return writeAll();
        }
        return false;
    }

    @Override
    public  boolean delete(Customer customer) throws IOException {
        customerList.remove(customer);
        return writeAll();
    }

    @Override
    public  Customer search(String id) throws IOException {
        Customer customer = new Customer();
        customer.setId(id);
        if (customerList.isEmpty()) {
            readAll();
        }
        int index = customerList.indexOf(customer);
        if (index != -1) {
            return customerList.get(index);
        }
        return null;
    }

    @Override
    public  ObservableList<Customer> getAll() throws IOException {
        if (customerList.isEmpty()) {
            readAll();
        }
        return customerList;
    }

    @Override
    public  boolean readAll() throws IOException {
        FileManager.getInstance().readAllRecords(Data.CUSTOMER);       
        customerList.clear();
        System.out.println("dddd");
        try{
            for (String line : recordList) {               
                String[] parts = line.split(Symbol.SPLIT_SYMBOL_EXPRESSION);
                System.out.println("Customer Parts : "+parts.length);
                Customer customer = new Customer(parts[0], 
                                                 parts[1], 
                                                 parts[2], 
                                                 parts[3], 
                                                 parts[4], 
                                                 parts[5], 
                                                 parts[6], 
                                                 parts[7], 
                                                 parts[8], 
                                                 parts[9], 
                                                 parts[10], 
                                                 parts[11], 
                                                 parts[12], 
                                                 parts[13], 
                                                 parts[14], 
                                                 parts[15], 
                                                 parts[16],
                                                 parts[17]
                                               );
                customerList.add(customer);
            }
        }catch(Exception e){
            System.out.println("Customer Database is malfunctioned");
        }
        Collections.sort(customerList);
        return true;
    }

    @Override
    public boolean writeAll() throws IOException {
        recordList.clear();
        if (customerList != null) {
            Collections.sort(customerList);
            customerList.stream().forEach((customDTO) -> {
                recordList.add(customDTO.toString());
            });
            FileManager.getInstance().writeAllRecords(Data.CUSTOMER);
        }
        return true;
    }

    @Override
    public long getNextId() {
         if (customerList.isEmpty()) {
            return 0;
        }
        long preIndex = -1;
        for (Customer customer : customerList) {
            if(Long.parseLong(customer.getId()) - preIndex > 1){
                break;
            }
            preIndex = Long.parseLong(customer.getId());
        }
        return preIndex+1;
    }
}
