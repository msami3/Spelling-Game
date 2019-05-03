import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class NetworkConnection {
	
	private ConnThread connthread = new ConnThread();
	private Consumer<Serializable> callback;
	private ArrayList<ClientThread> threads;
	private boolean updateScores = false;
	private boolean gameDone = false;
	
	public NetworkConnection(Consumer<Serializable> callback) {
		this.callback = callback;
		connthread.setDaemon(true);
		threads = new ArrayList<ClientThread>();
		
	}
	
	public int getNumPlayers() {
		return threads.size();
	}
	
	public boolean getUpdateScores() {
		return updateScores;
	}
	
	public void setUpdateScores(boolean b) {
		updateScores = b;
	}
	
	public void startConn() throws Exception{
		try {
		connthread.start();
		}catch(Exception e) {
			callback.accept("Error");
		}
	}
	
	public void send(Serializable data) throws Exception {
		for(int i=0;i<threads.size();i++) {
			threads.get(i).out.writeObject(data);
		}
	}
	
	public void send(int index, Serializable data) throws Exception{
		threads.get(index).out.writeObject(data);
	}
	
	public void sendToTwo(Serializable data) throws Exception{
		for(int i=0;i<threads.size();i++) {
			threads.get(i).out.writeObject(data);
		}
	}
	
	public void closeConn() throws Exception{
		for(int i =0;i<threads.size();i++) {
			threads.get(i).socket.close();
		}
	}
	
	public ClientThread getClientThread(int index) {
		return threads.get(index);
	}
	
	public boolean checkTwoInput() {
		if(threads.size() == 2) {
			try {
			}catch(Exception e) {e.printStackTrace();}
			for(int i=0;i<threads.size();i++) {
				if(threads.get(i).getChoice() == null)
					return false;
			}
			if(checkWinnerUpdateScore())
				return true;
			else
				return false;
		}
		else {
			return false;
		}
	}
	
	public boolean checkWinnerUpdateScore() {
		String p1Choice = threads.get(0).choice;
		String p2Choice = threads.get(1).choice;
		
		if(p1Choice == "Scissors" && p2Choice == "Paper")
			threads.get(0).score++;
		
		else if(p1Choice == "Paper" && p2Choice == "Scissors")
			threads.get(1).score++;
		
		else if(p1Choice == "Paper" && p2Choice == "Rock")
			threads.get(0).score++;
		
		else if(p1Choice == "Rock" && p2Choice == "Paper")
			threads.get(1).score++;
		
		else if(p1Choice == "Rock" && p2Choice == "Lizard")
			threads.get(0).score++;
		
		else if(p1Choice == "Lizard" && p2Choice == "Rock")
			threads.get(1).score++;
				
		else if(p1Choice == "Lizard" && p2Choice == "Spock")
			threads.get(0).score++;
		
		else if(p1Choice == "Spock" && p2Choice == "Lizard")
			threads.get(1).score++;

		else if(p1Choice == "Spock" && p2Choice == "Scissors")
			threads.get(0).score++;
		
		else if(p1Choice == "Scissors" && p2Choice == "Spock")
			threads.get(1).score++;

		else if(p1Choice == "Scissors" && p2Choice == "Lizard")
			threads.get(0).score++;
		
		else if(p1Choice == "Lizard" && p2Choice == "Scissors")
			threads.get(1).score++;

		else if(p1Choice == "Lizard" && p2Choice == "Paper")
			threads.get(0).score++;
		
		else if(p1Choice == "Paper" && p2Choice == "Lizard")
			threads.get(1).score++;

		else if(p1Choice == "Paper" && p2Choice == "Spock")
			threads.get(0).score++;
		
		else if(p1Choice == "Spock" && p2Choice == "Paper")
			threads.get(1).score++;

		else if(p1Choice == "Spock" && p2Choice == "Rock")
			threads.get(0).score++;
		
		else if(p1Choice == "Rock" && p2Choice == "Spock")
			threads.get(1).score++;

		else if(p1Choice == "Rock" && p2Choice == "Scissors")
			threads.get(0).score++;
		
		else if(p1Choice == "Scissors" && p2Choice == "Rock")
			threads.get(1).score++;

		else {
			return false;
		}
		
		try{send(1, "Player 1 played " + threads.get(0).getChoice());
		send(0, "Player 2 played " + threads.get(1).getChoice());}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<threads.size();i++) {
			threads.get(i).choice = null;
		}
		updateScores = true;
		for(int i=0;i<threads.size();i++) {
			callback.accept("Player" + threads.get(i).player + " score: " + threads.get(i).score);
			if(threads.get(i).score == 3) {
				callback.accept("Player " + threads.get(i).player + " won");
			}
		}
		return true;
	}
	
	public boolean gameContinues() {
		for(int i=0;i<threads.size();i++) {
			if(threads.get(i).score == 3) {
				return false;
			}
		}
		return true;
	}
	
	
	abstract protected boolean isServer();
	abstract protected String getIP();
	abstract protected int getPort();
	
	class ConnThread extends Thread{
		
		//add a counter that would keep track of how many threads are a part of the specific server
		public void run() {
			try(ServerSocket server = new ServerSocket(getPort())){
				
				int counter = 1;
				while(true) {
					if(counter <= 4) {
						callback.accept("Number of players connected: " + threads.size());
						ClientThread t1 = new ClientThread(counter, server.accept());
						counter++;
						t1.start();
						threads.add(t1);
					}
					else {
						callback.accept("Number of players connected: " + threads.size());
						callback.accept("Start game");
						break;
					}	
				}

				
			}
			catch(Exception e) {
				e.printStackTrace();
				//callback.accept("connection Closed");
			}
		}
	}
	
	class ClientThread extends Thread{
		private String choice; 
		private int player;
		private int score;
		private String continueGame = null;
		
		private Socket socket;
		private ObjectOutputStream out;
		
		ClientThread(int p, Socket s){
			this.player = p;
			this.socket = s;
		}
		
		public void run() {
			try(ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
				
				this.out = out;	
				
				while(true) {
					if(threads.size() == 4) {
						send("Game begins");
					}
				
					if(gameContinues()) {
						if(threads.size() == 2) {
							if(threads.get(0).choice == null && threads.get(1).choice == null) {
								send(0, "Your turn"); //player 1 turn
								send(1, "Waiting on player 1");
							}
							else if(threads.get(0).choice != null && threads.get(1).choice == null) {
								send(1, "Your turn"); //player 2 turn
								send(0, "Waiting on player 2");
							}
							else if(threads.get(0) == null && threads.get(1) != null) {
								send("Problem");
								System.out.println("Error with assigning turns");
							}
						}
						
						else if(threads.size() != 2) {
							send("Waiting for more players");
						}
						
						Serializable data = (Serializable) in.readObject();
						String  temp = data.toString();
						temp = temp.intern();
						if(choice == null) {
							this.choice = temp;
							callback.accept("Player " + player + " choice: " + choice);
						}
						
						if(temp == "Quit") {
							threads.remove(player-1);
							break;
						}
						checkTwoInput();
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
				
		public Serializable getChoice() {
			return this.choice;
		}
		
		public int getScore() {
			return score;
		}
	
	}
}