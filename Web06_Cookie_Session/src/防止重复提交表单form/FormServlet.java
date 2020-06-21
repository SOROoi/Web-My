package ��ֹ�ظ��ύ����form;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

public class FormServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 产生随机数（表单�?)
		TokenProcessor tp = TokenProcessor.getInstance();
		String token = tp.generateToken();
		request.getSession().setAttribute("token", token);
		request.getRequestDispatcher("/form.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}

class TokenProcessor {// 令牌
	/*
	 * 1。构�? 方法私有 2。自己创建一�? 3。对外暴露一个方法，允许获取上面创建的对�?
	 * 
	 */
	private TokenProcessor() {
	}

	private static final TokenProcessor instance = new TokenProcessor();

	public static TokenProcessor getInstance() {
		return instance;

	}

	public String generateToken() {
		String token = System.currentTimeMillis() + new Random().nextInt() + "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] md5 = md.digest(token.getBytes());
			// base64编码
			BASE64Encoder encoder = new BASE64Encoder();// 文档没有正式发布
			return encoder.encode(md5);
			// return new String(md5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		// return null;
	}

}
