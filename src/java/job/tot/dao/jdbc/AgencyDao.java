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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.util.DbConn;

/**
 * @author zhongshg
 * @since 2016年9月21日 15:51:19 资产数据库操作DAO类
 */
public class AgencyDao extends AbstractDao {
	private static Log log = LogFactory.getLog(AgencyDao.class);

	/**
	 * @param col
	 *            where判断条件字段
	 * @param id
	 *            col对应的值
	 * @param fieldArr
	 *            要查询的字段
	 */
	public List<DataField> getByCol(String where) {
		String fieldArr = "parentid,agencylvl,uid";
		return getData("select " + fieldArr + " from agency where " + where, fieldArr);
	}

	public List<Map<String, String>> searchBywhere(String where) {
		String fieldArr = "parentid,agencylvl,uid";
		StringBuffer sql = new StringBuffer("select ");
		sql.append(fieldArr);
		sql.append(" from agency  ");
		if (where != null && where != "") {
			sql.append("where " + where);
		}
		List<Map<String, String>> agencyList = new ArrayList<Map<String, String>>();
		Connection conn = DbConn.getConn();
		PreparedStatement ptst = null;
		ResultSet rs = null;
		try {
			ptst = conn.prepareStatement(sql.toString());
			rs = ptst.executeQuery();
			while (rs.next()) {
				// parentid,agencylvl,uid
				Map<String, String> agency = new HashMap<String, String>();
				agency.put("parentid", rs.getString("parentid"));
				agency.put("agencylvl", rs.getString("agencylvl"));
				agency.put("uid", rs.getString("uid"));
				agencyList.add(agency);
			}
		} catch (SQLException ex) {
			Logger.getLogger(AgencyDao.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		} finally {
			DbConn.getAllClose(conn, ptst, rs);
		}
		return agencyList;
	}

	public boolean add(Map<String, String> agency, Connection conn) throws SQLException {
		boolean returnValue = true;
		// 查询代理关系，往上找10级
		String parentid = agency.get("parentid");
		// 查询上级代理的上9级代理的parentid
		int count = 0;// 记录代理级别
		Statement ps = null;
		ResultSet rs = null;
		String[] parentids = null;
		try {
			String qsql = "select parentid from agency where uid=" + parentid
					+ " and agencylvl in (1,2,3,4,5,6,7,8,9,10) order by agencylvl asc";
			System.out.println(qsql);
			ps = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//ptst = conn.prepareStatement(qsql);
			rs = ps.executeQuery(qsql);
			rs.last();
			int size = rs.getRow();
			System.out.println(size);
			rs.beforeFirst();
			if(size==10){
				parentids = new String[10];
				while (rs.next()) {
					if(count==0){
						continue;
					}
					parentids[count++] = rs.getString("parentid");
				}
			}else{
				parentids = new String[size+1];
				while (rs.next()) {
					System.out.println(rs.getString("parentid"));
					parentids[count++] = rs.getString("parentid");
				}
			}
			parentids[parentids.length-1]=parentid;
		} catch (SQLException e) {
			DBUtils.closeConnection(conn);
			Logger.getLogger(AgencyDao.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
			returnValue = false;
			return returnValue;
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closeStatement(ps);
		}
		// 准备SQL
		// parentid,agencylvl,uid
		try {
			ps = conn.createStatement();
			for (int i = 0; i < parentids.length; i++) {
				if (parentids[i] != null) {
					String sql = "insert into agency(parentid,agencylvl,uid) values(" + parentids[i] + "," + (i + 1)
							+ "," + agency.get("uid") + ")";
					System.out.println(sql);
					ps.addBatch(sql);
				}
			}
			int[] num = ps.executeBatch();
			if (num.length == 0) {
				returnValue = false;
				return returnValue;
			}
		} catch (SQLException e) {
			DBUtils.closeConnection(conn);
			Logger.getLogger(AgencyDao.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
			returnValue = false;
			return returnValue;
		} finally {
			DBUtils.closeStatement(ps);
		}
		System.out.println(returnValue);
		return returnValue;
	}

	public boolean del(String id) throws ObjectNotFoundException, DatabaseException {
		return exe("delete from agency where id=" + id);
	}

	public List<Map<String, String>> get_Limit(int currentpage, int pagesize, String where) {
		String str = "parentid,agencylvl,uid";
		StringBuilder sql = new StringBuilder("select ");
		sql.append(str);
		sql.append(" from agency ");
		if (where != null && where != "") {
			sql.append(" where " + where);
		}
		sql.append(" order by id ");
		int offset = currentpage == 1 ? 0 : (currentpage - 1) * pagesize;
		sql.append(" limit " + offset + "," + pagesize);
		List<Map<String, String>> agencyList = new ArrayList<Map<String, String>>();
		Connection conn = DbConn.getConn();
		PreparedStatement ptst = null;
		ResultSet rs = null;
		try {
			ptst = conn.prepareStatement(sql.toString());
			rs = ptst.executeQuery();
			while (rs.next()) {
				Map<String, String> agency = new HashMap<String, String>();
				// parentid,agencylvl,uid
				agency.put("parentid", rs.getString("parentid"));
				agency.put("agencylvl", rs.getString("agencylvl"));
				agency.put("uid", rs.getString("uid"));
				agencyList.add(agency);
			}
		} catch (SQLException ex) {
			Logger.getLogger(AgencyDao.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		} finally {
			DbConn.getAllClose(conn, ptst, rs);
		}
		return agencyList;
	}
}
