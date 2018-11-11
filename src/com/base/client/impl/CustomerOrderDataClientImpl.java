/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client.impl;

import com.base.client.CustomerOrderDataClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.manifest.Data;
import com.manifest.Symbol;
import com.model.child.CustomerOrder;
import com.model.child.CustomerOrderData;
import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author RISITH-PC
 */
public class CustomerOrderDataClientImpl implements CustomerOrderDataClient{ 
    
    private static CustomerOrderDataClientImpl customerOrderDataClientImpl;
    private static ObservableList<CustomerOrder> customerOrderList;
    private static ObservableList<CustomerOrderData> customerOrderDataList;


    private CustomerOrderDataClientImpl() {
        customerOrderList =  (ObservableList<CustomerOrder>) ListConnection.getInstance().getCustomerOrderList();
        customerOrderDataList = (ObservableList<CustomerOrderData>) ListConnection.getInstance().getCustomerOrderDataList();

    }
   
    public static CustomerOrderDataClientImpl getInstance() {
        if (customerOrderDataClientImpl == null) {
            customerOrderDataClientImpl = new CustomerOrderDataClientImpl();
        }
        return customerOrderDataClientImpl;
    }
    
  
    @Override
    public boolean add(CustomerOrderData customerOrderData) throws SQLException, ClassNotFoundException {
        customerOrderDataList.add(customerOrderData);
        String query = "Insert into customerOrderData values(?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);

        state.setObject(1, customerOrderData.getId());
        state.setObject(2, customerOrderData.getCustomerOrder().getId());
        state.setObject(3, customerOrderData.getAmount());
        state.setObject(4, customerOrderData.getDiscount());

        if (state.executeUpdate() > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(CustomerOrderData customerOrderData) {
       return true;
    }

    @Override
    public boolean delete(int id)  {
        return true;
    }

    @Override
    public CustomerOrderData search(int id) {
        CustomerOrderData customerOrderData = new CustomerOrderData(id);
        int index = customerOrderDataList.indexOf(customerOrderData);
        if (index != -1) {
            return customerOrderDataList.get(index);
        }
        return null;
    }

    @Override
    public ObservableList<CustomerOrderData> getAll(){
        return customerOrderDataList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        String query = "Select * from customerOrderData";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            CustomerOrderData customerOrderData = new CustomerOrderData();

            customerOrderData.setId(result.getInt(1));
            if(customerOrderList.isEmpty()){
                CustomerOrderClientImpl.getInstance().loadAll();
            }
            customerOrderData.setCustomerOrder(CustomerOrderClientImpl.getInstance().search(result.getInt(2)));
            customerOrderData.setAmount(result.getDouble(3));
            customerOrderData.setRate(result.getInt(4));

            if(customerOrderData.getCustomerOrder() !=  null) customerOrderDataList.add(customerOrderData);
        }
        System.out.println("Customer Order Data List Loaded : " + customerOrderDataList.size());
    }


    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "Select cusOrderDataId+1 from customerOrderData order by 1 desc limit 1";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
}
