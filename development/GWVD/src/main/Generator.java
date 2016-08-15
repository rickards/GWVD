package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        Scene scene = new Scene(rootGroup, 600, 300, Color.TRANSPARENT);
        stage.setScene(scene);
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
        
        Rectangle rect2 = new Rectangle(600,300);
        rect2.setFill(Color.ANTIQUEWHITE);
        
        Text title = new Text("GENERATOR WORKLOAD VULNERABILITY DATA");
        title.setFill(Color.DARKOLIVEGREEN);
        title.setEffect(new Lighting());
        title.setBoundsType(TextBoundsType.VISUAL);
        title.setFont(Font.font(Font.getDefault().getFamily(), 20));
        
        VBox mainVbox = new VBox();
        mainVbox.setSpacing(10);
        mainVbox.setPadding(new Insets(20, 0, 0, 60));
        mainVbox.setAlignment(Pos.TOP_CENTER);
        mainVbox.getChildren().addAll(title);
        
        VBox granularitybox = new VBox(10);
        ToggleGroup granularitytg = new ToggleGroup();
        RadioButton granularityrb1 = new RadioButton("Files");
        granularityrb1.setToggleGroup(granularitytg);
        RadioButton granularityrb2 = new RadioButton("Functions");
        granularityrb2.setToggleGroup(granularitytg);
        granularityrb2.setSelected(true);
        granularitybox.getChildren().addAll(granularityrb1,granularityrb2);
        TitledPane t1 = new TitledPane("Granularity", granularitybox);
        
        VBox crossvbox = new VBox(10);
        ToggleGroup crosstg = new ToggleGroup();
        RadioButton crossrb1 = new RadioButton("n fold-validation");
        crossrb1.setToggleGroup(crosstg);
        RadioButton crossrb2 = new RadioButton("release fold-validation");
        crossrb2.setToggleGroup(crosstg);
        crossrb2.setSelected(true);
        crossvbox.getChildren().addAll(crossrb1,crossrb2);
        TitledPane t2 = new TitledPane("Cross-validation ", crossvbox);
        
        TitledPane t3 = new TitledPane("Projects", new Rectangle(120, 50, Color.RED));
        
        Accordion accordion = new Accordion();
        accordion.getPanes().add(t1);
        accordion.getPanes().add(t2);
        accordion.getPanes().add(t3);
        accordion.setMinSize(100, 100);
        accordion.setPrefSize(100, 200);
        mainVbox.getChildren().addAll(accordion);
        
        rootGroup.getChildren().addAll(rect2,mainVbox);
	}

}
