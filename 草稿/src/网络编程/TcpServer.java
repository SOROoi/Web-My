package 网络编程;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 		TCP协议传输数据	接收端
 * 
 * 		server.accept()	为阻塞式方法，流通后建立一个socket 连接
 * 		输入流的 read()	为阻塞式方法，另一端socket的输出流 write()后，才会流通
 */

public class TcpServer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TcpReceive();
	}

	private static void TcpReceive() throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server = new ServerSocket(12315);
		Socket socket = server.accept();	//阻塞方法，流通后建立连接，返回一个Socket对象
		
		InputStream in = socket.getInputStream();	//socket中的字节输入流	
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));	//将字节输入流包装为字符缓冲流
		String str = null;
		if((str = reader.readLine()) != null) {		//read()也是阻塞方法，另一端发送后，才会流通
			System.out.println(socket.getInetAddress().getHostAddress()+" 发来消息："+str);
		}
		socket.close();
	}

}
