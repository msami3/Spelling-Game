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
		Button A = new Button("A");
		Image pic = new Image("A.png");
		ImageView v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		A.setGraphic(v);
		
		Button B = new Button("B");
		pic = new Image("B.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		B.setGraphic(v);
		
		Button C = new Button("C");
		pic = new Image("C.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		C.setGraphic(v);
		
		Button D = new Button("D");
		pic = new Image("D.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		D.setGraphic(v);
		
		Button E = new Button("E");
		pic = new Image("E.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		E.setGraphic(v);
		
		Button F = new Button("F");
		pic = new Image("F.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		F.setGraphic(v);
		
		Button G = new Button("G");
		pic = new Image("G.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		G.setGraphic(v);
		
		Button H = new Button("H");
		pic = new Image("H.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		H.setGraphic(v);
		
		Button I = new Button("I");
		pic = new Image("I.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		I.setGraphic(v);
		
		Button J = new Button("J");
		pic = new Image("J.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		J.setGraphic(v);
		
		Button H = new Button("H");
		pic = new Image("H.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		H.setGraphic(v);
		
		Button K = new Button("K");
		pic = new Image("K.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		K.setGraphic(v);
		
		Button L = new Button("L");
		pic = new Image("L.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		L.setGraphic(v);
		
		Button M = new Button("M");
		pic = new Image("M.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		M.setGraphic(v);
		
		Button N = new Button("N");
		pic = new Image("N.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		N.setGraphic(v);
		
		Button O = new Button("O");
		pic = new Image("O.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		O.setGraphic(v);
		
		Button P = new Button("P");
		pic = new Image("P.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		P.setGraphic(v);
		
		Button Q = new Button("Q");
		pic = new Image("Q.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		Q.setGraphic(v);
		
		Button R = new Button("R");
		pic = new Image("R.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		R.setGraphic(v);
		
		Button S = new Button("S");
		pic = new Image("S.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		S.setGraphic(v);
		
		Button T = new Button("T");
		pic = new Image("T.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		T.setGraphic(v);
		
		Button U = new Button("U");
		pic = new Image("U.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		U.setGraphic(v);
		
		Button V = new Button("V");
		pic = new Image("V.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		V.setGraphic(v);
		
		Button W = new Button("W");
		pic = new Image("W.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		W.setGraphic(v);
		
		Button X = new Button("X");
		pic = new Image("X.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		X.setGraphic(v);
		
		Button Y = new Button("Y");
		pic = new Image("Y.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		Y.setGraphic(v);
		
		Button Z = new Button("Z");
		pic = new Image("Z.png");
		v = new ImageView(pic);
		v.setFitHeight(75);
		v.setFitWidth(75);
		v.setPreserveRatio(true);
		Z.setGraphic(v);
		
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