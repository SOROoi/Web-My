package wxt.service;

import java.util.List;

import wxt.bean.SysLog;

public interface ILogService {

	public void addLog(SysLog log) throws Exception;
	
	public List<SysLog> findAll(int pageNum,int pageSize) throws Exception;
}
