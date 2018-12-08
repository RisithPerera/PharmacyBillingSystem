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
 * @author RISITH-PC
 */

public class Drug extends SuperModel implements Comparable<Drug> {
    private String date;
    private String time;
    private int id;
    private String name;
    private double price;

    public Drug() {
    }

    public Drug(int id) {
        this.id = id;
    }

    public Drug(String date, String time, int id, String name, double price) {
        this.date = date;
        this.time = time;
        this.id = id;
        this.name = name;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //---------------------------- Calculatons -------------------------------------//


    //---------------------------- Override Methods -------------------------------------//

    @Override
    public String toString() {
        return getDate() + Symbol.SPLIT +
                getTime() + Symbol.SPLIT +
                getId() + Symbol.SPLIT +
                getName() + Symbol.SPLIT +
                getPrice();
    }


    @Override
    public int compareTo(Drug dto) {
        boolean logic = dto.getId() > this.getId();
        return logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Drug) {
            return ((Drug) obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}
