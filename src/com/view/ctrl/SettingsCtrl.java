/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.ctrl;

import com.base.client.impl.CustomerClientImpl;
import com.base.client.impl.UserClientImpl;
import com.main.Main;
import com.manifest.Data;
import com.manifest.Message;
import com.model.child.Customer;
import com.model.child.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author RISITH-PC
 */
public class SettingsCtrl implements Initializable {

    @FXML
    private Button restoreDataBtn, backupBtn, addNewUserBtn;

    @FXML
    private TextField locationText, usernameText;

    @FXML
    private ComboBox<String> userTypeCombo;

    @FXML
    private PasswordField passwordText, confirmPasswordText;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> dateCol, timeCol, usernameCol, userTypeCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        userTable.getItems().setAll(UserClientImpl.getInstance().getAll());
        userTypeCombo.getItems().addAll(Data.USER_TYPE);
    }
    
    @FXML
    void addNewUserBtnEvent(ActionEvent event) {
        if(passwordText.getText().equals(confirmPasswordText.getText())){
            try {
                User user = new User();
                user.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                user.setTime(new SimpleDateFormat("hh:mm:ss").format(new Date()));
                user.setId(UserClientImpl.getInstance().getNextId());
                user.setUserName(usernameText.getText());
                user.setPassword(passwordText.getText());
                user.setType(userTypeCombo.getSelectionModel().getSelectedIndex());

                UserClientImpl.getInstance().add(user);
                userTable.getItems().setAll(UserClientImpl.getInstance().getAll());
             
                MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.ADD, Data.USER));
            } catch (SQLException e) {
                MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(), e.getMessage());
            } catch (ClassNotFoundException e) {
                MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(), e.getMessage());
            }
        }else{
            System.out.println("Passwords aren't matched....!");
        }
        clearFields();
    }

    @FXML
    void restoreDataBtnEvent(ActionEvent event) {
        Main.initializeDatabase();
    }
    
    private void clearFields(){
        usernameText.clear();
        passwordText.clear();
        userTypeCombo.getSelectionModel().select(null);
    }
}
