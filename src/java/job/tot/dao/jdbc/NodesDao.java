package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import job.tot.dao.AbstractDao;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;

/**
 * 获取节点dao类
 * 
 * @author zhongshg
 * @since 2016年9月23日 14:45:54
 */
public class NodesDao extends AbstractDao {

    /**
     * 新增节点
     */
    public boolean addNode(String name, String url, String parentid, int orders, String remark) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	String sql = "insert into nodes(name,url,parentid,orders,remark) values(?,?,?,?,?,?)";
	try {
	    conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, name);
	    ps.setString(2, url);
	    ps.setString(3, parentid);
	    ps.setInt(4, orders);
	    ps.setString(5, remark);
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

    /**
     * 删除节点
     */
    public boolean del(int id) throws ObjectNotFoundException, DatabaseException {
	return exe("delete from nodes where id=" + id);
    }

    /**
     * 修改节点
     */
    public boolean mod(int id, String name, String url, String parentid, int orders, String remark) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	StringBuffer sql = new StringBuffer("update nodes set ");
	sql.append(" orders=? ");
	Map<Integer, String> map = new HashMap<Integer, String>();
	if (name != null) {
	    map.put(2, name);
	    sql.append(",name=?");
	}
	if (url != null) {
	    map.put(3, url);
	    sql.append(",url=?");
	}
	if (parentid != null) {
	    map.put(4, url);
	    sql.append(",parentid=?");
	}
	if (remark != null) {
	    map.put(5, url);
	    sql.append(",remark=?");
	}
	sql.append(" where id=?");
	// String sql = "update nodes set name=?,url=?,parentid=?,orders=?,remark=? where id=?";
	try {
	    conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql.toString());
	    ps.setInt(1, orders);
	    for (int i = 1; i < map.size(); i++) {
		ps.setString(i, map.get(i));
	    }
	    ps.setInt(map.size()+2, id);
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
    /**
     * 查询所有节点
     */
    public Collection getList(int id) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "t.id,t.name,t.url,t.parentid,t.orders,t.remark";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from nodes t left join rolespowers t1 on t1.nid=t.id ");
        sql.append(" left join roles t2 on t2.id = t1.rid left join users t3 on t3.roleid = t2.id ");
        sql.append(" where t3.id = "+ id);
        return this.getData(sql.toString(), fieldArr);
    }
}
