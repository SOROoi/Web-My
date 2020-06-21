package wxt.bean;

import java.io.Serializable;

public class Account implements Serializable {
	private String aname;
	private String aword;

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getAword() {
		return aword;
	}

	public void setAword(String aword) {
		this.aword = aword;
	}

	@Override
	public String toString() {
		return "Account [aname=" + aname + ", aword=" + aword + "]";
	}
}
