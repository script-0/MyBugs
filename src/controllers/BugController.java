/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.entities.BugEntity;

/**
 *
 * @author Isaac
 */
public class BugController implements Initializable {

    @FXML
    private AnchorPane bug;

    @FXML
    private FontAwesomeIconView viewBug;

    @FXML
    private FontAwesomeIconView editBug;

    @FXML
    private FontAwesomeIconView checkBug;

    @FXML
    private Label dateBug;

    @FXML
    private Label bugLabel;

    private BugEntity bugData;

    /*public static BugDetailsController bugDetailsControl = null;

    public static Pane bugDetails = null;
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadBug(BugEntity bug) {
        this.bugData = bug;
        setBugLabel(bug.getLabel());
        setLabelDate(bug.getCreationDate());
        if (bug.isResolved()) {
            checkBug.getStyleClass().add("resolved");
        }
    }

    public boolean setBugLabel(String label) {
        bugLabel.setText(label);
        return true;
    }

    public boolean setLabelDate(Date date) {
        dateBug.setText(Utils.Utils.format(date));
        return true;
    }

    @FXML
    void displayBug() {
        //if (bugDetails == null) {
        //Loading Bug Details interface
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bugDetails.fxml"));
            AnchorPane bugDetails = (AnchorPane) loader.load();
            BugDetailsController bugDetailsControl = loader.getController();
            bugDetailsControl.loadBug(bugData);
            System.out.println("Loading Bug Details Interface success");
            Scene scene = new Scene(bugDetails);
            scene.setFill(Color.TRANSPARENT);
            //Stage stage = new Stage(StageStyle.TRANSPARENT);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            
            bugDetailsControl.setStage(stage);
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AppDesignController.class.getName()).log(Level.SEVERE, null, ex);
            //Loading  failed
        }
        /* } else {
            bugDetailsControl.loadBug(bugData);
        }*/

    }

}
