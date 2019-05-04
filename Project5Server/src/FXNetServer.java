import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXNetServer extends Application{
	
	private NetworkConnection conn;
	private TextArea messages = new TextArea();
	private boolean gameDone = false;
	Button end = new Button();
	
	private Parent createContent() {
		messages.setPrefHeight(350);
		TextField input = new TextField();
		
		input.setOnAction(event -> {
			String message = "Server: ";
			message += input.getText();
			input.clear();
			
			messages.appendText(message + "\n");
			try {
				conn.send(message);
			}
			catch(Exception e) {
				
			}
			
		});
		
		VBox root = new VBox(20, messages, input);
		root.setPrefSize(400, 400);
		
		return root;			
	}
	
	public Parent endScene() {
		Button playAgain = new Button("Play Again?");
		Button quit = new Button("Quit");
		
		HBox end = new HBox(playAgain, quit);
		BorderPane root = new BorderPane();
		root.setCenter(end);
		root.setAlignment(end, Pos.CENTER);
		
		return root;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		Button on = new Button("On");
		Button off = new Button("Off");
		Button port1 = new Button("5555");
		Button port2 = new Button("4444");
		Text portText = new Text("Choose a port: ");
		
		end.setOnAction(e-> {
			primaryStage.setScene(new Scene(endScene()));
			primaryStage.show();
		});
		
		VBox serverPowerButtons = new VBox(20, on, off);
		VBox port = new VBox(10, portText, port1, port2);
		HBox openScreen = new HBox(10, serverPowerButtons, port);
		openScreen.setPrefSize(400, 400);
		
		EventHandler<ActionEvent> portOptions = e -> {
			Button b = (Button)e.getSource();
			int chosenPort = Integer.parseInt(b.getText());
			conn = createServer(chosenPort);
			try{
				init();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			on.setDisable(false);
			off.setDisable(false);
		};
		
		EventHandler<ActionEvent> serverPower = event -> {
			Button b = (Button)event.getSource();
			b.setDisable(true);
			if(b.getText() == "On") {
				primaryStage.setScene(new Scene(createContent()));
				primaryStage.show();
			}
			else {
				System.exit(0);
			}
		};
		
		port1.setOnAction(portOptions);
		port2.setOnAction(portOptions);		
		
		on.setOnAction(serverPower);
		off.setOnAction(serverPower);
		on.setDisable(true);
		off.setDisable(true);

		
		primaryStage.setScene(new Scene(openScreen));
		primaryStage.show();
		
	}
	
	@Override
	public void init() throws Exception{
		if(conn != null) {
			conn.startConn();
		}
	}
	
	@Override
	public void stop() throws Exception{
		if(conn != null) {
			conn.closeConn();
		}
	}
	
	private Server createServer(int port) {
		return new Server(port, data-> {
			Platform.runLater(()->{
				messages.appendText(data.toString() + "\n");
			});
		});
	}
	

}
