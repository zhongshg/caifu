package job.tot.test;

import java.sql.SQLException;

import job.tot.dao.DaoFactory;
import job.tot.util.CodeUtils;

public class Test {

    public static void main(String[] args) throws SQLException {
	createCodeTest();
    }

    private void generateTest() {
	CodeUtils cu = new CodeUtils();
	cu.generate_list(6, 20);
    }

    private static void createCodeTest() throws SQLException {
	DaoFactory.getuCodeDao().createCode();
    }
}
