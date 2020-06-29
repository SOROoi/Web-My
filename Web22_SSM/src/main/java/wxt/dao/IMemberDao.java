package wxt.dao;
/**
 * 会员表操作类
 * @author asus pc
 *
 */

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.Member;

@Repository
public interface IMemberDao {
	
	@Select("select * from member where id = #{memberId}")
	Member findById(String memberId) throws Exception;
	
	@Select("select * from member")
	List<Member> findAll() throws Exception;
}
