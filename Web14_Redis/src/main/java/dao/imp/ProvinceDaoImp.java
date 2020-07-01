package dao.imp;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import bean.Province;
import dao.ProvinceDao;
import util.JDBCUtils;

public class ProvinceDaoImp implements ProvinceDao {

	private JdbcTemplate jTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public List<Province> findAll() {
		String sql = "select * from province";
		List<Province> list = jTemplate.query(sql, new BeanPropertyRowMapper<Province>(Province.class));
		return list;
	}

}
