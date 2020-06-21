package wxt.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import wxt.bean.Order;
import wxt.service.IOrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	IOrderService service;
	
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
		List<Order> list = service.findAll(page, size);	//分页查询所有订单
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
}
