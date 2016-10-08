package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.dao.DaoFactory;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.global.GlobalEnum;
import job.tot.util.DateUtil;
import job.tot.util.DbConn;
import job.tot.util.MD5;

/**
 * @author zhongshg
 * @since 2016年9月21日 15:51:19 用户数据库操作DAO类
 */
public class UsersDao extends AbstractDao {
	private Logger log = Logger.getLogger(UsersDao.class.getName());

	/**
	 * @param col
	 *            where判断条件字段
	 * @param id
	 *            col对应的值
	 * @param fieldArr
	 *            要查询的字段
	 */
	public DataField getByCol(String where, String fieldArr) {
		return getFirstData("select " + fieldArr + " from users where " + where, fieldArr);
	}

	public List<Map<String, String>> searchBywhere(String where, String fieldArr) {
		if (fieldArr == null) {
			fieldArr = "id,name,pwd,age,viplvl,cardid,bankcard,phone,roleid,parentid,dr,ts,nick,store,isvip";
		}
		StringBuffer sql = new StringBuffer("select ");
		sql.append(fieldArr);
		sql.append(" from users  ");
		if (where != null && where != "") {
			sql.append("where " + where);
		}
		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		Connection conn = DbConn.getConn();
		PreparedStatement ptst = null;
		ResultSet rs = null;
		try {
			ptst = conn.prepareStatement(sql.toString());
			rs = ptst.executeQuery();
			while (rs.next()) {
				Map<String, String> users = new HashMap<String, String>();
				users.put("id", rs.getString("id"));
				users.put("name", rs.getString("name"));
				users.put("viplvl", GlobalEnum.VIPLVL.get(rs.getString("viplvl")));
				users.put("cardid", rs.getString("cardid"));
				users.put("bankcard", rs.getString("bankcard"));
				users.put("phone", rs.getString("phone"));
				users.put("roleid", rs.getString("roleid"));
				users.put("parentid", rs.getString("parentid"));
				users.put("nick", rs.getString("parentid"));
				users.put("store", rs.getString("store"));
				users.put("isvip", rs.getString("isvip"));
				users.put("ts", rs.getString("ts").substring(0, 10));
				userList.add(users);
			}
		} catch (SQLException ex) {
			log.log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		} finally {
			DbConn.getAllClose(conn, ptst, rs);
		}
		return userList;
	}

	public DataField getByIdAndPwd(String id, String pwd, String fieldArr) {
		return getFirstData("select " + fieldArr + " from users where id=" + id + " and pwd='" + pwd + "' and isvip=1",
				fieldArr);
	}

	public DataField getById(String id, String fieldArr) {
		return getFirstData("select " + fieldArr + " from users where id=" + id + " and isvip=1", fieldArr);
	}

	public void batDel(String[] s) {
		this.bat("delete from users where id=?", s);
	}

	public DataField getMaxId() {
		String fieldArr = "max(id) counts";
		return getFirstData("select " + fieldArr + " from users", "counts");
	}

	public int getMaxIdInt() {
		return getDataCount("select max(id) from users");
	}

