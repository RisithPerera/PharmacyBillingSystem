/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;

import com.model.child.NormalOrder;
import com.model.child.NormalOrderData;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author RISITH-PC
 */
public interface NormalOrderDataClient{
    public boolean add(ObservableList<NormalOrderData> normalOrderDataList) throws SQLException, ClassNotFoundException;

    public ObservableList<NormalOrderData> search(NormalOrder normalOrder) throws SQLException, ClassNotFoundException;
}
