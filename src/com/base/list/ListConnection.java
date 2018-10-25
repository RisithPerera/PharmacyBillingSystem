/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.list;


import com.model.child.Customer;
import com.model.child.CustomerOrder;
import com.model.child.CustomerOrderData;
import com.model.child.NormalOrder;
import com.model.child.NormalOrderData;
import com.model.child.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author risit
 */
public class ListConnection {

    private static ListConnection listConnection;

    private final ObservableList<Customer> customerList;
    private final ObservableList<CustomerOrder> customerOrderList;
    private final ObservableList<CustomerOrderData> customerOrderDataList;
    private final ObservableList<NormalOrder> normalOrderList;
    private final ObservableList<NormalOrderData> normalOrderDataList;
    private final ObservableList<User> userList;

    private ListConnection() {
        this.customerList = FXCollections.observableArrayList();
        this.customerOrderList = FXCollections.observableArrayList();
        this.customerOrderDataList = FXCollections.observableArrayList();
        this.normalOrderList = FXCollections.observableArrayList();
        this.normalOrderDataList = FXCollections.observableArrayList();
        this.userList = FXCollections.observableArrayList();

    }

    public static ListConnection getInstance() {
        if (listConnection == null) {
            listConnection = new ListConnection();
        }
        return listConnection;
    }

    public ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    public ObservableList<CustomerOrder> getCustomerOrderList() {
        return customerOrderList;
    }

    public ObservableList<CustomerOrderData> getCustomerOrderDataList() {
        return customerOrderDataList;
    }

    public ObservableList<NormalOrder> getNormalOrderList() {
        return normalOrderList;
    }

    public ObservableList<NormalOrderData> getNormalOrderDataList() {
        return normalOrderDataList;
    }

    public ObservableList<User> getUserList() {
        return userList;
    }
}
