/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.child;


import com.manifest.Symbol;
import com.model.superb.SuperModel;
import java.util.Objects;


/**
 *
 * @author RISITH-PC
 */

public class CustomerOrderData extends SuperModel{

    private CustomerOrder customerOrder;
    private double amount;
    private int rate;

    public CustomerOrderData() {}

    public CustomerOrderData(CustomerOrder customerOrder, double amount, int rate) {
        this.customerOrder = customerOrder;
        this.amount = amount;
        this.rate = rate;
    }

    public int getCustomerOrderId() {
        return customerOrder.getId();
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    //-------------------------- Calculatons ----------------------------------//
   
    public double getDiscount() {
        return getAmount() * getRate()/100;
    }
    
    public double getRemainder() {
        return getAmount() - getDiscount();
    }
    
    public double getPoints() {
        return getAmount() * 0.01;
    }
    
    //------------------------- Override Methods ------------------------------//
    @Override
    public String toString() {
        return  getCustomerOrderId()  + Symbol.SPLIT +
                getAmount()           + Symbol.SPLIT +
                getRate();              
    }

    /*
    @Override
    public int compareTo(CustomerOrderData dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomerOrderData) {
            return ((CustomerOrderData)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {        
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
    */
}
