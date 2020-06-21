package bean;

import java.io.Serializable;
import java.util.Date;

/*
	1.一个类只有实现了Serializable接口，它的对象才是可序列化的
	2.什么情况下需要序列化
		a）当你想把的内存中的对象写入到硬盘的时候；
		b）当你想用套接字在网络上传送对象的时候；
		c）当你想通过RMI传输对象的时候；
 */
public class User implements Serializable {

	private Integer id;
	private String username;
	private Date birthday;
	private String sex;
	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", birthday=" + birthday + ", sex=" + sex + ", address="
				+ address + "]";
	}
}
