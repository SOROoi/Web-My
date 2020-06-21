package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.Member;
import wxt.bean.Order;
import wxt.bean.Product;

@Repository
public interface IOrderDao {

	@Select("select * from orders")
	@Results({
		@Result(id=true,property="id",column="id"),
		@Result(property = "orderNum",column = "orderNum"),
        @Result(property = "orderTime",column = "orderTime"),
        @Result(property = "orderStatus",column = "orderStatus"),
        @Result(property = "peopleCount",column = "peopleCount"),
        @Result(property = "peopleCount",column = "peopleCount"),
        @Result(property = "payType",column = "payType"),
        @Result(property = "orderDesc",column = "orderDesc"),
        @Result(property = "product",column = "productId",javaType = Product.class,one = @One(select = "wxt.dao.IProductDao.findById"))
	})
	List<Order> findAll() throws Exception;

	@Select("select * from orders where id = #{id}")
	@Results({
		@Result(id=true,property="id",column="id"),
		@Result(property = "orderNum",column = "orderNum"),
        @Result(property = "orderTime",column = "orderTime"),
        @Result(property = "orderStatus",column = "orderStatus"),
        @Result(property = "peopleCount",column = "peopleCount"),
        @Result(property = "peopleCount",column = "peopleCount"),
        @Result(property = "payType",column = "payType"),
        @Result(property = "orderDesc",column = "orderDesc"),
        @Result(property = "product",column = "productId",javaType = Product.class,one = @One(select = "wxt.dao.IProductDao.findById")),
        @Result(property = "member",column = "memberId",javaType = Member.class,one = @One(select= "wxt.dao.IMemberDao.findById")),
        @Result(property = "travellers",column = "id",javaType = List.class,many = @Many(select="wxt.dao.ITravellerDao.findById"))
	})
	Order findById(String id) throws Exception;
}
