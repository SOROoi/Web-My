package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import wxt.bean.Product;
import wxt.dao.IProductDao;
import wxt.service.IProductService;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)	//只读型事务配置
public class ProductService implements IProductService {
	
	@Autowired
	private IProductDao dao;

	//查询所有产品(分页)
	@Override
	public List<Product> findAll(int page, int size) throws Exception {
		PageHelper.startPage(page, size);
		return dao.findAll();
	}
	
	//查询所有产品
	@Override
	public List<Product> findAll() throws Exception {
		return dao.findAll();
	}

	//添加产品
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)  //读写型事务配置
	@Override
	public void save(Product product) throws Exception {
		// TODO Auto-generated method stub
		dao.save(product);
	}

	//查询单一产品
	@Override
	public Product findById(String id) throws Exception {
		// TODO Auto-generated method stub
		
		return dao.findById(id);
	}

}
