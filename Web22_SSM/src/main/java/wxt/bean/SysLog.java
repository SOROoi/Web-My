package wxt.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import wxt.util.DateToString;

public class SysLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;				//主键
	
	private Timestamp visitTime;	//访问时间
	private String visitTimeStr;	
	private Long executionTime;	//执行时长
	private String username;		//用户名
	private String ip;				//访问者IP
	private String url;				//资源URL
	private String method;			//访问方法
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Timestamp getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Timestamp visitTime) {
		this.visitTime = visitTime;
	}
	public String getVisitTimeStr() {
		if(visitTime != null) {
			visitTimeStr = DateToString.toString(visitTime, "yyyy-MM-dd HH:mm:ss");
		}
		return visitTimeStr;
	}
	public void setVisitTimeStr(String visitTimeStr) {
		this.visitTimeStr = visitTimeStr;
	}
	public Long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
