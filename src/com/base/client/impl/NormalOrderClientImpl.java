/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client.impl;

import com.base.client.NormalOrderClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.manifest.Data;
import com.manifest.Symbol;
import com.model.child.CustomerOrder;
import com.model.child.NormalOrder;
import java.io.IOException;
import java.sql.*;
import java.util.Collections;

import com.model.child.NormalOrderData;
import javafx.collections.ObservableList;

/**
 *
 * @author RISITH-PC
 */
public class NormalOrderClientImpl implements NormalOrderClient{ 
    
    private static NormalOrderClientImpl normalOrderClientImpl;
    private static ObservableList<NormalOrder> normalOrderList;

    private NormalOrderClientImpl() {
        normalOrderList = (ObservableList<NormalOrder>) ListConnection.getInstance().getNormalOrderList();
    }
    
    public static NormalOrderClientImpl getInstance() {
        if (normalOrderClientImpl == null) {
            normalOrderClientImpl = new NormalOrderClientImpl();
        }
        return normalOrderClientImpl;
    }
  
    @Override
    public boolean add(NormalOrder normalOrder, ObservableList<NormalOrderData> normalOrderDataList) throws SQLException, ClassNotFoundException {
        if (normalOrder == null || normalOrderDataList == null) {
            return false;
        }

        String query = "Insert into normalOrder values(?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(1, normalOrder.getDate());
            state.setObject(2, normalOrder.getTime());
            state.setObject(3, normalOrder.getId());

            if (state.executeUpdate() > 0) {
                if (NormalOrderDataClientImpl.getInstance().add(normalOrderDataList)) {
                    conn.commit();
                    normalOrderList.add(normalOrder);
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
    public NormalOrder search(int id)  {
        NormalOrder normalOrder = new NormalOrder(id);
        int index = normalOrderList.indexOf(normalOrder);
        if (index != -1) {
            System.out.println(normalOrderList.get(index));
            return normalOrderList.get(index);
        }
        return null;
    }

    @Override
    public ObservableList<NormalOrder> getAll(){
        return normalOrderList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        normalOrderList.clear();
        String query = "Select * from normalOrder";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            NormalOrder normalOrder = new NormalOrder();

            normalOrder.setDate(result.getString(1));
            normalOrder.setTime(result.getString(2));
            normalOrder.setId(result.getInt(3));

            normalOrderList.add(normalOrder);
        }
        System.out.println("Normal Order List Loaded : " + normalOrderList.size());
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "Select norOrderId+1  as nextID from normalOrder order by 1 desc limit 1";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt("nextId");
        }
        return 0;
    }
}
