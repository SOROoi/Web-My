package check;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.imp.UserDaoImp;

public class CheckServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// request.setCharacterEncoding("utf-8");
		String name = request.getParameter("user");
		// name = new String(name.getBytes("iso-8859-1"), "UTF-8");	//get请求也不需要写该代码了
		System.out.println(name);
		UserDaoImp dao = new UserDaoImp();
		if (dao.checkName(name)) {
			response.getWriter().write("0");
		} else {
			response.getWriter().write("1");
		}
		
	}

}