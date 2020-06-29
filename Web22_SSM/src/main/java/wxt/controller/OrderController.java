package wxt.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import wxt.bean.Member;
import wxt.bean.Order;
import wxt.bean.Product;
import wxt.service.IMemberService;
import wxt.service.IOrderService;
import wxt.service.IProductService;
import wxt.service.IUserService;

@Controller
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	IOrderService service;
	
	@Autowired
	IProductService productService;
	
	@Autowired
	IMemberService memberService;
	
	@Autowired
	IUserService userService;
	//未分页查询
//	@RequestMapping("/findAll")
//	public ModelAndView findAll() throws Exception {
//		ModelAndView mv = new ModelAndView();
//		List<Order> orders = service.findAll();
//		mv.addObject("ordersList", orders);
//		mv.setViewName("orders-list");
//		return mv;
//	}
	
	//分页查询
	@RequestMapping("/findAll")
	public ModelAndView findAll(
			@RequestParam(name="page",required=false,defaultValue="1") int page,
			@RequestParam(name="size",required=false,defaultValue="5") int size) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		SecurityContext context = SecurityContextHolder.getContext();
		User user = (User)(context.getAuthentication().getPrincipal());
		String username = user.getUsername();		//获得用户名
		
		List<Order> list = service.findAll(page, size,username);	//分页查询所有订单
		PageInfo<Order> pageInfo = new PageInfo<Order>(list);	//将分页数据封装到 PageInfo中
		mv.addObject("pageInfo",pageInfo);
		mv.setViewName("orders-page-list");
		return mv;
	}
	
	@RequestMapping("/findById")
	public ModelAndView findById(String id) throws Exception {
		ModelAndView mv = new ModelAndView(); 
		Order order= service.findById(id);
		mv.addObject("orders", order);
		mv.setViewName("orders-show");
		
		return mv;
	}
	
	//查询所有产品和所有会员
	@RequestMapping("/findProductMember")
	public ModelAndView findProjectMember() throws Exception {
		ModelAndView mv = new ModelAndView();
		
		List<Product> productList = productService.findAll();
		List<Member> memberList = memberService.findAll();
		mv.addObject("productList", productList);
		mv.addObject("memberList", memberList);
		mv.setViewName("order-add");
		return mv;
	}
	
	//添加订单
	@RequestMapping("/save")
	public String saveOrder(Order order,String productId,String memberId) throws Exception {
		long time = System.currentTimeMillis();
		Timestamp stamp = new Timestamp(time);	//获得order时间
		Product product = productService.findById(productId);	//获得product对象
		Member member = memberService.findById(memberId);
		
		SecurityContext context = SecurityContextHolder.getContext();
		User user = (User)(context.getAuthentication().getPrincipal());
		String username = user.getUsername();		//获得用户名
		String userid = userService.findId(username);
				
		order.setOrderTime(stamp);
		order.setProduct(product);
		order.setMember(member);
		order.setUserid(userid);

		service.addOrder(order);
		return "forward:findAll";
	}
	
	//删除订单
	@RequestMapping("/delete")
	public String deleteOrder(String orderId) throws Exception{
		service.deleteOrder(orderId);
		return "forward:findAll";
	}
}
