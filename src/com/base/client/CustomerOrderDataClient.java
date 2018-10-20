/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;


import com.base.client.superb.SuperClient;
import com.model.child.CustomerOrder;
import com.model.child.CustomerOrderData;
import java.io.IOException;
import javafx.collections.ObservableList;



/**
 *
 * @author RISITH-PC
 */
public interface CustomerOrderDataClient extends SuperClient<CustomerOrderData>{
    public boolean addOrderData(ObservableList<CustomerOrderData> list) throws IOException;
    public boolean updateOrderData(CustomerOrder t, ObservableList<CustomerOrderData> list) throws IOException;
    public boolean deleteOrderData(CustomerOrder t) throws IOException;
    public ObservableList<CustomerOrderData> getOrderData(CustomerOrder t) throws IOException;
}
