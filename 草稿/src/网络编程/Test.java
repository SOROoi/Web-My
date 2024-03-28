package 网络编程;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	public static void main(String[] args) {
//		method1();
		method2();
	}

	//创建UDP接收端发送端线程
	private static void method2() {
		// TODO Auto-generated method stub
		//创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(2);

		//UDP接收端
		pool.execute(()->{
			try {
				//创建DatagramSocket对象,使用2333端口
				DatagramSocket socket = new DatagramSocket(2333);
				byte[] by = new byte[1024];	//创建一个字节数组作为接收数据的缓冲区
				DatagramPacket p = new DatagramPacket(by, by.length);
				socket.receive(p);	//阻塞接收
				String data = new String(p.getData(),"UTF-8");	//数据
				System.out.println("接收来自IP："+p.getAddress().getHostAddress()+"  端口："+p.getPort()+"  数据："+data);
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		//UDP发送端
		pool.execute(()->{
			try {
				//创建DatagramSocket对象,使用5555端口
				DatagramSocket socket = new DatagramSocket(5555);
				byte[] data = "你好啊".getBytes(StandardCharsets.UTF_8);
				DatagramPacket p = new DatagramPacket(data, data.length, InetAddress.getByName("172.16.0.2"), 2333);
				socket.send(p);	//发送数据
				System.out.println("发送成功："+data);
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		pool.shutdown();
	}

	private static void method1() {
		// TODO Auto-generated method stub
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();	//获取IP地址
			System.out.println("本机IP是："+ip);
			ip=InetAddress.getByName("www.baidu.com").getHostAddress();
			System.out.println("百度IP:"+ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

