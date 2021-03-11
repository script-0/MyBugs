/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.entities.BugEntity;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class BugDetailsController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private FontAwesomeIconView expand;

    @FXML
    private FontAwesomeIconView closeButton;

    @FXML
    private TextField labelTextField;

    @FXML
    private TextArea solutionArea;

    @FXML
    private CheckBox isResolved;

    @FXML
    private JFXButton save;

    @FXML
    private FontAwesomeIconView saveIcon;

    @FXML
    private JFXButton cancel;

    @FXML
    private FontAwesomeIconView cancelIcon;

    @FXML
    private JFXButton includeFiles;

    @FXML
    private FontAwesomeIconView includeFilesIcon;

    private Stage stage = null;

    private BugEntity bug = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void loadBug(BugEntity bug) {
        this.bug = bug;
        this.labelTextField.setText(bug.getLabel());
        this.solutionArea.setText(bug.getSolution());
        this.isResolved.setSelected(bug.isResolved());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        this.setStage(stage);

        stage.show();
    }

    boolean isMaximized = false;

    @FXML
    void maximize() {
        expand.setDisable(true);
        isMaximized = !isMaximized;
        stage.setMaximized(isMaximized);
        expand.setGlyphName(isMaximized ? "CLONE" : "SQUARE_ALT");
        expand.setDisable(false);
    }

    @FXML
    void minimize() {
        stage.setIconified(true);
    }

    @FXML
    void closeIcon() {
        if (closeButton.getStyleClass().contains("closeIcon")) {
            closeButton.getStyleClass().remove("closeIcon");
        } else {
            closeButton.getStyleClass().add("closeIcon");
        }
    }

    /**
     * ******** Managing moving *************
     */
    double dx = 0.0, dy = 0.0;

    @FXML
    void mouseDragged(MouseEvent e) {
        stage.setX(e.getScreenX() + dx);
        stage.setY(e.getScreenY() + dy);
        e.consume();
    }

    @FXML
    void mousePressed(MouseEvent e) {
        dx = stage.getX() - e.getScreenX();
        dy = stage.getY() - e.getScreenY();
        e.consume();
    }

    @FXML
    void mouseReleased(MouseEvent e) {
        dx = 0;
        dy = 0;
        e.consume();
    }

    /**
     * **********************
     */
    @FXML
    void close() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }

    @FXML
    void cancel() {
        //traitment
        close();
    }

    @FXML
    void save() {
        //traitment
        boolean isModified = bug.getLabel().equalsIgnoreCase(labelTextField.getText())
                || bug.getSolution().equalsIgnoreCase(solutionArea.getText())
                || (bug.isResolved() == isResolved.isSelected());
        //Si a moins une modification a été effectuée alors on enregistre dans le B.D. 
        //On est si regardant à cause de la politique d'affichage des bugs, les plus récemment mis à jour sont affichés en premier
        if (isModified) {
            this.bug.setLabel(labelTextField.getText());
            this.bug.setSolution(solutionArea.getText());
            this.bug.setResolved(isResolved.isSelected());
            int result = Utils.Utils.getBugServices().update(this.bug);
            if (result == 1) {
                // All is OK!
            } else if (result == 0) {
                //No Bug in the Database
            } else if (result < 0) {
                //Error
            } else { // result > 1
                //More than 1 row with the same id. (Impossible a priori)
            }
        }
        close();
    }

}
