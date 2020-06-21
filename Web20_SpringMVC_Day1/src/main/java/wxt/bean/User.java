package wxt.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class User implements Serializable {

	private String username;
	private Integer userword;
	private Date birthday;
	private Account account;
	private List<Person> friends;
	private Map<String, Integer> mathCore;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUserword() {
		return userword;
	}

	public void setUserword(Integer userword) {
		this.userword = userword;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Person> getFriends() {
		return friends;
	}

	public void setFriends(List<Person> friends) {
		this.friends = friends;
	}

	public Map<String, Integer> getMathCore() {
		return mathCore;
	}

	public void setMathCore(Map<String, Integer> mathCore) {
		this.mathCore = mathCore;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", userword=" + userword + ", birthday=" + birthday + ", account="
				+ account + ", friends=" + friends + ", mathCore=" + mathCore + "]";
	}

}
