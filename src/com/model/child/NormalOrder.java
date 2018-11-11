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

public class NormalOrder extends SuperModel implements Comparable<NormalOrder>{
    private String date;
    private String time;
    private int id;


    public NormalOrder() {}

    public NormalOrder(int id) {
        this.id = id;
    }

    public NormalOrder(String date, String time, int id) {
        this.date = date;
        this.time = time;
        this.id = id;
    }
   
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //------------------------- User Methods ----------------------------//
    public String getDetail() {
        return "**************";
    }
    
    //-----------------------------------------------------------------//
    
    @Override
    public String toString() {
        return  getDate()       + Symbol.SPLIT +
                getTime()       + Symbol.SPLIT +
                getId();
    }      
    
    @Override
    public int compareTo(NormalOrder dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NormalOrder) {
            return ((NormalOrder)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {        
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }  
}
