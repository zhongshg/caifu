package job.tot.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.util.CodeUtils;

public class StoreCodeDao extends AbstractDao {
    public String getNewCode() {
	String sql = "select scode from storecode order by rand() limit 1";
	DataField df = getFirstData(sql, "scode");
	if(df.getField("scode")==null){
	    return null;
	}
	return df.getString("scode");
    }

    public boolean del(String scode) throws ObjectNotFoundException, DatabaseException {
	return this.exe("delete from storecode where scode=" + scode);
    }

    /**
     * 生成code
     */
    public void createCode() throws SQLException {
	List<String> codeList = new ArrayList<String>();
	CodeUtils codeTool = new CodeUtils();
	for (int i = 4; i < 7; i++) {
	    codeList.addAll(codeTool.generate(i, 100));
	}
	String sqlStr = "insert into storecode(scode) values(?)";
	this.bat_list(sqlStr, codeList);
    }
    
}
