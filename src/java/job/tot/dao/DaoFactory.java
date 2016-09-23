/*
 * DaoFactory.java
 *
 * Created on 2006��7��27��, ����11:59
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package job.tot.dao;

import job.tot.dao.jdbc.*;
import wap.wx.dao.PowersDAO;
import wap.wx.dao.RolesDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Administrator
 */
public class DaoFactory {

    private static CategoryDaoImplJDBC CategoryDao = new CategoryDaoImplJDBC();
    private static ProductDaoImplJDBC ProductDao = new ProductDaoImplJDBC();
    private static OrderDaoImplJDBC OrderDao = new OrderDaoImplJDBC();
    private static BasketDaoImplJDBC BasketDao = new BasketDaoImplJDBC();
    private static SlideDaoImplJDBC SlideDao = new SlideDaoImplJDBC();
    private static AddressDaoImplJDBC AddressDao = new AddressDaoImplJDBC();
    private static MysqlDaoImplJDBC MysqlDao = new MysqlDaoImplJDBC();
    private static FundsDaoImplJDBC Funds = new FundsDaoImplJDBC();
    private static ExportlogDaoImplJDBC exportlogDaoImplJDBC = new ExportlogDaoImplJDBC();
    private static PropertysDaoImplJDBC propertysDaoImplJDBC = new PropertysDaoImplJDBC();
    private static AreaDaoImplJDBC areaDaoImplJDBC = new AreaDaoImplJDBC();
    private static CourierDaoImplJDBC courierDaoImplJDBC = new CourierDaoImplJDBC();
    private static PsvDaoImplJDBC psvDaoImplJDBC = new PsvDaoImplJDBC();
    private static UsersDao userDao = null;
    private static UCodeDao uCodeDao = null;
    private static NodesDao nodesDao = null;
    private static RolesDAO rolesDao = null;
    private static PowersDAO powersDao = null;

    public static FundsDaoImplJDBC getFundsDao() {
	return Funds;
    }

    public static MysqlDaoImplJDBC getMysqlDao() {

	return MysqlDao;

    }

    public static CategoryDaoImplJDBC getCategoryDAO() {
	return CategoryDao;
    }

    public static ProductDaoImplJDBC getProductDAO() {
	return ProductDao;
    }

    public static OrderDaoImplJDBC getOrderDAO() {
	return OrderDao;
    }

    public static BasketDaoImplJDBC getBasketDAO() {
	return BasketDao;
    }

    public static SlideDaoImplJDBC getSlideDAO() {
	return SlideDao;
    }

    public static AddressDaoImplJDBC getAddressDAO() {
	return AddressDao;
    }

    public static ExportlogDaoImplJDBC getExportlogDaoImplJDBC() {
	return exportlogDaoImplJDBC;
    }

    public static PropertysDaoImplJDBC getPropertysDaoImplJDBC() {
	return propertysDaoImplJDBC;
    }

    public static AreaDaoImplJDBC getAreaDaoImplJDBC() {
	return areaDaoImplJDBC;
    }

    public static CourierDaoImplJDBC getCourierDaoImplJDBC() {
	return courierDaoImplJDBC;
    }

    public static PsvDaoImplJDBC getPsvDaoImplJDBC() {
	return psvDaoImplJDBC;
    }

    public DaoFactory() {
    }

    public static UsersDao getUserDao() {
	if (userDao == null) {
	    userDao = new UsersDao();
	}
	return userDao;
    }

    public static UCodeDao getuCodeDao() {
	if (uCodeDao == null) {
	    uCodeDao = new UCodeDao();
	}
	return uCodeDao;
    }

    public static NodesDao getNodesDao() {
	if (nodesDao == null) {
	    nodesDao = new NodesDao();
	}
	return nodesDao;
    }

    public static RolesDAO getRolesDao() {
	if (rolesDao == null) {
	    rolesDao = new RolesDAO();
	}
	return rolesDao;
    }

    public static PowersDAO getPowersDao() {
	if (powersDao == null) {
	    powersDao = new PowersDAO();
	}
        return powersDao;
    }

}
