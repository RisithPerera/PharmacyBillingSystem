/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client.impl;

import com.base.client.UserClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.User;
import java.sql.*;
import javafx.collections.ObservableList;

/**
 *
 * @author RISITH-PC
 */
public class UserClientImpl implements UserClient{ 
    
    private static UserClientImpl userClientImpl;
    private static ObservableList<User> userList;

    public UserClientImpl() {
        userList = (ObservableList<User>) ListConnection.getInstance().getUserList();
    }

    public static UserClientImpl getInstance() {
        if (userClientImpl == null) {
            userClientImpl = new UserClientImpl();
        }
        return userClientImpl;
    }
      
    @Override
    public boolean add(User user) throws SQLException, ClassNotFoundException {

        if (user == null) return false;

        String query = "Insert into user value (?,?,?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);

            state.setObject(1, user.getDate());
            state.setObject(2, user.getTime());
            state.setObject(3, user.getId());
            state.setObject(4, user.getUserName());
            state.setObject(5, user.getPassword());
            state.setObject(6, user.getType());

            if(state.executeUpdate()>0){
                userList.add(user);
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }finally{
            conn.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {

        if (user == null) return false;

        String query =  "Update user set    " +
                        "    recordDate = ?," +
                        "    recordTime = ?," +
                        "    userName   = ?," +
                        "    password   = ? " +
                        "    userType   = ?," +
                        "where userId = ?;";

        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(1, user.getDate());
            state.setObject(2, user.getTime());
            state.setObject(3, user.getUserName());
            state.setObject(4, user.getPassword());
            state.setObject(5, user.getType());
            state.setObject(6, user.getId());
            if (state.executeUpdate() > 0) {
                int index = userList.indexOf(user);
                if (index != -1) userList.set(index, user);
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        } finally {
            conn.setAutoCommit(true);
        }
    }


    @Override
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        if (id < 0) return false;
        User user = new User(id);
        userList.remove(user);

        String query = "Delete from user where userId = ?";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        state.setObject(1, id);
        return state.executeUpdate() > 0;
    }

    @Override
    public User search(int id){

        if (id < 0) return null;

        User user = new User(id);
        int index = userList.indexOf(user);
        if (index != -1) {
            return userList.get(index);
        }
        return null;
    }

    @Override
    public ObservableList<User> getAll(){
        return userList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        userList.clear();
        String query = "Select * from user";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            User user = new User();

            user.setDate(result.getString(1));
            user.setTime(result.getString(2));
            user.setId(result.getInt(3));
            user.setUserName(result.getString(4));
            user.setPassword(result.getString(5));
            user.setType(result.getInt(6));

            userList.add(user);
        }
        System.out.println("User List Loaded : " + userList.size());
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "Select userId+1  as nextID from user order by 1 desc limit 1";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt("nextId");
        }
        return 0;
    }
}