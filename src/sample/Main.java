package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/homescreen/home_screen.fxml"));
        Image image = new Image("icon.png");
        Scene scene = new Scene(root, 1000, 600, Color.LIGHTSKYBLUE);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.getIcons().add(image);
        stage.setTitle("PassVault");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


//        Text text = new Text();
//        text.setText("WHOAA");
//        text.setX(50);
//        text.setY(50);
//        text.setFont(Font.font("Verdana", 50));
//        text.setFill(Color.LIMEGREEN);
//        Line line = new Line();
//        line.setStartX(200);
//        line.setStartY(200);
//        line.setEndX(500);
//        line.setEndY(200);
//        line.setStrokeWidth(5);
//        line.setStroke(Color.RED);
//        line.setOpacity(0.5);



//        root.getChildren().add(text);
//        root.getChildren().add(line);
//        stage.setScene(scene);
//        stage.setTitle("<3");
//        stage.getIcons().add(image);
//        stage.show();