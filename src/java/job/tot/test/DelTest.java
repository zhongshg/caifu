package job.tot.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import job.tot.db.DBUtils;

public class DelTest {
    public static void main(String[] args) throws SQLException {
	Connection conn = null;
	String sql1 = "delete from users where id<>88888";
	String sql2 = "delete from  orders";
	String sql3 = "delete from logininfo";
	String sql4 = "delete from assets_in";
	String sql5 = "delete from assets_out";
	String sql6 = "delete from assets";
	String sql7 = "delete from agency";
	try {
	    conn = DBUtils.getConnection();
	    conn.setAutoCommit(false);
	    Statement stmt = conn.createStatement();
	    stmt.addBatch(sql7);
	    stmt.addBatch(sql6);
	    stmt.addBatch(sql5);
	    stmt.addBatch(sql4);
	    stmt.addBatch(sql3);
	    stmt.addBatch(sql2);
	    stmt.addBatch(sql1);
	    int[] num = stmt.executeBatch();
	    System.out.println(num.length);
	    if (num.length == 7) {
		conn.commit();
		System.out.println("系统清空完毕");
	    } else {
		conn.rollback();
		System.out.println("系统清空失败");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    DBUtils.closeAllConnections();
	}
    }
}
