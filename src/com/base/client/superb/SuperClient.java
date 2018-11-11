/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client.superb;


import com.model.superb.SuperModel;
import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.ObservableList;

/**
 *
 * @author Pahansith
 * @param <T>
 */
public interface SuperClient<T extends SuperModel>{    
    public boolean add(T t) throws SQLException, ClassNotFoundException;
    public boolean update(T t) throws SQLException, ClassNotFoundException;
    public T search(int t);
    public boolean delete(int t) throws SQLException, ClassNotFoundException;
    public ObservableList<T> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
