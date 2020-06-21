package wxt.bean;

import java.io.Serializable;

/**
 * User类
 * 
 * @author asus pc
 *
 */
public class User implements Serializable {

	private int id;
	private String name;
	private double money;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "[      id：" + id + ", 用户名：" + name + ", 工资：" + money + "      ]";
	}
}
