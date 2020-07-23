package wxt.exception;

/**
 * 异常信息 枚举类
 * @author asus pc
 *
 */

public enum ExceptionMsgEnum {

	Fail("请求失败",404), Error("请求参数有误",400); // 枚举类首行必须为枚举项。

	private String msg;
	private int status;

	private ExceptionMsgEnum() {
	}

	private ExceptionMsgEnum(String msg, int status) {
		this.msg = msg;
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public int getStatus() {
		return status;
	}

}
