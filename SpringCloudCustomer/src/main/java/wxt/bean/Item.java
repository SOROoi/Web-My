package wxt.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 商品类
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	private String id;			//id
	private String name;		//商品名
	private Integer price;		//价格
}
