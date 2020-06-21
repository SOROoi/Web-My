package 网络编程;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/*
 * 	UDP协议	接收端	(若抛出端口无法绑定的异常，且更改端口也无效，则是控制台中的程序未全部关闭，重复启动)
 * 	
 * 		发送'886'则退出通信
 */
public class UdpServer {

	public static void main(String[] args) throws IOException {
		// InetAddress ip = InetAddress.getByName("月色泠然");
		// System.out.println(ip); //192.168.61.1
		UdpReceive();
	}

	private static void UdpReceive() throws IOException {
		// TODO Auto-generated method stub
		DatagramSocket so = new DatagramSocket(10086);
		DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
		while(true) {
			so.receive(p); // 阻塞,运行到这一步程序不继续执行
			String data = new String(p.getData(),0,p.getLength());
			System.out.println("IP："+p.getAddress().getHostAddress() + " port:" + p.getPort() + " 发来消息:" + data);
			if("886".equals(data)) {
				break;
			}
		}
		so.close();
	}

}
