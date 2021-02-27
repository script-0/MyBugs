package Utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Isaac
 */
public class Utils {

    private static ImageView waitPane = null;
    
    public static boolean init(){
        try {
            waitPane = (ImageView)FXMLLoader.load(Utils.class.getResource("/views/wait.fxml"));
            return true;
        } catch (IOException ex) {
            //Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("loading WaitPane failed");
            return false;
        }
    }
    
    public static ImageView getWaitPane(){
        if(waitPane == null){
            return init() ? waitPane : null;
        }
        return waitPane;
    }
    
    public static void openTransition(Node b) {
        b.setVisible(true);
        transition(b, true);
    }

    public static void closeTransition(Node b) {
        transition(b, false);
    }

    public static void transition(Node b, boolean open) {
        if ((open && b.getOpacity() != 0.0) || (!open && b.getOpacity() == 0.0)) {
            return;
        }

        b.setDisable(true);

        ScaleTransition st = new ScaleTransition(Duration.seconds(.1), b);
        st.setFromX((open ? .8 : 1));
        st.setFromY((open ? .8 : 1));
        st.setToX((open ? 1 : 0));
        st.setToY((open ? 1 : 0));

        FadeTransition ft = new FadeTransition(Duration.seconds(.15), b);
        ft.setFromValue((open ? .2 : 1));
        ft.setToValue((open ? 1 : 0));
        ft.setOnFinished((event) -> {
            b.setDisable(false);
            if (!open) {
                b.setVisible(false);
            }
        });
        st.play();
        ft.play();
    }

    public static void menuTransition(AnchorPane pane, BorderPane parent, FontAwesomeIconView info, double width, boolean open, int timer) {

        pane.setPrefWidth(0);
        Animation transition;
        if (open) {
            info.setDisable(true);
            parent.setLeft(pane);
            transition = new Transition() {
                {
                    setCycleDuration(Duration.millis(timer));
                    setInterpolator(Interpolator.EASE_IN);
                }

                @Override
                protected void interpolate(double frac) {
                    pane.setPrefWidth(width * frac);
                }
            };
            transition.setOnFinished((e) -> {
                info.setVisible(false);
            });
        } else {
            transition = new Transition() {
                {
                    setCycleDuration(Duration.millis(timer));
                    setInterpolator(Interpolator.EASE_IN);
                }

                @Override
                protected void interpolate(double frac) {
                    pane.setPrefWidth(width * (1 - frac));
                }
            };
            transition.setOnFinished((e) -> {
                parent.setLeft(null);
                info.setVisible(true);
                info.setDisable(false);
            });
        }
        transition.play();
    }

    public static void stageTransition(Stage stage, double x, double y, double width, double height, boolean open, int timer) {
        stage.setX(0);
        stage.setY(0);
        stage.setWidth(0);
        stage.setHeight(0);
        Animation transition;
        if (open) {
            transition = new Transition() {
                {
                    setCycleDuration(Duration.millis(timer));
                    setInterpolator(Interpolator.EASE_IN);
                }

                @Override
                protected void interpolate(double frac) {
                    stage.setHeight(height * frac);
                    stage.setWidth(width * frac);
                    stage.setX(x * frac);
                    stage.setY(y * frac);
                }
            };
        } else {
            transition = new Transition() {
                {
                    setCycleDuration(Duration.millis(timer));
                    setInterpolator(Interpolator.EASE_IN);
                }

                @Override
                protected void interpolate(double frac) {
                    stage.setHeight(height * (1 - frac));
                    stage.setWidth(width * (1 - frac));
                    stage.setX(x * (1 - frac));
                    stage.setY(y * (1 - frac));
                }
            };
        }
        transition.play();
    }

    public static void stageTransition(Stage stage, double x, double y, double width, double height, boolean open) {
        stageTransition(stage, x, y, width, height, open, 100);
    }
    
    public static void searchPaneAnimation(Node pane, FontAwesomeIconView icon,double xPane, double xIcon, int timer){
        
        double x0 = AnchorPane.getLeftAnchor(pane);
        
        Animation transition = new Transition() {
                {
                    setCycleDuration(Duration.millis(timer));
                    setInterpolator(Interpolator.EASE_IN);
                }

                @Override
                protected void interpolate(double frac) {
                    AnchorPane.setLeftAnchor(pane, frac*(xPane-x0) +x0);
                    AnchorPane.setLeftAnchor(icon, frac*(xIcon-x0-7) +x0+7);
                }
            };
        
        transition.play();
    }
    
    public static void searchPaneTransition(Node pane, FontAwesomeIconView icon){
        double x0 = AnchorPane.getLeftAnchor(pane);
        Utils.searchPaneAnimation(pane, icon, x0==435?35:435, x0==435?42:442, 300);
    }

    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
    
    public static String format(Date date){
        return formatter.format(date);
    }
    
}
