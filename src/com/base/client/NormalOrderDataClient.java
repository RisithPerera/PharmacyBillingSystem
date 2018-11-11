/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;


import com.base.client.superb.SuperClient;
import com.model.child.NormalOrder;
import com.model.child.NormalOrderData;
import java.io.IOException;
import javafx.collections.ObservableList;


/**
 *
 * @author RISITH-PC
 */
public interface NormalOrderDataClient extends SuperClient<NormalOrderData>{
    public ObservableList<NormalOrderData> getAllData(NormalOrder t);
}
