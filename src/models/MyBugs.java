package models;

import controllers.SplashController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Isaac
 */
public class MyBugs extends Application {

    public static ArrayList<FontAwesomeIconView> stars = new ArrayList<>();

    public static Stage stage;

    public static double dx, dy;

    public static ObservableList<Bug> bugs = FXCollections.observableArrayList();

    Scene mainApp;
    SplashController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/splash.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        MyBugs.stage = stage;

        stage.setOnShown((WindowEvent e) -> {
            Thread t = new Thread() {
                @Override
                public void run() {
                    mainApp = controller.loadApp();
                }
            };
            t.setName("Loading Main App");
            t.setDaemon(true);
            t.start();
                        
            Timer timer = new Timer("Loading Main App Timer");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MyBugs.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Platform.runLater(() -> {
                        MyBugs.stage.setScene(mainApp);
                        controller.cancelTimer();
                    });
                }

            },5000);
        });

        stage.show();

        /*  Computer too slow
            double x=stage.getX(), y = stage.getY(), width = stage.getWidth(),height = stage.getHeight();
            Utils.Utils.stageTransition(stage, x, y, width, height, true);
         */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public boolean clearResult() {
        bugs.clear();
        return true;
    }

    public boolean search(String query) {
        //Interroger la B.D et charger les resultats dans 'bugs'
        return true;
    }

}
