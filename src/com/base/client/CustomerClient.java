/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;

import com.model.child.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;


/**
 *
 * @author RISITH-PC
 */
public interface CustomerClient {
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException;
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException;
    public Customer search(int t);
    public boolean delete(int t) throws SQLException, ClassNotFoundException;
    public ObservableList<Customer> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
