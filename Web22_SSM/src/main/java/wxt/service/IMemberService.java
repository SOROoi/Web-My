package wxt.service;

import java.util.List;

import wxt.bean.Member;

public interface IMemberService {

	List<Member> findAll() throws Exception;
	
	Member findById(String memberId) throws Exception;
}
