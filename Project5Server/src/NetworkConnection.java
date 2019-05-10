import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;

public abstract class NetworkConnection {
	
	private ConnThread connthread = new ConnThread();
	private Consumer<Serializable> callback;
	private ArrayList<ClientThread> threads;
	private String scramWord; 
	private HashMap<String, ArrayList<String>> playWords;
	private ArrayList<String> unscramArray;
	
	
	public NetworkConnection(Consumer<Serializable> callback) {
		this.callback = callback;
		connthread.setDaemon(true);
		threads = new ArrayList<ClientThread>();
		playWords = new HashMap<String, ArrayList<String>>();
		
	}
	
	public int getNumPlayers() {
		return threads.size();
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
	
	public void closeConn() throws Exception{
		for(int i =0;i<threads.size();i++) {
			threads.get(i).socket.close();
		}
	}

	public void sortFile() {
		
		File file = new File("dictionary.txt");
		ArrayList<String> words = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				words.add(line);
			}
			reader.close();
		}
		catch(Exception e) {
			e.printStackTrace(); 
		}
		
		String origWord;
		String scramWord;
		
		for(int i=0;i<words.size();i++) {
			origWord = words.get(i);
			scramWord = sortString(origWord);
			ArrayList<String> list = playWords.get(scramWord);
			if(list == null) {
				list = new ArrayList<String>();
				list.add(origWord);
				playWords.put(scramWord, list);
			}
			else {
				list.add(origWord);
				playWords.put(scramWord, list);
			}
		}
	}
	
	public String sortString(String s) {
		char temp[] = s.toCharArray();
		Arrays.sort(temp);
		return new String(temp);
	}
	
	public void pickWord() {
		/*Random generator = new Random();
		Object[] values = (String[]) playWords.values().toArray();
		
		Object randomValue = values[generator.nextInt(values.length)];
		
		System.out.println(randomValue);*/
		
	    Object[] crunchyKeys = playWords.keySet().toArray();
	    Object key = crunchyKeys[new Random().nextInt(crunchyKeys.length)];
	    scramWord = (String) key;
	    unscramArray = playWords.get(scramWord);

	}
	
	public boolean gameContinues() {
		if(threads.size() == 4) {
			for(int i=0;i<threads.size();i++) {
				if(threads.get(i).played == false)
					return true;
			}
			return false;
		}
		else
			return false;
	}
	
	abstract protected boolean isServer();
	abstract protected String getIP();
	abstract protected int getPort();
	
	class ConnThread extends Thread{
		
		//add a counter that would keep track of how many threads are a part of the specific server
		public void run() {
			try(ServerSocket server = new ServerSocket(getPort())){
				
				sortFile();
				pickWord();
				System.out.println(unscramArray);
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
						send("Game begins");
						break;
					}	
				}

				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	class ClientThread extends Thread{
		private String choice; 
		private int player;
		private int score;
		private Boolean played;
		
		private Socket socket;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		
		ClientThread(int p, Socket s){
			this.player = p;
			this.socket = s;
			this.played = false;
		}
		
		public void run() {
			try(ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
				
				this.out = out;	
				this.in = in;
				
				while(true) {
					if(threads.size() == 4){
						send("Game begins");
						
						if(threads.get(0).played == false) 
							send(0, "Your turn");
						else if(threads.get(1).played == false)
							send(1, "Your turn");
						else if(threads.get(2).played == false)
							send(2, "Your turn");
						else if(threads.get(3).played == false)
							send(3, "Your turn");
					}
					
					else if(threads.size() != 4) {
						send("Waiting for more players");
					}
							
					Serializable data = (Serializable) in.readObject();
					
					if(data.toString().intern() == "Done guessing") {
						threads.get(player-1).played = true;
					}
					
					boolean found = false;
					for(int i=0;i<unscramArray.size();i++) {
						if(data.toString().intern() == unscramArray.get(i))
							found = true;
					}
					
					if(found)
						threads.get(player-1).score++;
					else
						send(player-1, "word not found in dictionary");
					
					send(player-1, "Score: " + threads.get(player-1).score);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public synchronized void gamePlay() {
			try{
				send(player-1, scramWord);
				send(player-1, "Your turn");
					while(true) {
						Serializable data = (Serializable) in.readObject();
						if(data.toString().intern() == "Done guessing") {
							threads.get(player-1).played = true;
							break;
						}
						boolean found = false;
						for(int i=0;i<unscramArray.size();i++) {
							if(data.toString().intern() == unscramArray.get(i))
								found = true;
						}
						if(found)
							score++;
						else
							send(player-1, "word not found in dictionary");
						
						send(player-1, "Score: " + score);
						callback.accept(data);
					}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public int getScore() {
			return score;
		}
	
	}
}
