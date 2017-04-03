package hadoop;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
public static void main(String[] args) throws Exception{
	ServerSocket server=new ServerSocket(5555);
	while(true){
	Socket socket=server.accept();
	DataInputStream in=new DataInputStream(socket.getInputStream());
  String content;
//System.out.println("content:"+content);
}}
}
