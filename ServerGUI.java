package project5;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServerGUI extends Application{
    
    private TextArea messages = new TextArea();
    private boolean gameDone = false;
    Button end = new Button();
    
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }
    public void setStandards() {
        ButtonBase start = null;
        start.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage myStage = null;
                
            }
        });
        
        ButtonBase exit = null;
        //If the exit button is pressed, close the application
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        
        //Create panes and set them up
        BorderPane startPane = new BorderPane();
        BorderPane playerPane = new BorderPane();
        BorderPane gamePane = new BorderPane();
        startPane.setPadding(new Insets(70));
        playerPane.setPadding(new Insets(70));
        
        Node Intro = null;
        VBox paneCenter = new VBox(10, Intro, start);
        
        
        
        startPane.setCenter(paneCenter);
        startPane.setBottom(exit);
        Node playerCenter = null;
        playerPane.setCenter(playerCenter);
        Scene scene2 = new Scene(playerPane,400, 500, Color.GREEN);
        Scene scene = new Scene(startPane, 400, 500, Color.GREEN);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        Text Intro = new Text();
        
        Intro.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        Intro.setFill(Color.BROWN);
        Intro.setText("Welcome to Word Hunt!");
        Button on = new Button("Join");
        Button off = new Button("Quit");
        Button port1 = new Button("24");
        //Button port2 = new Button("4444");
        Text portText = new Text("Choose a port: ");
        
        end.setOnAction(e-> {
            
            primaryStage.show();
        });
        
        VBox serverPowerButtons = new VBox(20, on, off);
        VBox port = new VBox(10, portText, port1);
        //HBox openScreen = new HBox(10, serverPowerButtons, port);
        //openScreen.setPrefSize(400, 400);
        
        
        
        
        
        
        on.setDisable(true);
        off.setDisable(true);
        //grid.add(openScreen, 40, 40);
        grid.add(Intro, 5, 5);
        grid.add(serverPowerButtons, 22, 40);
        grid.add(port, 30, 90);
        grid.setBackground(Background.EMPTY);
        Scene newScene = new Scene(grid, 600, 460);
        newScene.setFill(Color.PURPLE);
        primaryStage.setScene(newScene);
        primaryStage.show();
        
    }
    
    
    
    
    
}

