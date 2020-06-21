package wxt.dao;
/**
 * 持久层接口
 * @author asus pc
 *
 */

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.Product;

@Repository
public interface IProductDao {

	@Select("select * from product")
	List<Product> findAll() throws Exception;
	
	@Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) "
			+ "values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
	void save(Product product) throws Exception;
	
	@Select("select * from product where id = #{id}")
	Product findById(String id) throws Exception;
}
