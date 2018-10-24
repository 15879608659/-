package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.ClassInfo;
import util.JDBCUtil;
import util.JdbcException;

public class ClassDAO {
	JDBCUtil jdbcUtil = new JDBCUtil();

	// 如果该方法执行时抛出异常，表示未成功写入数据表，否则成功写入数据表
	public void save(ClassInfo classInfo) throws JdbcException {
		if (!exist(classInfo)) {
			String sql = "insert into class(name,enroll_year) values(?,?)";
			jdbcUtil.executeUpdate(sql, classInfo.getName(), classInfo.getEnroll_year());
		}
	}

	public void update(ClassInfo classInfo) throws JdbcException {
		String sql = "update class set name=?,enroll_year=? where id=?";
		jdbcUtil.executeUpdate(sql, classInfo.getName(), classInfo.getEnroll_year(), classInfo.getId());
	}

	public void remove(int id) throws JdbcException {
		String sql = "delete from class where id=?";
		jdbcUtil.executeUpdate(sql, id);
	}

	public void findAll() throws JdbcException {
	}

	public ArrayList<ClassInfo> findAll(int pageSize,int page) throws JdbcException {
		String sql = "select * from class limit ?,?";
		return jdbcUtil.executeQuery(rs -> {
			ArrayList<ClassInfo> classinfos = new ArrayList<ClassInfo>();
			try {
				while (rs.next()) {
					ClassInfo classInfo = new ClassInfo();
					classInfo.setId(rs.getInt("id"));
					classInfo.setName(rs.getString("name"));
					classInfo.setEnroll_year(rs.getInt("enroll_year"));
					classinfos.add(classInfo);
				}
			} catch (SQLException e) {
				throw new JdbcException(JdbcException.ExceptionCategory.ResultSetFail);
			}
			return classinfos;
		}, sql,(page - 1) * pageSize,pageSize);

	}

	public boolean exist(ClassInfo classInfo) throws JdbcException {
		return false;
	}

}
