package bean;

import java.io.Serializable;

public class Timee implements Serializable {

	private int id;
	private String name;
	private String size;
	private Role role;

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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Timee [id=" + id + ", name=" + name + ", size=" + size + ", role=" + role + "]";
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
