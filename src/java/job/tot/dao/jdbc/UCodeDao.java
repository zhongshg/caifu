package job.tot.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.dao.DaoFactory;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.util.CodeUtils;

public class UCodeDao extends AbstractDao {
    public String getNewCode() throws SQLException, ObjectNotFoundException, DatabaseException {
	String sql = "select ucode from ucode order by rand() limit 1";
	DataField df = getFirstData(sql, "ucode");
	//如果号码已经被取完，重新生成新号码再取
	if(df==null || df.getField("ucode")==null){
	    createCode();
	    getNewCode();
	}
	String code =  df.getString("ucode");
	//校验号码是否已经被注册
	boolean flag = DaoFactory.getUserDao().validate(code);
	if(flag){
	    return df.getString("ucode");
	}else{//如果已经号码已经被注册  就删除重新获取
	    del(code);
	    getNewCode();
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
	//i=5 i<9 表示生成 5-8位的数字
	for (int i = 5; i < 9; i++) {
	    codeList.addAll(codeTool.generate(i, 100));
	}
	String sqlStr = "insert into ucode(ucode) values(?)";
	this.bat_list(sqlStr, codeList);
    }
    
}
