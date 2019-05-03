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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXNetClient extends Application{
	
	private NetworkConnection  conn;
	private TextArea messages = new TextArea();
	int chosenPort;
	String chosenIP;
	Text score = new Text();
	BorderPane homePage = new BorderPane();
	private boolean gameDone = false;
	private Button end = new Button();
	
	private Parent createContent() {
		Button rock = new Button("Rock");
		Image pic = new Image("Rock.jpg");
		ImageView v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		rock.setGraphic(v);
		
		Button paper = new Button("Paper");
		pic = new Image("paper.jpg");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		paper.setGraphic(v);
		
		Button scissors = new Button("Scissors");
		pic = new Image("scissors.jpg");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		scissors.setGraphic(v);
		
		Button lizard = new Button("Lizard");
		pic = new Image("lizard.jpg");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		lizard.setGraphic(v);
		
		Button spock = new Button("Spock");
		pic = new Image("spock.jpg");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		spock.setGraphic(v);
		
		Button exit = new Button("Exit");
		//score.setText("Score: 0");
		
		messages.setPrefSize(75, 75);
		TextField input = new TextField();
		
		EventHandler<ActionEvent> choiceEvent = e ->{
			String message;
			Button b = (Button)e.getSource();
			if(b.getText() == "Rock") {
				try {conn.send("Rock");}catch(Exception ex1) { System.out.println("Error");}
			}
			else if(b.getText() == "Paper") {
				try {conn.send("Paper");}catch(Exception ex2) { System.out.println("Error");}
			}
			else if(b.getText() == "Scissors") {
				try {conn.send("Scissors");}catch(Exception ex3) { System.out.println("Error");}
			}
			else if(b.getText() == "Lizard") {
				try {conn.send("Lizard");}catch(Exception ex4) { System.out.println("Error");}
			}
			else if(b.getText() == "Spock") {
				try {conn.send("Spock");}catch(Exception ex5) { System.out.println("Error");}
			}
		};
		rock.setOnAction(choiceEvent);
		paper.setOnAction(choiceEvent);
		scissors.setOnAction(choiceEvent);
		lizard.setOnAction(choiceEvent);
		spock.setOnAction(choiceEvent);
		
		input.setOnAction(event -> {
			String message = input.getText();
			input.clear();
			
			messages.appendText(message + "\n");
			try {
				conn.send(message);
			}
			catch(Exception e) {
				
			}
			
			while(updateScore()) {
				System.out.println("Did not update");
			}
			
		});
		
		VBox choices = new VBox(rock, paper, scissors, lizard, spock);
		BorderPane root = new BorderPane();
		root.setLeft(choices);
		//root.setCenter(score);
		//root.setAlignment(score, Pos.TOP_CENTER);
		root.setBottom(messages);
		root.setAlignment(messages, Pos.BOTTOM_CENTER);
		root.setTop(exit);
		root.setAlignment(exit, Pos.TOP_RIGHT);
		//VBox root = new VBox(20, messages, input);
		root.setPrefSize(400, 400);
		
		homePage = root;
		return root;
						
	}
	
	public Parent endScene() {
		Button playAgain = new Button("Play Again?");
		Button quit = new Button("Quit");
		
		HBox end = new HBox(playAgain, quit);
		BorderPane root = new BorderPane();
		root.setCenter(end);
		root.setAlignment(end, Pos.CENTER);
		
//		playAgain.setOnAction(e->{
//			try {
//				conn.send("Play again");
//			}catch(Exception ex) {
//				ex.printStackTrace();
//			}
//		});
//		
//		quit.setOnAction(e->{
//			System.exit(0);
//			try{conn.send("Quit");;}catch(Exception ex) {ex.printStackTrace();}
//		});
		
		root.setPrefSize(400, 400);
		root.setBottom(messages);
		return root;
	}
	
	public boolean updateScore() {
		if(score.getText() == "Score: " + conn.getConnThread().getScore())
			return true;
		score.setText(conn.getConnThread().getScore());
		homePage.setCenter(score);
		homePage.setAlignment(score, Pos.TOP_CENTER);
		return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Text portText = new Text("Please choose a port:");
		Button port1 = new Button("5555");
		Button port2 = new Button("4444");
		
		Text ipText = new Text("Please choose an IP address");
		Button ip1 = new Button("127.0.0.1");
		
		VBox ports = new VBox(portText, port1, port2);
		VBox ips = new VBox(ipText, ip1);
		HBox selection = new HBox(ports, ips);
		selection.setPrefSize(400, 400);
		
		ip1.setDisable(true);
	
		EventHandler<ActionEvent> portsEvent = e -> {
			ip1.setDisable(false);
			
			Button b = (Button)e.getSource();
			chosenPort = Integer.parseInt(b.getText());
			
		};
		
		end.setOnAction(e-> {
			primaryStage.setScene(new Scene(endScene()));
			primaryStage.show();
		});
		
		EventHandler<ActionEvent> ipEvent = e -> {
			Button b = (Button) e.getSource();
			chosenIP = b.getText();
			conn = createClient(chosenIP, chosenPort);
			try{init();} catch(Exception ex) { ex.printStackTrace();}
			primaryStage.setScene(new Scene(createContent()));
			primaryStage.show();
		};
		
		port1.setOnAction(portsEvent);
		port2.setOnAction(portsEvent);
		ip1.setOnAction(ipEvent);
		
		primaryStage.setScene(new Scene(selection));
		primaryStage.show();
		
	}
	
	@Override
	public void init() throws Exception{
		if( conn != null) {
			conn.startConn();
		}
	}
	
	@Override
	public void stop() throws Exception{
		if(conn != null) {
			conn.closeConn();
		}
	}
	
	
	private Client createClient(String ip, int port) {
		return new Client(ip, port, data -> {
			Platform.runLater(()->{
				if(data.toString().intern() == "New game?") {
					end.fire();
				}
				messages.appendText(data.toString() + "\n");
				
			});
		});
	}

}