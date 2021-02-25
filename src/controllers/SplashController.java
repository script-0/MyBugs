/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author Isaac
 */
public class SplashController implements Initializable {

    @FXML
    Circle led1;

    @FXML
    Circle led2;

    @FXML
    Circle led3;

    @FXML
    Circle led4;

    final Circle[] leds = new Circle[4];

    Timer timer;

    int index = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leds[0] = led1;
        leds[1] = led2;
        leds[2] = led3;
        leds[3] = led4;

        timer = new Timer("Led Animation");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                leds[index].getStyleClass().remove("ledOn");
                /*if (index + 2 < 4) {
                    leds[index + 2].getStyleClass().add("ledOn");
                }

                if (index - 1 >= 0) {
                    leds[index - 1].getStyleClass().remove("ledOn");
                }*/

                leds[(index + 1) % 4].setEffect(leds[index].getEffect());
                leds[index].setEffect(null);

                index = (index + 1) % 4;

                leds[index].getStyleClass().add("ledOn");
            }

        }, 0, 1000);
    }

    public Scene loadApp() {
        Scene scene = null;
        try {
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/views/app_design.fxml"));
            scene = new Scene(root);
        } catch (IOException ex) {
            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

    public void cancelTimer() {
        timer.cancel();
    }

}