	public boolean add(Connection conn, Map<String, String> users, Map<String, String> orders) throws SQLException {
		// Connection conn = null;
		PreparedStatement ps = null;
		boolean returnValue = false;
		try {
			if (conn == null) {
				conn = DBUtils.getConnection();
			}
			String sql = "insert into users(name,pwd,id,cardid,bankcard,phone,parentid,nick,store,viplvl,isvip) values(?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, users.get("uname"));
			ps.setString(2, users.get("password"));
			ps.setString(3, users.get("uid"));
			ps.setString(4, users.get("cardid"));
			ps.setString(5, users.get("bankcard"));
			ps.setString(6, users.get("tel"));
			ps.setString(7, users.get("parentid"));
			ps.setString(8, users.get("nick"));
			ps.setString(9, users.get("store"));
			ps.setInt(10, 1);
			ps.setInt(11, 1);
			if (ps.executeUpdate() == 1) {
				returnValue = true;
			}
			if (!returnValue) {
				return returnValue;
			}
			// 判断上级是否为管理员，如果是管理员不能参与上下级记录
			if ("1".equals(users.get("proleid"))) {
				// 开始记录代理信息
				Map<String, String> agency = new HashMap<String, String>();
				agency.put("uid", users.get("uid"));
				agency.put("parentid", users.get("parentid"));
				returnValue = DaoFactory.getAgencyDao().add(agency, conn);
				System.out.println("flag by add2:" + returnValue);
				if (!returnValue) {
					return returnValue;
				}
			}
			// 开始新增资产财富表信息
			returnValue = DaoFactory.getAssetsDao().add(users.get("uid"), conn);
			System.out.println("flag by add3:"+returnValue);
			if (!returnValue) {
				return returnValue;
			}
			// 开始新增订单信息
			returnValue = DaoFactory.getOrdersDao().add(conn, orders);
			System.out.println("flag by add4:"+returnValue);
			if (!returnValue) {
				return returnValue;
			}
			// 开始新增资产支出信息
			Map<String, String> assets_out = new HashMap<String, String>();
			assets_out.put("uid", users.get("parentid"));
			assets_out.put("amount", orders.get("oAmountMoney"));
			assets_out.put("type", "26");// 注册支出
			assets_out.put("oid", orders.get("oNum"));
			assets_out.put("dr", "1");
			returnValue = DaoFactory.getAssetsOUTDao().add(assets_out, conn);
			if (!returnValue) {
				return returnValue;
			}
			// 开始新增用户报单佣金信息

			try {
				// 开始更新会员号库存表
				returnValue = DaoFactory.getuCodeDao().update(users.get("uid"), conn);
				return returnValue;
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			DBUtils.closeConnection(conn);
			log.log(Level.SEVERE, null, e);
			e.printStackTrace();
			return false;
		} finally {
			DBUtils.closeStatement(ps);
		}
		return returnValue;
	}

	public boolean update(Connection conn, String id, Map<String, String> users) {
		// Connection conn = null;
		PreparedStatement ps = null;
		boolean returnValue = true;
		String sql = "update users set ";
		List<String> values = new ArrayList<String>();
		for (String key : users.keySet()) {
			if (key.equals("id")) {
				continue;
			}
			sql += key + "=?,";
			values.add(users.get(key));
		}
		sql = sql.substring(0, sql.length() - 1);

		sql += "  where id=?";
		try {
			if (conn == null) {
				conn = DBUtils.getConnection();
			}
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < values.size(); i++) {
				ps.setString(i + 1, values.get(i));
				System.out.println(values);
			}
			ps.setInt(values.size() + 1, Integer.parseInt(id));
			System.out.println(Integer.parseInt(id));
			if (ps.executeUpdate() != 1) {
				returnValue = false;
			}
		} catch (SQLException e) {
			DBUtils.closeConnection(conn);
			log.log(Level.SEVERE, null, e);
			e.printStackTrace();
			return false;
		} finally {
			DBUtils.closePrepareStatement(ps);
		}
		return returnValue;
	}

	public boolean del(String id) throws ObjectNotFoundException, DatabaseException {
		return exe("delete from users where id=" + id);
	}

	public List<Map<String, String>> get_Limit(int currentpage, int pagesize, String where) {
		String str = "id,name,pwd,age,viplvl,cardid,bankcard,phone,roleid,parentid,dr,ts,nick,store,isvip";
		StringBuilder sql = new StringBuilder("select ");
		sql.append(str);
		sql.append(" from users ");
		if (where != null && where != "") {
			sql.append(" where " + where);
		}
		sql.append(" order by id ");
		int offset = currentpage == 1 ? 0 : (currentpage - 1) * pagesize;
		sql.append(" limit " + offset + "," + pagesize);
		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		Connection conn = DbConn.getConn();
		PreparedStatement ptst = null;
		ResultSet rs = null;
		try {
			ptst = conn.prepareStatement(sql.toString());
			rs = ptst.executeQuery();
			while (rs.next()) {
				Map<String, String> users = new HashMap<String, String>();
				users.put("id", rs.getString("id"));
				users.put("name", rs.getString("name"));
				users.put("viplvl", GlobalEnum.VIPLVL.get(rs.getString("viplvl")));
				users.put("cardid", rs.getString("cardid"));
				users.put("bankcard", rs.getString("bankcard"));
				users.put("phone", rs.getString("phone"));
				users.put("roleid", rs.getString("roleid"));
				users.put("parentid", rs.getString("parentid"));
				users.put("nick", rs.getString("parentid"));
				users.put("store", rs.getString("store"));
				users.put("isvip", rs.getString("isvip"));
				users.put("ts", rs.getString("ts").substring(0, 10));
				userList.add(users);
			}
		} catch (SQLException ex) {
			log.log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		} finally {
			DbConn.getAllClose(conn, ptst, rs);
		}
		return userList;
	}

	public int getTotalCount() {
		return getDataCount("select count(1) from users where isvip=1");
	}

