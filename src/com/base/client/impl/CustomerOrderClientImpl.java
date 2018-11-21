/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 */

package com.base.client.impl;

import com.base.client.CustomerOrderClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Customer;
import com.model.child.CustomerOrder;
import java.sql.*;

import com.model.child.CustomerOrderData;
import javafx.collections.ObservableList;

/**
 * @author RISITH-PC
 */

public class CustomerOrderClientImpl implements CustomerOrderClient{ 
    
    private static CustomerOrderClientImpl customerOrderClient;
    private static ObservableList<Customer> customerList;
    private static ObservableList<CustomerOrder> customerOrderList;

    private CustomerOrderClientImpl() {
        customerList = ListConnection.getInstance().getCustomerList();
        customerOrderList = ListConnection.getInstance().getCustomerOrderList();
    }
  
    public static CustomerOrderClientImpl getInstance() {
        if (customerOrderClient == null) {
            customerOrderClient = new CustomerOrderClientImpl();
        }
        return customerOrderClient;
    }
    
    @Override
    public boolean add(CustomerOrder customerOrder, ObservableList<CustomerOrderData> customerOrderDataList) throws SQLException, ClassNotFoundException {

        if (customerOrder == null || customerOrder.getCustomer() == null || customerOrderDataList == null) {
            System.out.println("XXXXXXXXX");
            return false;
        }

        String query = "Insert into customerOrder values(?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(1, customerOrder.getDate());
            state.setObject(2, customerOrder.getTime());
            state.setObject(3, customerOrder.getId());
            state.setObject(4, customerOrder.getCustomer().getId());

            if (state.executeUpdate() > 0) {
                if (CustomerOrderDataClientImpl.getInstance().add(customerOrderDataList)) {
                    customerOrderList.add(customerOrder);
                    conn.commit();
                    return true;
                }
                conn.rollback();
                return false;
            }
            conn.rollback();
            return false;
        } finally {
            conn.setAutoCommit(true);
        }
    }


    @Override
    public CustomerOrder search(int id)  {
        CustomerOrder customerOrder = new CustomerOrder(id);
        int index = customerOrderList.indexOf(customerOrder);
        if (index != -1) {
            return customerOrderList.get(index);
        }
        return null;
    }

    @Override
    public ObservableList<CustomerOrder> getAll() {
        return customerOrderList;
    }


    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        customerOrderList.clear();
        String query = "Select * from customerOrder";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            CustomerOrder customerOrder = new CustomerOrder();

            customerOrder.setDate(result.getString(1));
            customerOrder.setTime(result.getString(2));
            customerOrder.setId(result.getInt(3));
            if(customerList.isEmpty()){
                CustomerClientImpl.getInstance().loadAll();
            }
            customerOrder.setCustomer(CustomerClientImpl.getInstance().search(result.getInt(4)));
            if(customerOrder.getCustomer() !=  null) customerOrderList.add(customerOrder);
        }
        System.out.println("Customer Order List Loaded : " + customerOrderList.size());
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "Select cusOrderId+1 as nextID from customerOrder order by 1 desc limit 1";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt("nextId");
        }
        return 0;
    }
}