package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Course;
import util.IHander;
import util.JDBCUtil;
import util.JdbcException;

public class CourseDAO {
	JDBCUtil jdbcUtil = new JDBCUtil();

	public boolean exist(Course course) throws JdbcException {
		boolean flag = false;
		ArrayList<Course> result=null;
		if (course.getId() == 0) {// 添加课程
			String sql = "select ccode from course where number=? or cname=? or ename=?";
			result=jdbcUtil.executeQuery(rs -> {
				ArrayList<Course> courses = new ArrayList<Course>();
				try {
					while (rs.next()) {
						Course c = new Course();
						c.setCcode(rs.getString("ccode"));
						courses.add(c);
					}
				} catch (SQLException e) {
					throw new JdbcException(JdbcException.ExceptionCategory.ResultSetFail);
				}
				return courses;
			}, sql, course.getCcode(), course.getCname(), course.getEname());
		} else {// 修改课程信息
			String sql = "select ccode from course where id!=? and (number=? or cname=? or ename=?)";
			result=jdbcUtil.executeQuery(rs -> {
				ArrayList<Course> courses = new ArrayList<Course>();
				try {
					while (rs.next()) {
						Course c = new Course();
						c.setCcode(rs.getString("ccode"));
						courses.add(c);
					}
				} catch (SQLException e) {
					throw new JdbcException(JdbcException.ExceptionCategory.ResultSetFail);
				}
				return courses;
			}, sql, course.getId(),course.getCcode(), course.getCname(), course.getEname());
		}
		if(result.size()>0) {
			flag=true;
		}
		// try {
		// Connection conn = DriverManager.getConnection(URL, "root", "0123456789");
		// String sql = "select number from course where number=? or cname=? or
		// ename=?";
		// PreparedStatement pt = conn.prepareStatement(sql);
		// pt.setString(1, course.getNumber());
		// pt.setString(2, course.getCname());
		// pt.setString(3, course.getEname());
		// ResultSet rs = pt.executeQuery();
		// if (rs.next()) {
		// flag = true;
		// }
		// pt.close();
		// conn.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		return flag;
	}

	public void save(Course course) throws JdbcException {
		String sql = "insert into course(ccode,cname,ename,score,chour,lhour,tchour,tlhour) values(?,?,?,?,?,?,?,?)";
		jdbcUtil.executeUpdate(sql, course.getCcode(), course.getCname(), course.getEname(), course.getScore(),
				course.getChour(), course.getLhour(), course.getTchour(), course.getTlhour());
	}

	public void update(Course course) throws JdbcException {
		String sql = "update course set ccode=?,cname=?,ename=?,score=?,chour=?,lhour=?,tchour=?,tlhour=? where id=?";
		jdbcUtil.executeUpdate(sql, course.getCcode(), course.getCname(), course.getEname(), course.getScore(),
				course.getChour(), course.getLhour(), course.getTchour(), course.getTlhour(), course.getId());
	}

	public void delete(Course course) throws JdbcException {
		String sql = "delete from course where id=?";
		jdbcUtil.executeUpdate(sql, course.getId());
	}

	public ArrayList<Course> findByCName(String CName) {
		String sql="select * from course where cname like ?";
		return jdbcUtil.executeQuery(rs->{
			ArrayList<Course> courses = new ArrayList<Course>();
			try {
				if (rs.next()) {
					Course course = new Course();
					course.setId(rs.getInt("id"));
					course.setCcode(rs.getString("ccode"));
					course.setCname(rs.getString("cname"));
					course.setEname(rs.getString("ename"));
					course.setScore(rs.getInt("score"));
					course.setChour(rs.getInt("chour"));
					course.setLhour(rs.getInt("lhour"));
					course.setTchour(rs.getInt("tchour"));
					course.setTlhour(rs.getInt("tlhour"));
					courses.add(course);
				}
			} catch (SQLException e) {
				throw new JdbcException(JdbcException.ExceptionCategory.ResultSetFail);
			}
			return courses;
		}, sql, "%"+CName+"%");

	}

	public Course findById(int id) throws JdbcException {
		Course course = null;
		String sql = "select * from course where id=?";
		ArrayList<Course> courses = jdbcUtil.executeQuery(new IHander<Course>() {
			@Override
			public ArrayList<Course> wrap(ResultSet rs) throws JdbcException {
				ArrayList<Course> courses = new ArrayList<Course>();
				try {
					if (rs.next()) {
						Course course = new Course();
						course.setId(rs.getInt("id"));
						course.setCcode(rs.getString("ccode"));
						course.setCname(rs.getString("cname"));
						course.setEname(rs.getString("ename"));
						course.setScore(rs.getInt("score"));
						course.setChour(rs.getInt("chour"));
						course.setLhour(rs.getInt("lhour"));
						course.setTchour(rs.getInt("tchour"));
						course.setTlhour(rs.getInt("tlhour"));
						courses.add(course);
					}
				} catch (SQLException e) {
					throw new JdbcException(JdbcException.ExceptionCategory.ResultSetFail);
				}
				return courses;
			}
		}, sql, id);
		if (!courses.isEmpty()) {
			course = courses.get(0);
		}
		return course;
	}

	public ArrayList<Course> findAll() throws JdbcException {
		String sql = "select * from course";
		return jdbcUtil.executeQuery(new CourseHandler(), sql);
	}

	class CourseHandler implements IHander<Course> {

		@Override
		public ArrayList<Course> wrap(ResultSet rs) throws JdbcException {
			ArrayList<Course> courses = new ArrayList<Course>();
			try {
				while (rs.next()) {
					Course course = new Course();
					course.setId(rs.getInt("id"));
					course.setCcode(rs.getString("ccode"));
					course.setCname(rs.getString("cname"));
					course.setEname(rs.getString("ename"));
					course.setScore(rs.getInt("score"));
					course.setChour(rs.getInt("chour"));
					course.setLhour(rs.getInt("lhour"));
					course.setTchour(rs.getInt("tchour"));
					course.setTlhour(rs.getInt("tlhour"));
					courses.add(course);
				}
			} catch (SQLException e) {
				throw new JdbcException(JdbcException.ExceptionCategory.ResultSetFail);
			}
			return courses;
		}

	}
}
