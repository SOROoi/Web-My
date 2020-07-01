package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.IProvinceService;
import service.imp.ProvinceService;

/*
		案例需求：
			1. 提供index.html页面，页面中有一个省份 下拉列表
			2. 当 页面加载完成后 发送ajax请求，加载所有省份
 */

public class LoadServlet extends HttpServlet {

	private IProvinceService service = new ProvinceService();

	// 查询所有省份，返回json数据
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String proJson = service.findRedis();
		//返回json数据
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().write(proJson);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
