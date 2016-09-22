package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;

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
	return getFirstData("select " + fieldArr + " from users where code='" + ucode + "'", fieldArr);
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

    public boolean update(int id, String svname) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	String sql = "update users set svname=? where id=?";
	try {
	    conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, svname);
	    ps.setInt(2, id);
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

    public Collection get_Limit(int id, int currentpage, int pagesize) {
	String str = "id,name,pwd,code,age,viplvl,cardid,bankcard,phone,roleid,parentid,indate,dr,ts";
	StringBuilder sql = new StringBuilder("select id,svid,svname,sid,signid,wxsid from users where id='" + id);
	return getDataList_mysqlLimit(sql.toString(), str, pagesize, (currentpage - 1) * pagesize);
    }

    public int getTotalCount(String sid, String signid, String wxsid) {
	return getDataCount("select count(*) from users where sid='" + sid + "' and signid=" + signid + " and wxsid=" + wxsid);
    }

    /**
     * 校验用户会员编号是否存在
     * @param code 新会员编号
     * */
    public boolean validate(String code) {
	DataField df = getFirstData("select code from users where code=" + code, "code");
	if (df.getString("code") == null) {
	    return true;
	}
	return false;
    }
}
