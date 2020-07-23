package wxt.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import wxt.bean.ExceptionResult;
import wxt.exception.ExceptionMsgEnum;
import wxt.exception.ProjectException;

/**
 * 通用异常处理类: 1.对不同异常分别返回不同结果 2.返回结果用JSON封装
 * 
 * @author asus pc
 *
 */

@ControllerAdvice
public class CommonExceptionHandler {

	/**
	 * @ExceptionHandler 声明异常.Class，发生括号内异常 调用此方法
	 * @param 产生的异常对象
	 * @return 异常处理结果
	 */
	@ExceptionHandler(ProjectException.class)
	public ResponseEntity<ExceptionResult> handleException(ProjectException ex) {
		ExceptionMsgEnum em = ex.getMsg(); // 获得ProjectException中的异常信息

		ExceptionResult result = new ExceptionResult(em);	//封装异常结果
		return ResponseEntity.status(em.getStatus()).body(result);
	}
}
