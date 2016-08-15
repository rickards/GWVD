package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
        Scene scene = new Scene(rootGroup, 600, 350, Color.TRANSPARENT);
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
        
        Rectangle rect2 = new Rectangle(600,350);
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
        
        VBox projectvbox1 = new VBox();
        projectvbox1.setSpacing(10);
        CheckBox projectcb1 = new CheckBox("Mozilla");
        CheckBox projectcb2 = new CheckBox("Kernel");
        CheckBox projectcb3 = new CheckBox("Xen Hipervisor");
        projectvbox1.getChildren().addAll(projectcb1, projectcb2, projectcb3);
        CheckBox projectcb4 = new CheckBox("Httpd");
        CheckBox projectcb5 = new CheckBox("Glibc");
        VBox projectvbox2 = new VBox();
        projectvbox2.setSpacing(10);
        projectvbox2.getChildren().addAll(projectcb4, projectcb5);
        HBox projecthbox = new HBox(10);
        projecthbox.getChildren().addAll(projectvbox1,projectvbox2);
        TitledPane t3 = new TitledPane("Projects", projecthbox);
        
        VBox vulnerabilityvbox1 = new VBox();
        vulnerabilityvbox1.setSpacing(10);
        CheckBox vulnerabilitycb1 = new CheckBox("Mozilla");
        CheckBox vulnerabilitycb2 = new CheckBox("Kernel");
        CheckBox vulnerabilitycb3 = new CheckBox("Xen Hipervisor");
        CheckBox vulnerabilitycb4 = new CheckBox("Httpd");
        CheckBox vulnerabilitycb5 = new CheckBox("Httpd");
        vulnerabilityvbox1.getChildren().addAll(vulnerabilitycb1, vulnerabilitycb2, vulnerabilitycb3, vulnerabilitycb4, vulnerabilitycb5);
        
        CheckBox vulnerabilitycb6 = new CheckBox("Glibc");
        CheckBox vulnerabilitycb7 = new CheckBox("Glibc");
        CheckBox vulnerabilitycb8 = new CheckBox("Glibc");
        CheckBox vulnerabilitycb9 = new CheckBox("Glibc");
        CheckBox vulnerabilitycb10 = new CheckBox("Glibc");
        VBox vulnerabilityvbox2 = new VBox();
        vulnerabilityvbox2.setSpacing(10);
        vulnerabilityvbox2.getChildren().addAll(vulnerabilitycb6, vulnerabilitycb7, vulnerabilitycb8, vulnerabilitycb9, vulnerabilitycb10);
        
        CheckBox vulnerabilitycb11 = new CheckBox("Glibc");
        CheckBox vulnerabilitycb12 = new CheckBox("Glibc");
        CheckBox vulnerabilitycb13 = new CheckBox("Glibc");
        CheckBox vulnerabilitycb14 = new CheckBox("Glibc");
        CheckBox vulnerabilitycb15 = new CheckBox("Glibc");
        VBox vulnerabilityvbox3 = new VBox();
        vulnerabilityvbox3.setSpacing(10);
        vulnerabilityvbox3.getChildren().addAll(vulnerabilitycb11, vulnerabilitycb12, vulnerabilitycb13, vulnerabilitycb14, vulnerabilitycb15);
        
        HBox vulnerabilityhbox = new HBox(10);
        vulnerabilityhbox.getChildren().addAll(vulnerabilityvbox1, vulnerabilityvbox2, vulnerabilityvbox3);
        TitledPane t4 = new TitledPane("Vulnerabilities", vulnerabilityhbox);
        
        Accordion accordion = new Accordion();
        accordion.getPanes().add(t1);
        accordion.getPanes().add(t2);
        accordion.getPanes().add(t3);
        accordion.getPanes().add(t4);
        accordion.setMinSize(100, 100);
        accordion.setPrefSize(100, 270);
        mainVbox.getChildren().addAll(accordion);
        
        rootGroup.getChildren().addAll(rect2,mainVbox);
	}

}
