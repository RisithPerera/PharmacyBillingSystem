/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;

import com.model.child.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;


/**
 *
 * @author RISITH-PC
 */
public interface UserClient{
    public boolean add(User user) throws SQLException, ClassNotFoundException;
    public boolean update(User user) throws SQLException, ClassNotFoundException;
    public User search(int id);
    public boolean delete(int id) throws SQLException, ClassNotFoundException;
    public ObservableList<User> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
