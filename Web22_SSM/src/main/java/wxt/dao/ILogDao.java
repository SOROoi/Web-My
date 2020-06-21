package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.SysLog;

@Repository
public interface ILogDao {

	@Insert("insert into sysLog(visitTime,username,ip,url,executionTime,method) values(#{visitTime},"
			+ "#{username},#{ip},#{url},#{executionTime},#{method})")
	public void addLog(SysLog log) throws Exception;
	
	@Select("select * from sysLog")
	public List<SysLog> findAll() throws Exception;
}
