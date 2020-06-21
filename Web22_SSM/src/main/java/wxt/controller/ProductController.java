package wxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import wxt.bean.Product;
import wxt.service.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	IProductService service;

	@RequestMapping("/findAll")
	public ModelAndView findAll(
			@RequestParam(name="page",required=false,defaultValue="1")int page, 
			@RequestParam(name="size",required=false,defaultValue="5")int size) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Product> list = service.findAll(page,size);
		PageInfo<Product> pageInfo = new PageInfo<Product>(list);	//存入分页信息
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("product-list");
		System.out.println(list);
		return mv;
	}
	
	@RequestMapping("/save")
	public String save(Product product) throws Exception {
		service.save(product);
		return "redirect:findAll";
	}
}
