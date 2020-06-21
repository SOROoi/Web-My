package wxt.aspect;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import wxt.bean.SysLog;
import wxt.service.ILogService;

/**
 * 获得日志信息，封装到日志类
 * 存入数据库
 * @author asus pc
 *
 */
@Component
@Aspect
public class LogAop {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ILogService service;

	private Date start;
	private Timestamp visitTime;	//访问时间
	private Long executionTime;	//执行时长
	private String username;		//用户名
	private String ip;				//访问者IP
	private String url;				//资源URL
	private String method;			//访问方法名
	
	private Class clazz;	//访问的Controller类名
	
	//前置通知
	@Before("execution(* wxt.controller.*.*(..))")
	public void doBefore(JoinPoint jp) {
		clazz = jp.getTarget().getClass();
		
		if(clazz != null) {		//访问的Controller不为空才记录
			String clazzName = clazz.getName();
			if(!clazzName.endsWith("LogController")) {	//不访问 LogController就记录
				start = new Date();
				visitTime = new Timestamp(start.getTime());	//访问时间
			}
		}
		
	}
	
	@After("execution(* wxt.controller.*.*(..))")
	public void doAfter(JoinPoint jp) {
		
		if(clazz != null) {		//访问的Controller不为空才记录
			String clazzName = clazz.getName();
			if(!clazzName.endsWith("LogController")) {	//不访问 LogController就记录
				
				Date end = new Date();
				executionTime = end.getTime()-start.getTime();	//执行时长
				SecurityContext con = SecurityContextHolder.getContext();
				username = ((User)con.getAuthentication().getPrincipal()).getUsername();	//用户名
				ip = request.getRemoteAddr();	//访问者IP
				url = request.getRequestURI();	//资源URL
				method = jp.getSignature().getName();	//访问方法名
				
				SysLog log = new SysLog();
				log.setUsername(username);
				log.setIp(ip);
				log.setVisitTime(visitTime);
				log.setExecutionTime(executionTime);
				log.setUrl(url);
				log.setMethod("[类名]"+clazzName+" [方法名]"+method);
				
				try {
					service.addLog(log);	//将SysLog存入数据库
				} catch (Exception e) {
					throw new RuntimeException("SysLog存储数据库失败");
				}
				
			}
		}	
		
	}
}
