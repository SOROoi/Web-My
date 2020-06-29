package wxt.service;
/**
 * 业务层接口
 * @author asus pc
 *
 */

import java.util.List;

import wxt.bean.Product;

public interface IProductService {

	List<Product> findAll(int page, int size) throws Exception;
	
	List<Product> findAll() throws Exception;
	
	Product findById(String id) throws Exception;
	
	void save(Product product) throws Exception;
}
