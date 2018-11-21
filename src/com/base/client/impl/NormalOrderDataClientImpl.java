/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.base.client.impl;

import com.base.client.NormalOrderDataClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.manifest.Data;
import com.model.child.CustomerOrder;
import com.model.child.CustomerOrderData;
import com.model.child.NormalOrder;
import com.model.child.NormalOrderData;
import java.io.IOException;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author RISITH-PC
 */

public class NormalOrderDataClientImpl implements NormalOrderDataClient{ 
    
    private static NormalOrderDataClientImpl normalOrderDataClientImpl;
    private static ObservableList<NormalOrderData> normalOrderDataList;

    private NormalOrderDataClientImpl() {
        normalOrderDataList = (ObservableList<NormalOrderData>) ListConnection.getInstance().getNormalOrderDataList();
    }
    
    public static NormalOrderDataClientImpl getInstance() {
        if (normalOrderDataClientImpl == null) {
            normalOrderDataClientImpl = new NormalOrderDataClientImpl();
        }
        return normalOrderDataClientImpl;
    }
    
    @Override
    public boolean add(ObservableList<NormalOrderData> normalOrderDataList) throws SQLException, ClassNotFoundException {
        String query = "Insert into normalOrderData values(?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        int count = 0;
        for (NormalOrderData normalOrderData : normalOrderDataList) {
            PreparedStatement state = conn.prepareStatement(query);

            state.setObject(1, normalOrderData.getNormalOrder().getId());
            state.setObject(2, normalOrderData.getAmount());
            state.setObject(3, normalOrderData.getRate());
            count += state.executeUpdate();
        }
        return count == normalOrderDataList.size();
    }

    @Override
    public ObservableList<NormalOrderData> search(NormalOrder normalOrder) throws SQLException, ClassNotFoundException {

        if (normalOrder == null) return null;

        String query = "Select * from normalOrderData where norOrderId = " + normalOrder.getId();
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        normalOrderDataList.clear();
        while (result.next()) {
            NormalOrderData normalOrderData = new NormalOrderData();
            normalOrderData.setNormalOrder(normalOrder);
            normalOrderData.setAmount(result.getDouble("amount"));
            normalOrderData.setRate(result.getInt("rate"));
            normalOrderDataList.add(normalOrderData);
        }
        System.out.println("Normal Order Data List Loaded : " + normalOrderDataList.size());
        return  normalOrderDataList;
    }

    /*
    @Override
    public ObservableList<NormalOrderData> getAll() {
        return normalOrderDataList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        String query = "Select * from normalOrderData";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            NormalOrderData normalOrderData = new NormalOrderData();

            normalOrderData.setId(result.getInt(1));
            if(normalOrderDataList.isEmpty()){
                NormalOrderDataClientImpl.getInstance().loadAll();
            }
            normalOrderData.setNormalOrder(NormalOrderClientImpl.getInstance().search(result.getInt(2)));
            normalOrderData.setAmount(result.getDouble(3));
            normalOrderData.setRate(result.getInt(4));

            if(normalOrderData.getNormalOrder() !=  null) normalOrderDataList.add(normalOrderData);
        }
        System.out.println("Normal Order Data List Loaded : " + normalOrderDataList.size());
    }

    @Override
    public ObservableList<NormalOrderData> getAllData(NormalOrder normalOrder){
        ObservableList<NormalOrderData> list = FXCollections.observableArrayList();
        for (NormalOrderData normalOrderData : normalOrderDataList) {
            if(normalOrderData.getNormalOrder().equals(normalOrder)){
                list.add(normalOrderData);
            }
        }
        return list;
    }
    
    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "Select norOrderDataId+1 from normalOrderData order by 1 desc limit 1";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
    */
}
