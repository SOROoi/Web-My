package wxt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import wxt.service.ServiceDao;

@Controller
@RequestMapping("/con")
public class MyController {

	@Autowired
	ServiceDao service;
	
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request,MultipartFile upload) {
		service.upload(request,upload);
		return "success";
	}
}
