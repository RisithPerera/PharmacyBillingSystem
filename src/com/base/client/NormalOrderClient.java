/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;

import com.model.child.NormalOrder;
import com.model.child.NormalOrderData;
import javafx.collections.ObservableList;
import java.sql.SQLException;

/**
 *
 * @author RISITH-PC
 */
public interface NormalOrderClient{

    public boolean add(NormalOrder normalOrder, ObservableList<NormalOrderData> normalOrderDataList) throws SQLException, ClassNotFoundException;
    public NormalOrder search(int id);
    public ObservableList<NormalOrder> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
