package 防止重复提交表单form;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoFormServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		/*
		 * String username=request.getParameter("username"); try { Thread.sleep(1000*3);
		 * } catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } System.out.println("鍚戞暟鎹簱鍐欑敤鎴峰悕");
		 */
		String r_token = request.getParameter("token");
		HttpSession session = request.getSession(false);
		if (r_token != null && session != null && r_token.equalsIgnoreCase((String) session.getAttribute("token"))) {
			request.getSession().removeAttribute("token");
			System.out.println("鍚戞暟鎹簱鍐欑敤鎴峰悕");
		} else {
			System.out.println("閲嶅鎻愪氦");
		}
	}
}
