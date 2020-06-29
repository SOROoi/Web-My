package wxt.service;

import java.util.List;

import wxt.bean.Order;

/**
 * 业务层接口
 * @author asus pc
 *
 */
public interface IOrderService {

	/**
	 * 未分页
	 * 查找所有订单
	 * @return
	 * @throws Exception
	 */
	List<Order> findAll(String username) throws Exception;
	
	/**
	 * 分页
	 * 查询所有订单
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	List<Order> findAll(int page,int size,String username) throws Exception;
	
	/**
	 * 根据ID查询 订单详情(Order)
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Order findById(String id) throws Exception;
	
	
	/*
	 * 添加订单
	 */
	void addOrder(Order order) throws Exception;
	
	/*
	 * 删除订单
	 */
	void deleteOrder(String orderId) throws Exception;
}
