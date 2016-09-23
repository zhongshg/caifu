package job.tot.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.util.CodeUtils;

public class UCodeDao extends AbstractDao {
    public String getNewCode() {
	String sql = "select ucode from ucode order by rand() limit 1";
	DataField df = getFirstData(sql, "ucode");
	if(df.getField("ucode")==null){
	    return null;
	}
	return df.getString("ucode");
    }

    public boolean del(String ucode) throws ObjectNotFoundException, DatabaseException {
	return this.exe("delete from ucode where ucode=" + ucode);
    }

    /**
     * 生成code
     */
    public void createCode() throws SQLException {
	List<String> codeList = new ArrayList<String>();
	CodeUtils codeTool = new CodeUtils();
	for (int i = 5; i < 9; i++) {
	    codeList.addAll(codeTool.generate(i, 100));
	}
	String sqlStr = "insert into ucode(ucode) values(?)";
	this.bat_list(sqlStr, codeList);
    }
    
}
