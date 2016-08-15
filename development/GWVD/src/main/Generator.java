package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("restriction")
public class Generator extends Application{
	
    private double initX;
    private double initY;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final Stage stage = new Stage(StageStyle.TRANSPARENT); 
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, 200, 200, Color.TRANSPARENT);
        stage.setScene(scene);
        //stage.setTitle("GENERATOR WORKLOAD VULNERABILITY DATA");
        stage.centerOnScreen();
        stage.show();
        
        //when mouse button is pressed, save the initial position of screen
        rootGroup.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - stage.getX();
            initY = me.getScreenY() - stage.getY();
        });

        //when screen is dragged, translate it accordingly
        rootGroup.setOnMouseDragged((MouseEvent me) -> {
            stage.setX(me.getScreenX() - initX);
            stage.setY(me.getScreenY() - initY);
        });
        
        //create dragger with desired size
        Circle dragger = new Circle(100, 100, 100);
        
        rootGroup.getChildren().addAll(dragger);
	}

}
