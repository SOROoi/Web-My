package 网络编程;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/*
 * 	UDP协议	发送端
 * 
 * 		发送'886'则退出通信
 */

public class UdpClient {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		UdpSend();
	}
	
	private static void UdpSend() throws IOException {
		// TODO Auto-generated method stub
		DatagramSocket so = new DatagramSocket();
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你要发送的信息(发送'886'退出通信)：");
		while(true) {
			String data = sc.nextLine();
			DatagramPacket p = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName("127.0.0.1"), 10086);
			so.send(p);
			if("886".equals(data)) {
				break;
			}
		}	
		so.close();
	}
}
