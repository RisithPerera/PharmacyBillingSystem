/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;


import com.model.child.Customer;
import com.model.child.CustomerOrder;
import com.model.child.CustomerOrderData;
import javafx.collections.ObservableList;
import java.sql.SQLException;


/**
 *
 * @author RISITH-PC
 */
public interface CustomerOrderClient{
    public boolean add(CustomerOrder customerOrder, ObservableList<CustomerOrderData> customerOrderDataList) throws SQLException, ClassNotFoundException;
    public CustomerOrder search(int id);
    public ObservableList<CustomerOrder> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
