import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection{
	
	private int port;
	
	public Server(int port, Consumer<Serializable> callback) {
		super(callback);
		this.port = port;
	}

	protected boolean isServer() {
		return true;
	}

	protected String getIP() {
		return null;
	}

	protected int getPort() {
		return port;
	}
	
}
