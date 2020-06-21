package wxt.service;

public class Service implements ServiceIn {

	@Override
	public void all() {
		// TODO Auto-generated method stub
		System.out.println("查找所有");
	}

	@Override
	public Integer find() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void add(Integer num) {
		// TODO Auto-generated method stub
		System.out.println("添加"+num);
	}

}
