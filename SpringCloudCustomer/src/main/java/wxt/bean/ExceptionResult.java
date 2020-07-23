package wxt.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wxt.exception.ExceptionMsgEnum;

/**
 * 异常结果Bean 用于封装异常信息，返回给前端,可以转为JSON
 * 
 * status 状态码 message 异常信息 time 异常时间
 * 
 * @author asus pc
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResult {

	private String message;
	private Timestamp time;

	public ExceptionResult(ExceptionMsgEnum em) {	//通过ExceptionMsgEnum构建对象
		this.message = em.getMsg();
		this.time = new Timestamp(System.currentTimeMillis());
	}
}
