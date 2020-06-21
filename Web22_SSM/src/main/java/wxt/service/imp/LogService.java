package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import wxt.bean.SysLog;
import wxt.dao.ILogDao;
import wxt.service.ILogService;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class LogService implements ILogService {	

	@Autowired
	private ILogDao logDao;
		
	//SysLog存储到数据库
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void addLog(SysLog log) throws Exception {
		// TODO Auto-generated method stub
		logDao.addLog(log);
	}

	//封装 数据库所有 syslog表
	@Override
	public List<SysLog> findAll(int pageNum,int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<SysLog> list = logDao.findAll();
		return list;
		
	}
	


}
