package 网络编程;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * 		TCP传输数据		发送端
 * 
 * 		输出流的 write()方法后，最好再加一个flush()，以确保将所有缓冲区中的数据写入流中
 */

public class TcpClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		TcpSend();
	}

	private static void TcpSend() throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 12315);	//与服务器端建立连接
		
		OutputStream out = socket.getOutputStream();	//获得客户端的输出流——向服务器端传输
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		writer.write("你好呀");
		writer.flush(); 	//添加此代码，确保所有缓冲区中的数据写入流中
		socket.close();
	}

}