	/**
	 * 记录会员登录时间
	 * 
	 * @throws Exception
	 */
	public void remarkLogininfo(String uid) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into logininfo(userid) values(?)";
		try {
			conn = DBUtils.getConnection();
			System.out.println("用户" + uid + "登录,SQL=" + sql);
			ps = conn.prepareStatement(sql);
			ps.setString(1, uid);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, null, e);
			e.printStackTrace();
		} finally {
			DBUtils.closePrepareStatement(ps);
			DBUtils.closeConnection(conn);
		}
	}

	/**
	 * 校验用户会员编号是否存在
	 * 
	 * @param code
	 *            新会员编号
	 * @throws SQLException
	 * @throws DatabaseException
	 * @throws ObjectNotFoundException
	 */
	public boolean validate(String id) throws SQLException, ObjectNotFoundException, DatabaseException {
		DataField udf = getFirstData("select id from users where id=" + id, "id");
		if (udf != null && udf.getString("id") != null) {
			return false;
		}
		return true;
	}

	public int validate(Connection conn, Map<String, String> users) throws SQLException {
		int code = -1;
		boolean flag = false;
		String newsql = "";
		String sql = "select id from users where ";
		try {
			if (conn == null) {
				conn = DBUtils.getConnection();
			}
			// 开始校验手机号
			newsql = sql + "phone=" + users.get("tel");
			String field = "id";
			DataField df = this.execute(newsql, field);
			if (df != null && df.getInt("id") > 0) {
				code = 4;
			} else {
				newsql = sql + "cardid=" + users.get("cardid");
				df = this.execute(newsql, field);
				if (df != null && df.getInt("id") > 0) {
					code = 3;
				} else {
					newsql = sql + "bankcard=" + users.get("bankcard");
					df = this.execute(newsql, field);
					if (df != null && df.getInt("id") > 0) {
						code = 5;
					} else {
						String oNum = DaoFactory.getOrdersDao().getNewProcode();
						// 拼接订单信息
						Map<String, String> orders = new HashMap<String, String>();
						orders.put("oDt", DateUtil.getStringDate());
						orders.put("oLastUpdateDt", DateUtil.getStringDate());
						orders.put("oNum", oNum);
						orders.put("oPrice", users.get("allPrice"));
						orders.put("oCount", users.get("allVAL"));
						orders.put("oAmountMoney", users.get("sum"));
						orders.put("ouserid", users.get("parentid"));
						orders.put("ousername", users.get("parengname"));
						orders.put("pid", users.get("allid"));
						orders.put("pName", users.get("allName"));
						flag = DaoFactory.getUserDao().add(conn, users, orders);

						System.out.println("flag by add:" + flag);
						// 更新资产信息
						if (flag && "1".equals(users.get("proleid"))) {
							Float new_assets = Float.valueOf(users.get("old_assets")) - Float.valueOf(users.get("sum"));
							Float new_balance = Float.valueOf(users.get("old_balance"))
									- Float.valueOf(users.get("sum"));
							Map<String, String> assets = new HashMap<String, String>();
							assets.put("assets", String.valueOf(new_assets));
							assets.put("balance", String.valueOf(new_balance));
							assets.put("id", users.get("parentid"));
							flag = DaoFactory.getAssetsDao().update(conn, assets);
						}
						if (flag) {
							int viplvl = Integer.parseInt(users.get("user_viplvl").toString());
							if (viplvl != 4) {// 如果还不是钻级会员
								List<DataField> dfList = DaoFactory.getAgencyDao()
										.getByCol("parentid=" + users.get("parentid"));
								int number = dfList.size();
								Map<String, String> old_users = new HashMap<String, String>();
								String user_id = users.get("parentid");
								if (number >= 5) {// 升级为钻级会员
									old_users.put("viplvl", "4");
								} else if (number >= 3) {// 升级为金级会员
									old_users.put("viplvl", "3");
								} else {// 升级为银级会员
									old_users.put("viplvl", "2");
								}
								flag = DaoFactory.getUserDao().update(conn, user_id, old_users);
								System.out.println("flag by update:" + flag);
							}
						}
						if (flag) {
							code = 0;
						} else {// 系统错误
							code = -1;
						}
					}
				}
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return code;
	}

	public DataField execute(String sqlStr, String fieldArr) throws ObjectNotFoundException, DatabaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DataField df = null;
		if (fieldArr == null) {
			fieldArr = "id,name,pwd,age,viplvl,cardid,bankcard,phone,roleid,parentid,dr,ts,nick,store,isvip";
		}
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStr);
			String[] splitStr = fieldArr.split(",");
			if (rs.next()) {
				df = new DataField();
				for (int i = 0; i < splitStr.length; i++) {
					df.setField(splitStr[i], rs.getString(i + 1), 0);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// log.error("Sql Exception Error:", e);
		} finally {
			DBUtils.closeConnection(conn);
			DBUtils.closeResultSet(rs);
			DBUtils.closeStatement(stmt);
		}
		return df;
	}
}
