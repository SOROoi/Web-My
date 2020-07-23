package wxt.exception;

public class ProjectException extends RuntimeException {

	private ExceptionMsgEnum msg;

	public ProjectException(ExceptionMsgEnum msg) {
		super();
		this.msg = msg;
	}

	public ExceptionMsgEnum getMsg() {
		return msg;
	}

}
