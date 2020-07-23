package wxt.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import wxt.bean.Item;
import wxt.exception.ExceptionMsgEnum;
import wxt.exception.ProjectException;

/*
 		此Controller用来测试：生产环境下的通用异常处理。采用RESTful风格请求
 		
 	1.假设做新增商品，需要接收下列参数：
 		price: 价格
 		name: 名称
 	
 	2.对参数进行校验：
 		价格不能为空。
 
 */

@RestController
public class TestErrorController {

	@GetMapping("/Commodit/{name}/{price}")
	public ResponseEntity<Item> get(@PathVariable(name = "price", required = true) Integer price,
			@PathVariable(name = "name", required = true) String name) {
		if (price == 1) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Item());	//直接处理
//			throw new RuntimeException("请求有误");		//通用异常处理
			throw new ProjectException(ExceptionMsgEnum.Error);	//通用异常处理优化
		}
		Item i = new Item(Math.random() * 1000 + "", name, price);

		return ResponseEntity.status(HttpStatus.CREATED).body(i); // 返回响应实体，包含状态码、响应体
	}

}
