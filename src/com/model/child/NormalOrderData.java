/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

/**
 *
 * @author RISITH-PC
 */

public class NormalOrderData extends SuperModel {

    private NormalOrder normalOrder;
    private double amount;
    private int rate;

    public NormalOrderData() {}

    public NormalOrderData(NormalOrder normalOrder, double amount, int rate) {
        this.normalOrder = normalOrder;
        this.amount = amount;
        this.rate = rate;
    }

    public NormalOrder getNormalOrder() {
        return normalOrder;
    }

    public int getNormalOrderId() {
        return normalOrder.getId();
    }

    public void setNormalOrder(NormalOrder normalOrder) {
        this.normalOrder = normalOrder;
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
    
    //---------------------------- Override Methods ---------------------------//
    
    @Override
    public String toString() {
        return  getNormalOrderId()    + Symbol.SPLIT +
                getAmount()           + Symbol.SPLIT +
                getRate();              
    }      

    /*
    @Override
    public int compareTo(NormalOrderData dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NormalOrderData) {
            return ((NormalOrderData)obj).getId() == this.getId();
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
