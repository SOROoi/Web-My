package wxt.advice;

import wxt.utils.TransactionManager;

public class ShiwuAdvice {
	private TransactionManager txManager;
	
	
	public void setTxManager(TransactionManager txManager) {
		this.txManager = txManager;
	}

	/**
	 * 前置通知
	 */
	public void startShiwu() {
		//1.开启事务
        txManager.beginTransaction();
	}
	
	/**
	 * 后置通知
	 */
	public void commitShiwu() {
		//3.提交事务
        txManager.commit();
	}
	
	/**
	 * 异常通知
	 */
	public void exceptionShiwu() {
		//5.回滚操作
        txManager.rollback();
	}
	
	/**
	 * 最终通知
	 */
	public void finallyShiwu() {
		//6.释放连接
        txManager.release();
	}
}
