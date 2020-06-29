package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wxt.bean.Member;
import wxt.dao.IMemberDao;
import wxt.service.IMemberService;

@Service
public class MemberService implements IMemberService {

	@Autowired
	IMemberDao dao;
	
	//查询所有member
	@Override
	public List<Member> findAll() throws Exception {
		return dao.findAll();
	}

	//查询单一member
	@Override
	public Member findById(String memberId) throws Exception {
		return dao.findById(memberId);
	}

}
