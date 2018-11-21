/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client.impl;

import com.base.client.CustomerOrderDataClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.CustomerOrder;
import com.model.child.CustomerOrderData;

import java.sql.*;

import javafx.collections.ObservableList;

/**
 *
 * @author RISITH-PC
 */
public class CustomerOrderDataClientImpl implements CustomerOrderDataClient{ 
    
    private static CustomerOrderDataClientImpl customerOrderDataClientImpl;
    private static ObservableList<CustomerOrderData> customerOrderDataList;

    private CustomerOrderDataClientImpl() {
        customerOrderDataList = (ObservableList<CustomerOrderData>) ListConnection.getInstance().getCustomerOrderDataList();
    }
   
    public static CustomerOrderDataClientImpl getInstance() {
        if (customerOrderDataClientImpl == null) {
            customerOrderDataClientImpl = new CustomerOrderDataClientImpl();
        }
        return customerOrderDataClientImpl;
    }

    @Override
    public boolean add(ObservableList<CustomerOrderData> customerOrderDataList) throws SQLException, ClassNotFoundException {
        String query = "Insert into customerOrderData values(?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        int count = 0;
        for (CustomerOrderData customerOrderData : customerOrderDataList) {
            PreparedStatement state = conn.prepareStatement(query);

            state.setObject(1, customerOrderData.getCustomerOrder().getId());
            state.setObject(2, customerOrderData.getAmount());
            state.setObject(3, customerOrderData.getRate());
            count += state.executeUpdate();
        }
        return count == customerOrderDataList.size();
    }

    @Override
    public ObservableList<CustomerOrderData> search(CustomerOrder customerOrder) throws SQLException, ClassNotFoundException {
        if (customerOrder == null) return null;

        String query = "Select * from customerOrderData where cusOrderId = " + customerOrder.getId();
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        customerOrderDataList.clear();
        while (result.next()) {
            CustomerOrderData customerOrderData = new CustomerOrderData();
            customerOrderData.setCustomerOrder(customerOrder);
            customerOrderData.setAmount(result.getDouble("amount"));
            customerOrderData.setRate(result.getInt("rate"));
            customerOrderDataList.add(customerOrderData);
        }
        System.out.println("Customer Order List Loaded : " + customerOrderDataList.size());
        return  customerOrderDataList;
    }

}
