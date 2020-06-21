package wxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import wxt.bean.SysLog;
import wxt.service.ILogService;

@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired
	private ILogService service;
	
	@RequestMapping("/findAll")
	public ModelAndView findAll(
			@RequestParam(name= "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(name= "size", required = false, defaultValue = "5")int pageSize) throws Exception {
		ModelAndView mv = new ModelAndView();

		List<SysLog> list = service.findAll(pageNum, pageSize);
		PageInfo<SysLog> pageInfo = new PageInfo<SysLog>(list);	//查询结果存入 分页对象
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("syslog-list");
		return mv;
	}
}
