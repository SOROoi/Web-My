package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.Traveller;

/**
 * 旅客表操作类
 * @author asus pc
 *
 */
@Repository
public interface ITravellerDao {

	@Select("select * from traveller where id in (select travellerId from order_traveller where orderId = #{orderId})")
	List<Traveller> findById(String orderId) throws Exception;
}
