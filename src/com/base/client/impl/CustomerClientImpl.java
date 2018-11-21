/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.base.client.impl;

import com.base.client.CustomerClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Customer;

import java.sql.*;
import javafx.collections.ObservableList;

public class CustomerClientImpl implements CustomerClient {

    private static CustomerClientImpl customerClient;
    private static ObservableList<Customer> customerList;

    private CustomerClientImpl() {
        customerList = ListConnection.getInstance().getCustomerList();
    }

    public static CustomerClientImpl getInstance() {
        if (customerClient == null) {
            customerClient = new CustomerClientImpl();
        }
        return customerClient;
    }

    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        if (customer == null) return false;
        String query = "Insert into customer values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);

            state.setObject(1, customer.getDate());
            state.setObject(2, customer.getTime());
            state.setObject(3, customer.getId());
            state.setObject(4, customer.getFName());
            state.setObject(5, customer.getLName());
            state.setObject(6, customer.getStreet());
            state.setObject(7, customer.getCity());
            state.setObject(8, customer.getDistrict());
            state.setObject(9, customer.getDob());
            state.setObject(10, customer.getNicNo());
            state.setObject(11, customer.getLicNo());
            state.setObject(12, customer.getTeleNo());
            state.setObject(13, customer.getWhatsappNo());
            state.setObject(14, customer.getViberNo());
            state.setObject(15, customer.getEmail());
            state.setObject(16, customer.getIssueDate());
            state.setObject(17, customer.getExpireDate());
            state.setObject(18, customer.getPoints());

            if(state.executeUpdate()>0){
                customerList.add(customer);
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;

        }finally{
            conn.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        if (customer == null) return false;
        String query =  "Update customer set " +
                        "recordDate = ?, " +
                        "recordTime = ?, " +
                        "fName      = ?, " +
                        "lName      = ?, " +
                        "street     = ?, " +
                        "city       = ?, " +
                        "district   = ?, " +
                        "dob        = ?, " +
                        "nic        = ?, " +
                        "lic        = ?, " +
                        "tele       = ?, " +
                        "whats      = ?, " +
                        "viber      = ?, " +
                        "email      = ?, " +
                        "issueDate  = ?, " +
                        "expirDate  = ?, " +
                        "points     = ?  " +
                        "where cusId = ?;";

        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(1, customer.getDate());
            state.setObject(2, customer.getTime());
            state.setObject(3, customer.getFName());
            state.setObject(4, customer.getLName());
            state.setObject(5, customer.getStreet());
            state.setObject(6, customer.getCity());
            state.setObject(7, customer.getDistrict());
            state.setObject(8, customer.getDob());
            state.setObject(9, customer.getNicNo());
            state.setObject(10, customer.getLicNo());
            state.setObject(11, customer.getTeleNo());
            state.setObject(12, customer.getWhatsappNo());
            state.setObject(13, customer.getViberNo());
            state.setObject(14, customer.getEmail());
            state.setObject(15, customer.getIssueDate());
            state.setObject(16, customer.getExpireDate());
            state.setObject(17, customer.getPoints());
            state.setObject(18, customer.getId());
            if (state.executeUpdate() > 0) {
                int index = customerList.indexOf(customer);
                if (index != -1) customerList.set(index, customer);
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        if (id < 0) return false;
        String query = "Delete from customer where cusId = ?";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        state.setObject(1, id);
        if (state.executeUpdate() > 0) {
            Customer customer = new Customer(id);
            customerList.remove(customer);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Customer search(int id) {
        if (id < 0) return null;
        Customer customer = new Customer(id);
        int index = customerList.indexOf(customer);
        if (index != -1) {
            return customerList.get(index);
        }
        return null;
    }

    @Override
    public ObservableList<Customer> getAll() {
        return customerList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        customerList.clear();
        String query = "Select * from customer";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Customer customer = new Customer();

            customer.setDate(result.getString("recordDate"));
            customer.setTime(result.getString(2));
            customer.setId(result.getInt(3));
            customer.setFName(result.getString(4));
            customer.setLName(result.getString(5));
            customer.setStreet(result.getString(6));
            customer.setCity(result.getString(7));
            customer.setDistrict(result.getString(8));
            customer.setDob(result.getString(9));
            customer.setNicNo(result.getString(10));
            customer.setLicNo(result.getString(11));
            customer.setTeleNo(result.getString(12));
            customer.setWhatsappNo(result.getString(13));
            customer.setViberNo(result.getString(14));
            customer.setEmail(result.getString(15));
            customer.setIssueDate(result.getString(16));
            customer.setExpireDate(result.getString(17));
            customer.setPoints(result.getDouble(18));

            customerList.add(customer);

        }
        System.out.println("Customer List Loaded : " + customerList.size());
    }


    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "Select cusId+1 as nextID from customer order by 1 desc limit 1";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt("nextID");
        }
        return 0;
    }
}
