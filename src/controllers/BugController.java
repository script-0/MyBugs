/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadBug(BugEntity bug) {
        this.bugData = bug;
        setBugLabel(bug.getLabel());
        setLabelDate(bug.getLastUpdateDate());
        if (bug.isResolved()) {
            checkBug.getStyleClass().add("resolved");
        }
    }

    public boolean setBugLabel(String label) {
        bugLabel.setText(label);
        return true;
    }

    public boolean setLabelDate(LocalDateTime date) {
        dateBug.setText(Utils.Utils.format(date));
        return true;
    }

    @FXML
    void view(){
        displayBug(true);
    }
    
    @FXML
    void edit(){
        displayBug(false);
    }
    
    void displayBug(boolean isView) {
        System.out.println("Loading Bug Details Interface ...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bugDetails.fxml"));
            AnchorPane bugDetails = (AnchorPane) loader.load();
            BugDetailsController bugDetailsControl = loader.getController();
            bugDetailsControl.loadBug(bugData, isView);
            System.out.println("[Success] Loading Bug Details Interface");           
            bugDetailsControl.show();
            
        } catch (IOException ex) {
            Logger.getLogger(AppDesignController.class.getName()).log(Level.SEVERE, null, ex);
            //Loading  failed
        }
        /* } else {
            bugDetailsControl.loadBug(bugData);
        }*/

    }

}
