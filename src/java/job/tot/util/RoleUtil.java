package job.tot.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import job.tot.dao.DaoFactory;

public class RoleUtil {

    private static List<Map<String, String>> rlist = new ArrayList<Map<String, String>>();

    public List<Map<String, String>> getRoleList() throws SQLException {
	if (rlist == null || rlist.size() == 0) {
	    rlist = DaoFactory.getRolesDao().getList();
	}
	return rlist;
    }
}
