package project5;

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

