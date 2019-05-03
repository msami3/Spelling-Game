import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {
	
	private ConnThread connthread = new ConnThread();
	private Consumer<Serializable> callback;
	
	public NetworkConnection(Consumer<Serializable> callback) {
		this.callback = callback;
		connthread.setDaemon(true);
		
	}
	
	public void startConn() throws Exception{
		connthread.start();
	}
	
	public void send(Serializable data) throws Exception{
		connthread.out.writeObject(data);
	}
	
	public void closeConn() throws Exception{
		connthread.socket.close();
	}
	
	public ConnThread getConnThread() {
		return connthread;
	}
	
	abstract protected boolean isServer();
	abstract protected String getIP();
	abstract protected int getPort();
	
	class ConnThread extends Thread{
		private String score;
		private Socket socket;
		private ObjectOutputStream out;
		
		public void run() {
			try(ServerSocket server = null;
					Socket socket = new Socket(getIP(), getPort());
					ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
				
				this.socket = socket;
				this.out = out;
				
				socket.setTcpNoDelay(true);
				
				while(true) {
					Serializable data = (Serializable) in.readObject();
					if(data.toString().intern() == "1") {
						score = "1";
						data = "";
					}
					else if(data.toString().intern() == "2") {
						data = "";
						score = "2";
					}
					else if(data.toString().intern() == "3") {
						data = "";
						score = "3";
					}
					callback.accept(data);
				}
				
			}
			catch(Exception e) {
				callback.accept("connection Closed");
				e.printStackTrace();
			}
		}
		
		public String getScore() {
			return score;
		}
	}
	
}