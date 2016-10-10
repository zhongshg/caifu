package job.tot.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import job.tot.dao.DaoFactory;

public class AssetsINTest {
	public static void main(String[] args) throws SQLException {
		Map<String,String> assets_out = new HashMap<String,String>();
		String uid="8487688";
		assets_out.put("amount", "3000");
		//boolean flag = DaoFactory.getAssetsINDao().agencyMoney(assets_out, uid);
		//System.out.println(flag);;
	}
}
