package br.ufal.ic.security.main;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.ufal.ic.security.struct.Setup;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserInterface extends Application{
	
    private double initX;
    private double initY;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final Stage stage = new Stage(StageStyle.TRANSPARENT); 
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, 500, 420, Color.TRANSPARENT);
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
        
        Rectangle rect2 = new Rectangle(500,300);
        //rect2.setFill(Color.ANTIQUEWHITE);
        rect2.setFill(new RadialGradient(-0.3, 135, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.DARKGRAY),
                new Stop(1, Color.BLACK)
            }));
        
        Text title = new Text("GENERATOR WORKLOAD VULNERABILITY DATA");
        title.setFill(Color.WHITE);
        title.setEffect(new Lighting());
        title.setBoundsType(TextBoundsType.VISUAL);
        title.setFont(Font.font(Font.getDefault().getFamily(), 20));
        
        Button close = new Button("x");
        close.setOnAction((ActionEvent event) -> {
            System.exit(0);
        });
        Button min = new Button("_");
        min.setOnAction((ActionEvent event) -> {
            stage.setIconified(true);
        });
        HBox minclose = new HBox(5);
        minclose.getChildren().addAll(min,close);
        minclose.setAlignment(Pos.TOP_RIGHT);
        minclose.setOpacity(0.9);
        
        VBox mainVbox = new VBox();
        mainVbox.getChildren().add(minclose);
        mainVbox.setSpacing(10);
        //mainVbox.setPadding(new Insets(20, 0, 0, 60));
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
        CheckBox projectcb3 = new CheckBox("Xen");
        projectvbox1.getChildren().addAll(projectcb1, projectcb2, projectcb3);
        CheckBox projectcb4 = new CheckBox("Httpd");
        CheckBox projectcb5 = new CheckBox("Glibc");
        VBox projectvbox2 = new VBox();
        projectvbox2.setSpacing(10);
        projectvbox2.getChildren().addAll(projectcb4, projectcb5);
        HBox projecthbox = new HBox(10);
        projecthbox.getChildren().addAll(projectvbox1,projectvbox2);
        TitledPane t3 = new TitledPane("Projects", projecthbox);
        
        String[] vulnerabilities = {"all","AuthAlgorithms","Bypass","CrossSiteScripting","CSRF","DenialOfService","DirectoryTraversal","Doubt","ExecuteCode","FileInclusion","GainInformation","GainPrivileges","Httpresponsesplitting","InformationDisclosure","InputHandling","MemoryCorruption","ObtainInformation","Overflow","Phishing","RaceCondition","Spoofing","XMLInjection"};
        ArrayList<VBox> vboxes = new ArrayList<>();
        vboxes.add(new VBox(10));
        vboxes.add(new VBox(10));
        vboxes.add(new VBox(10));
        int i_hbox=0;
        for (int i = 0; i < vulnerabilities.length; i++) {
			vboxes.get(i_hbox).getChildren().add(new CheckBox(vulnerabilities[i]));
			if(i%(vulnerabilities.length/3)==0 && i!=0) i_hbox++;
		}
        CheckBox all = (CheckBox) vboxes.get(0).getChildren().get(0);
        all.setOnMouseClicked((MouseEvent me) -> {
        	boolean value = all.isSelected();
        	for (VBox vBox : vboxes) {
				for (int i = 0; i < vBox.getChildren().size(); i++) {
					((CheckBox) vBox.getChildren().get(i)).setDisable(value);
				}
			}
        	all.setDisable(false);
        });
        
        HBox vulnerabilityhbox = new HBox(10);
        vulnerabilityhbox.getChildren().addAll(vboxes);
        TitledPane t4 = new TitledPane("Vulnerabilities", vulnerabilityhbox);
        
        Accordion accordion = new Accordion();
        accordion.getPanes().add(t1);
        accordion.getPanes().add(t2);
        accordion.getPanes().add(t3);
        accordion.getPanes().add(t4);
        accordion.setOpacity(0.9);
        mainVbox.getChildren().addAll(accordion);
        
        Button next = new Button("Go");
        next.setOpacity(0.9);
        next.setLayoutX(450);
        next.setLayoutY(250);
        
        rootGroup.setOpacity(0.96);
        rootGroup.getChildren().addAll(rect2,next,mainVbox);
        
        next.setOnAction((EventHandler<ActionEvent>)me -> {
        	Setup.CROSS_VALIDATION = ((RadioButton) crosstg.getSelectedToggle()).getText();
        	Setup.GRANULARITY = ((RadioButton) granularitytg.getSelectedToggle()).getText();
        	ArrayList<String> projects = new ArrayList<>(5);
        	if(projectcb1.isSelected()) projects.add(projectcb1.getText());
        	if(projectcb2.isSelected()) projects.add(projectcb2.getText());
        	if(projectcb3.isSelected()) projects.add(projectcb3.getText());
        	if(projectcb4.isSelected()) projects.add(projectcb4.getText());
        	if(projectcb5.isSelected()) projects.add(projectcb5.getText());
        	Setup.PROJECTS = (String[]) projects.toArray(new String[projects.size()]);
        	ArrayList<String> name_vulnerabilities = new ArrayList<>();
        	for (VBox vBox : vboxes) {
				for (int i = 0; i < vBox.getChildren().size(); i++) {
					CheckBox iterator = ((CheckBox) vBox.getChildren().get(i));
					if(iterator.isSelected()&&!iterator.isDisable()) name_vulnerabilities.add(iterator.getText());
				}
			}
        	Setup.VULNERABILITIES_TYPE = (String[]) name_vulnerabilities.toArray(new String[name_vulnerabilities.size()]);
        	if(Setup.VULNERABILITIES_TYPE[0].equals("all")) Setup.VULNERABILITIES_TYPE = vulnerabilities;
        	if(Setup.PROJECTS.length==0){
        		JOptionPane.showMessageDialog(null, "No project selected!");
        	}
        	else if(Setup.VULNERABILITIES_TYPE.length==0){
        		JOptionPane.showMessageDialog(null, "No vulnerability selected!");
        	}
        	else rootGroup.getChildren().removeAll(next, mainVbox);

        });
	}

}
