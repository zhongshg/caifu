package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
 * @since 2016年9月21日 15:51:19 用户数据库操作DAO类
 */
public class UsersDao extends AbstractDao {
    private static Log log = LogFactory.getLog(UsersDao.class);

    /**
     * @param col
     *            where判断条件字段
     * @param id
     *            col对应的值
     * @param fieldArr
     *            要查询的字段
     */
    public DataField getByCol(String col, Object id, String fieldArr) {
	// String fieldArr = "id,svid,svname,sid,signid,wxsid";
	return getFirstData("select " + fieldArr + " from users where " + col + "='" + id + "'", fieldArr);
    }

    public DataField getByNameAndPwd(String ucode, String pwd, String fieldArr) {
	return getFirstData("select " + fieldArr + " from users where name='" + ucode + "' and pwd='" + pwd + "'", fieldArr);
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

    public boolean add(String uname, String password, String parentid, String cardid, String bankcard, String tel, String code) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	if (!validate(code)) {
	    return false;
	}
	String sql = "insert into users(name,pwd,code,cardid,bankcard,phone,parentid) values(?,?,?,?,?,?,?)";
	try {
	    conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, uname);
	    ps.setString(2, password);
	    ps.setString(3, code);
	    ps.setString(4, cardid);
	    ps.setString(5, bankcard);
	    ps.setString(6, tel);
	    ps.setString(7, parentid);
	    if (ps.executeUpdate() != 1) {
		returnValue = false;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	} finally {
	    DBUtils.closePrepareStatement(ps);
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
	    e.printStackTrace();
	    return false;
	} finally {
	    DBUtils.closePrepareStatement(ps);
	    DBUtils.closeConnection(conn);
	}
	return returnValue;
    }

    public boolean del(String id) throws ObjectNotFoundException, DatabaseException {
	return exe("delete from users where id='" + id);
    }

    public List<Map<String,String>> get_Limit(int currentpage, int pagesize) {
	String str = "id,name,pwd,code,age,viplvl,cardid,bankcard,phone,roleid,parentid,indate,dr,ts";
	StringBuilder sql = new StringBuilder("select " + str + " from users order by id ");
	int offset = currentpage==0?currentpage:(currentpage - 1) * pagesize + 1;
	sql.append(" limit " + offset + "," + pagesize);
	List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
	Connection conn = DbConn.getConn();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    System.out.println(sql);
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		Map<String, String> users = new HashMap<String, String>();
		users.put("id", rs.getString("id"));
		users.put("name", rs.getString("name"));
		System.out.println(rs.getString("id"));
		System.out.println(rs.getString("name"));
		userList.add(users);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DbConn.getAllClose(conn, ptst, rs);
	}
	return userList;
    }

    public int getTotalCount() {
	return getDataCount("select count(1) from users where 1=1");
    }

    /**
     * 校验用户会员编号是否存在
     * 
     * @param code
     *            新会员编号
     */
    public boolean validate(String code) {
	DataField df = getFirstData("select code from users where code=" + code, "code");
	if (df == null || df.getString("code") == null) {
	    return true;
	}
	return false;
    }
}
