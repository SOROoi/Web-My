package wxt.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import wxt.util.DateToString;

/**
 * 订单类
 * 
 * @author asus pc
 *
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095442909029336433L;
	private String id;						//主键uuid
	private String orderNum;				//订单编号 不为空 唯一
	private Timestamp orderTime;					//下单时间
	private String orderTimeStr;			
	private Integer orderStatus;				//订单状态(0 未支付 1 已支付)
	private String orderStatusStr;
	private Integer peopleCount;				//出行人数
	private Product product;				//产品bean
	private List<Traveller> travellers;		//旅客List
	private Member member;					//会员bean
	private Integer payType;				//支付方式(0 支付宝 1 微信 2其它)
	private String payTypeStr;
	private String orderDesc;				//订单描述
	private String userid;					//用户id 外键

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	//手动修改
	public String getOrderTimeStr() {
		if(orderTime != null) {
			orderTimeStr = DateToString.toString(orderTime, "yyyy-MM-dd HH:mm");
		}
		return orderTimeStr;
	}

	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusStr() {
		if(orderStatus == 0 ) {
			orderStatusStr = "未支付";
		}
		if(orderStatus == 1 ) {
			orderStatusStr = "已支付";
		}
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public Integer getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(Integer peopleCount) {
		if(peopleCount != null) {
			this.peopleCount = peopleCount;
		}else {
			this.peopleCount = 0;
		}
		
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Traveller> getTravellers() {
		return travellers;
	}

	public void setTravellers(List<Traveller> travellers) {
		this.travellers = travellers;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	//手动修改
	public String getPayTypeStr() {
		if(payType != null) {
			if(payType == 0) {
				payTypeStr = "支付宝";
			}
			if(payType == 1) {
				payTypeStr = "微信";
			}
			if(payType == 2) {
				payTypeStr = "其他";
			}
		}
		return payTypeStr;
	}

	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
