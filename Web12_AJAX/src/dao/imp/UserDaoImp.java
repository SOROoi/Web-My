package dao.imp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.UserDao;
import util.jdbcutil.JdbcUtil;

public class UserDaoImp implements UserDao {

	@Override
	public boolean checkName(String name) {
		Connection con  = null;
		PreparedStatement pState = null;
		ResultSet re = null;
		try {
			JdbcUtil.setDriver();
			con= JdbcUtil.getConnection();
			String sql = "select * from user where name = ?";
			pState = con.prepareStatement(sql);
			pState.setString(1, name);
			re = pState.executeQuery();
			if (re.next()) {
				return true;
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ExceptionInInitializerError("JDBC工具初始化失败");
		} finally {
			JdbcUtil.close(re, pState, con);
		}
		return false;
	}

}
