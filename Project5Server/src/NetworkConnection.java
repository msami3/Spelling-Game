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
import java.util.function.Consumer;

public abstract class NetworkConnection {
	
	private ConnThread connthread = new ConnThread();
	private Consumer<Serializable> callback;
	private ArrayList<ClientThread> threads;
	private boolean updateScores = false;
	private boolean gameDone = false;
	private ArrayList<String> words;
	private HashMap<String, ArrayList<String>> playWords;
	
	
	public NetworkConnection(Consumer<Serializable> callback) {
		this.callback = callback;
		connthread.setDaemon(true);
		threads = new ArrayList<ClientThread>();
		playWords = new HashMap<String, ArrayList<String>>();
		
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
	
	public void openFile() {
		
		File file = new File("dictionary.txt");
		words = new ArrayList<String>();
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
		
	}
	
	public void sortFile() {
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
					if(threads.size() == 1) {
						//read in file
						openFile();
						sortFile();
						System.out.println(playWords);
						send("Game begins");
					}
				
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
				
		
		public int getScore() {
			return score;
		}
	
	}
}
