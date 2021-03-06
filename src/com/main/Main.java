/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.base.client.impl.CustomerClientImpl;
import com.base.client.impl.CustomerOrderClientImpl;
import com.base.client.impl.CustomerOrderDataClientImpl;
import com.base.client.impl.NormalOrderClientImpl;
import com.base.client.impl.NormalOrderDataClientImpl;
import com.base.client.impl.UserClientImpl;
import com.manifest.View;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.view.ctrl.MessageBoxViewCtrl;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author RISITH-PC
 */
public class Main extends Application {
    
    private Thread timeThread;
    public static Stage primaryStage;
    public static Task<Void> timeTask;
    
    @Override
    public void start(Stage stage){

        timeTask = new Task<Void>() {
            @Override 
            public Void call() throws InterruptedException {
                while (true){
                    updateMessage(new SimpleDateFormat("yyyy-MMMM-dd  hh:mm:ss a").format(new Date())); 
                    Thread.sleep(1000);                   
                }
            }
        };       
        timeThread = new Thread(timeTask);
        timeThread.setDaemon(true);
        timeThread.start();
        
        //--------------------------- Start Stage ----------------------------//
        try {
            primaryStage = stage;
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(String.format(View.PATH, View.LOGIN)))));
            stage.getIcons().add(new Image(View.IMAGE_ICON));
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("This is a master extend");
        initializeDatabase();
        launch(args);
    }
    
    public static void initializeDatabase(){
        try {
            CustomerClientImpl.getInstance().loadAll();
            CustomerOrderClientImpl.getInstance().loadAll();
            NormalOrderClientImpl.getInstance().loadAll();
            UserClientImpl.getInstance().loadAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
