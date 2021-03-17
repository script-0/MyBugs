package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import App.MyBugs;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.services.BugServices;

/**
 *
 * @author Isaac
 */
public class AppDesignController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Label today;

    @FXML
    private FontAwesomeIconView todayIcon;

    @FXML
    private FontAwesomeIconView minimize;

    @FXML
    private FontAwesomeIconView expand;

    @FXML
    private FontAwesomeIconView close;

    @FXML
    private FontAwesomeIconView info;

    @FXML
    private FontAwesomeIconView connectState;
    
    @FXML
    private Text connectStateText;

    @FXML
    private JFXButton addBug;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchText;

    @FXML
    private FontAwesomeIconView searchIcon;

    @FXML
    private FontAwesomeIconView cancelSearch;

    @FXML
    private VBox resultBox;

    @FXML
    private ImageView placeHolderSearchBox;

    @FXML
    private Pane datePane;

    @FXML
    private Label dateMessage;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private FontAwesomeIconView dateOK;

    @FXML
    private Pane starPane;

    @FXML
    private Label starMessage;

    @FXML
    private JFXTextField feedBackText;

    @FXML
    private FontAwesomeIconView starOK;

    @FXML
    private FontAwesomeIconView star1;

    @FXML
    private FontAwesomeIconView star2;

    @FXML
    private FontAwesomeIconView star3;

    @FXML
    private FontAwesomeIconView star4;

    @FXML
    private FontAwesomeIconView star5;

    @FXML
    private Label buildNumber;

    @FXML
    private FontAwesomeIconView buildNbIcon;

    @FXML
    private Label log;

    @FXML
    private Label activeUsers;

    @FXML
    private AnchorPane entete;

    @FXML
    private AnchorPane center;

    @FXML
    private AnchorPane footer;

    AnchorPane menu;

    double menuWidth, menuHeight;

    BugServices bugServices;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cancelSearch.setVisible(false);
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            cancelSearch.setVisible(!newValue.isEmpty());
        });

        today.setText(LocalDate.now().toString());
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            today.setText(newValue.toString());
        });

        App.MyBugs.stars.add(star1);
        App.MyBugs.stars.add(star2);
        App.MyBugs.stars.add(star3);
        App.MyBugs.stars.add(star4);
        App.MyBugs.stars.add(star5);

        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                resultBox.getChildren().clear();
                MyBugs.bugs.clear();
                resultBox.getChildren().add(placeHolderSearchBox);
                resultBox.setAlignment(Pos.CENTER);
            }
        });
        /*resultBox.getCssMetaData().forEach((t) -> {
            System.out.println(t.getProperty());
        });*/
        //pagination.setPageCount(1);

        Platform.runLater(()->{
            //Loading Menu interface
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu_bar.fxml"));
                menu = (AnchorPane) loader.load();
                MenuController controller = loader.getController();
                controller.setMainController(this);
                center.heightProperty().addListener((observable, oldValue, newValue) -> {
                    menu.setPrefHeight(newValue.doubleValue());
                });
                menuWidth = menu.getPrefWidth();  
                menuHeight = menu.getPrefHeight();
            } catch (IOException ex) {
                Logger.getLogger(AppDesignController.class.getName()).log(Level.SEVERE, null, ex);
                //Loading of Menu Interface failed
            }
        });
        
        Platform.runLater(()->{
            log.setText("Connecting to Server ...");
            bugServices = Utils.Utils.getBugServices();
            if(bugServices.testDBConnection()){
                log.setText("Connected to Server");
                connectState.setGlyphName("CIRCLE");
                connectStateText.setText("Server is Up");
            }else{
                log.setText("Connection to Server Failed. Retring ...");
                connectStateText.setText("Server is Down");
            }
        });
        System.gc();
    }

    boolean isMaximized = false;

    @FXML
    void maximize() {
        expand.setDisable(true);
        isMaximized = !isMaximized;
        MyBugs.stage.setMaximized(isMaximized);
        expand.setGlyphName(isMaximized ? "CLONE" : "SQUARE_ALT");
        expand.setDisable(false);
    }

    @FXML
    void minimize() {
        MyBugs.stage.setIconified(true);
    }

    @FXML
    void sendComment() {
        //Send Comment
        feedBackText.clear();
        closeBuildPane();
    }

    void showPane(Node node) {
        if (node.getOpacity() != 0.0) {
            Utils.Utils.closeTransition(node);
        } else {
            Utils.Utils.openTransition(node);
        }
    }

    @FXML
    void closePane(MouseEvent e) {
        Utils.Utils.closeTransition((Node) e.getSource());
        e.consume();
    }

    @FXML
    void closeIcon() {
        if (close.getStyleClass().contains("closeIcon")) {
            close.getStyleClass().remove("closeIcon");
        } else {
            close.getStyleClass().add("closeIcon");
        }
    }

    @FXML
    void sendIcon() {
        if (starOK.getStyleClass().contains("starOKHover")) {
            starOK.getStyleClass().remove("starOKHover");
        } else {
            starOK.getStyleClass().add("starOKHover");
        }
    }

    @FXML
    void showDatePane(MouseEvent e) {
        showPane(datePane);
        e.consume();
    }

    @FXML
    void closeDatePane() {
        if (!datePicker.isShowing()) {
            Utils.Utils.closeTransition(datePane);
        }
    }

    @FXML
    void showBuildPane() {
        showPane(starPane);
    }

    @FXML
    void closeBuildPane() {
        Utils.Utils.closeTransition(starPane);
    }

    @FXML
    void showInfoPane() {
        Utils.Utils.menuTransition(menu, root, info, menuWidth, true, 200);
    }

    @FXML
    void closeInfoPane() {
        Utils.Utils.menuTransition(menu, root, info, menuWidth, false, 200);
    }

    @FXML
    void clearBugSearch() {
        searchText.clear();
    }

    @FXML
    void starClicked(MouseEvent e) {
        boolean flag = false;
        FontAwesomeIconView starClicked = (FontAwesomeIconView) e.getSource();
        for (FontAwesomeIconView s : App.MyBugs.stars) {
            s.setGlyphName(flag ? "STAR_ALT" : "STAR");
            if (s.equals(starClicked)) {
                flag = true;
            }
        }
        e.consume();
    }

    @FXML
    void mouseDragged(MouseEvent e) {
        MyBugs.stage.setX(e.getScreenX() + MyBugs.dx);
        MyBugs.stage.setY(e.getScreenY() + MyBugs.dy);
        e.consume();
    }

    @FXML
    void mousePressed(MouseEvent e) {
        /* if(e.getClickCount()==2){
            maximize();
            return;
        }*/
        MyBugs.dx = MyBugs.stage.getX() - e.getScreenX();
        MyBugs.dy = MyBugs.stage.getY() - e.getScreenY();
        e.consume();
    }

    @FXML
    void mouseReleased(MouseEvent e) {
        MyBugs.dx = 0;
        MyBugs.dy = 0;
        e.consume();
    }

    @FXML
    void exit(MouseEvent e) {
        MyBugs.stage.close();
    }

    @FXML
    void searchPaneAnimation() {
        Utils.Utils.searchPaneTransition(searchText, searchIcon);
        searchText.setOnMouseClicked((e) -> {
        });
        searchIcon.setOnMouseClicked((e) -> {
            Utils.Utils.searchPaneTransition(searchText, searchIcon);
        });
    }
    
    @FXML
    void addBug() {
        Utils.Utils.loadBugDetailsInterface();
    }
    
    
    @FXML
    void search(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            searchText.setDisable(true);
            resultBox.getChildren().clear();
            resultBox.getChildren().add(Utils.Utils.getWaitPane());
            resultBox.setAlignment(Pos.CENTER);
            log.setText("Searching for bugs ...");
            System.out.println("Searching for bugs ...");
            String query = searchText.getText();
            MyBugs.bugs.clear();
            //Interrogate DB.
            MyBugs.bugs.addAll(bugServices.search(query));
            System.out.println("[Success] Searching for bugs");
            log.setText("Displaying Results ..");
            if(MyBugs.bugs.size() == 0){
                resultBox.getChildren().clear();
                resultBox.getChildren().add(placeHolderSearchBox);
                resultBox.setAlignment(Pos.CENTER);
            }else{
                initPagination();
            }
            log.setText("All traitements are done");
            searchText.setDisable(false);
            System.gc();
        }
    }

    AnchorPane loadBug(int i) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bug.fxml"));
            AnchorPane result = (AnchorPane) loader.load();
            BugController controller = loader.getController();
            controller.loadBug(MyBugs.bugs.get(i));
            return result;
        } catch (IOException ex) {
            Logger.getLogger(AppDesignController.class.getName()).log(Level.SEVERE, null, ex);
            //Loading of Bug Interface failed
        }
        return null;
    }

    void initPagination() {
        int tmp = (int) Math.ceil(MyBugs.bugs.size() / 4.0);
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);
        resultBox.getChildren().clear();

        int f = MyBugs.bugs.size() > 4 ? 4 : MyBugs.bugs.size();
        
        for (int i = 0; i < f; i++) {
            resultBox.getChildren().add(loadBug(i));
        }
        pagination.setPageFactory(param -> {
            resultBox.getChildren().clear();

            int fin = 4 * param + 4;
            if (fin > MyBugs.bugs.size()) {
                fin = MyBugs.bugs.size();
            }

            for (int i = 4 * param; i < fin; i++) {
                resultBox.getChildren().add(loadBug(i));
            }
            resultBox.setAlignment(Pos.TOP_CENTER);
            return this.resultBox;
        });
        System.gc();
    }

}
