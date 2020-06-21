package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import wxt.bean.Order;
import wxt.dao.IOrderDao;
import wxt.service.IOrderService;

/**
 * 业务层
 * 
 * @author asus pc
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderService implements IOrderService {

	@Autowired
	private IOrderDao dao;

	// 未分页查询所有
	@Override
	public List<Order> findAll() throws Exception {
		List<Order> orders = dao.findAll();
		return orders;
	}

	// 分页查询所有
	@Override
	public List<Order> findAll(int page, int size) throws Exception {
		PageHelper.startPage(page, size); // 分页查询	page查询第几页 size每页几条
		return dao.findAll();
	}

	@Override
	public Order findById(String id) throws Exception {
		Order order = dao.findById(id);
		return order;
	}

}
