package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import job.tot.dao.DaoFactory;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.util.DbConn;

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
		users.put("viplvl", rs.getString("viplvl"));
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
	return getFirstData("select " + fieldArr + " from users where id='" + id + "' and pwd='" + pwd + "' and isvip=1", fieldArr);
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

    public boolean add(String uname, String password, String parentid, String cardid, String bankcard, String tel, String id, String nick, String store) throws SQLException {
	System.out.println("开始新增用户");
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = false;
	try {
	    conn = DBUtils.getConnection();
	    conn.setAutoCommit(false);
	    
	    String sql = "insert into users(name,pwd,id,cardid,bankcard,phone,parentid,nick,store) values(?,?,?,?,?,?,?,?,?)";
	    ps = conn.prepareStatement(sql.toString());
	    ps.setString(1,uname);
	    ps.setString(2,password);
	    ps.setString(3,id);
	    ps.setString(4,cardid);
	    ps.setString(5,bankcard);
	    ps.setString(6,tel);
	    ps.setString(7,parentid);
	    ps.setString(8,nick);
	    ps.setString(9,store);
	    if (ps.executeUpdate() != 1) {
		returnValue = true;
	    }
	    System.out.println(sql);
	    
	    // 开始记录代理信息
	    System.out.println("开始记录代理信息");
	    Map<String,String> agency = new HashMap<String,String>();
	    agency.put("uid", id);
	    agency.put("parentid", parentid);
	    returnValue = DaoFactory.getAgencyDao().add(agency, conn);
	    if(!returnValue){
		return returnValue;
	    }
	    System.out.println("记录代理信息结束");
	    
	    // 开始新增资产财富表信息
	    System.out.println("开始新增资产财富表信息");
	    returnValue =  DaoFactory.getAssetsDao().add(id, conn);
	    if(!returnValue){
		return returnValue;
	    }
	    System.out.println("新增资产财富表信息结束");
	    try {
		//开始更新会员号库存表
		DaoFactory.getuCodeDao().update(id,conn);
	    } catch (ObjectNotFoundException e) {
		e.printStackTrace();
	    } catch (DatabaseException e) {
		e.printStackTrace();
	    }finally{
		DBUtils.closeStatement(ps);
		DBUtils.closeConnection(conn);
	    }
	    //提交所有操作
	    conn.commit();
	    System.out.println("新增用户结束");
	} catch (SQLException e) {
	    log.log(Level.SEVERE, null, e);
	    e.printStackTrace();
	    return returnValue;
	} finally {
	    DBUtils.closeStatement(ps);
	    DBUtils.closeConnection(conn);
	}
	return returnValue;
    }

    public boolean update(String id, Map<String, String> users) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	String sql = "update users set ";
	List<String> values = new ArrayList<String>();
	for (String key : users.keySet()) {
	    sql += key + "=?,";
	    values.add(users.get(key));
	}
	sql = sql.substring(0, sql.length() - 1);

	sql += "  where id=?";
	try {
	    conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql);
	    for (int i = 0; i < values.size(); i++) {
		ps.setString(i + 1, values.get(i));
	    }
	    ps.setInt(values.size() + 1, Integer.parseInt(id));
	    if (ps.executeUpdate() != 1) {
		returnValue = false;
	    }
	} catch (SQLException e) {
	    log.log(Level.SEVERE, null, e);
	    e.printStackTrace();
	    return false;
	} finally {
	    DBUtils.closePrepareStatement(ps);
	    DBUtils.closeConnection(conn);
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
		users.put("viplvl", rs.getString("viplvl"));
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
}
