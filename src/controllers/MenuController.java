package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Isaac
 */
public class MenuController implements Initializable{
    
    AppDesignController mainController;
    
    @FXML
    void closeMenu(){
        mainController.closeInfoPane();
    }
    
    @FXML void close(MouseEvent e){
        mainController.exit(e);
    }    
    
    @FXML
    void addBug() {
        Utils.Utils.loadBugDetailsInterface();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setMainController(AppDesignController controller){
        this.mainController = controller;
    }
    
}
