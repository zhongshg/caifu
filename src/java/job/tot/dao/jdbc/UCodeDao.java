package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.dao.DaoFactory;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.util.CodeUtils;

public class UCodeDao extends AbstractDao {
    public String getNewCode() throws SQLException, ObjectNotFoundException, DatabaseException {
	String sql = "select ucode from ucode where dr = 0 order by rand() limit 1 ";
	DataField df = getFirstData(sql, "ucode");
	//如果号码已经被取完，重新生成新号码再取
	if(df==null || df.getField("ucode")==null){
	    createCode();
	    df = getFirstData(sql, "ucode");
	}
	String code =  df.getString("ucode");
	//校验号码是否已经被注册
	boolean flag = DaoFactory.getUserDao().validate(code);
	if(flag){
	    return df.getString("ucode");
	}else{//如果已经号码已经被注册  就删除重新获取
	    update(code,null);
	    code = getNewCode();
	}
	return code;
    }

    public boolean del(String ucode) throws ObjectNotFoundException, DatabaseException {
	return this.exe("delete from ucode where ucode=" + ucode);
    }

    public boolean update(String ucode,Connection conn) throws ObjectNotFoundException, DatabaseException {
	Statement stmt = null;
	boolean returnValue = false;
	try {
	    if(conn==null){
		conn = DBUtils.getConnection();
	    }
	    stmt = conn.createStatement();
	    String sqlStr="update ucode set dr=1 where ucode=" + ucode;
	    if (stmt.executeUpdate(sqlStr) != 0) {
		returnValue = true;
	    } else {
		returnValue = false;
	    }
	} catch (SQLException e) {
	    DBUtils.closeConnection(conn);
	    throw new DatabaseException("Got Exception on Call Medthod exe in tot.dao.AbstractDao");
	} finally{
	    DBUtils.closeStatement(stmt);
	}
	return returnValue;
    }   
    /**
     * 生成code
     */
    public void createCode() throws SQLException {
	Map<Integer,Integer> codeMap = new HashMap<Integer,Integer>();
	CodeUtils codeTool = new CodeUtils();
	//i=5 i<9 表示生成 5-8位的数字
	for (int i = 5; i < 9; i++) {
	    codeMap.putAll(codeTool.generate_map(i, 3000));
	   // codeList.addAll(codeTool.generate(i, 100));
	}
	String sqlStr = "insert into ucode(ucode) values(?)";
	this.bat_map(sqlStr, codeMap);
    }
    
}
