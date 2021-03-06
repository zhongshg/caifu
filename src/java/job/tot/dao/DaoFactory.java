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

import job.tot.dao.jdbc.AddressDaoImplJDBC;
import job.tot.dao.jdbc.AgencyDao;
import job.tot.dao.jdbc.AreaDaoImplJDBC;
import job.tot.dao.jdbc.AssetsDao;
import job.tot.dao.jdbc.AssetsINDao;
import job.tot.dao.jdbc.AssetsOUTDao;
import job.tot.dao.jdbc.BasketDaoImplJDBC;
import job.tot.dao.jdbc.CategoryDaoImplJDBC;
import job.tot.dao.jdbc.CourierDaoImplJDBC;
import job.tot.dao.jdbc.ExportlogDaoImplJDBC;
import job.tot.dao.jdbc.FundsDaoImplJDBC;
import job.tot.dao.jdbc.MysqlDaoImplJDBC;
import job.tot.dao.jdbc.OrdersDao;
import job.tot.dao.jdbc.PowersDAO;
import job.tot.dao.jdbc.ProductDao;
import job.tot.dao.jdbc.PsvDaoImplJDBC;
import job.tot.dao.jdbc.RolesDAO;
import job.tot.dao.jdbc.SlideDaoImplJDBC;
import job.tot.dao.jdbc.UCodeDao;
import job.tot.dao.jdbc.UsersDao;

/**
 * 
 * @author Administrator
 */
public class DaoFactory {

    private static CategoryDaoImplJDBC CategoryDao = new CategoryDaoImplJDBC();
    private static BasketDaoImplJDBC BasketDao = new BasketDaoImplJDBC();
    private static SlideDaoImplJDBC SlideDao = new SlideDaoImplJDBC();
    private static AddressDaoImplJDBC AddressDao = new AddressDaoImplJDBC();
    private static MysqlDaoImplJDBC MysqlDao = new MysqlDaoImplJDBC();
    private static FundsDaoImplJDBC Funds = new FundsDaoImplJDBC();
    private static ExportlogDaoImplJDBC exportlogDaoImplJDBC = new ExportlogDaoImplJDBC();
    private static AreaDaoImplJDBC areaDaoImplJDBC = new AreaDaoImplJDBC();
    private static CourierDaoImplJDBC courierDaoImplJDBC = new CourierDaoImplJDBC();
    private static PsvDaoImplJDBC psvDaoImplJDBC = new PsvDaoImplJDBC();
    private static UsersDao userDao = null;
    private static UCodeDao uCodeDao = null;
    private static RolesDAO rolesDao = null;
    private static PowersDAO powersDao = null;
    private static ProductDao productDao = null;
    private static OrdersDao ordersDao = null;
    private static AgencyDao agencyDao = null;
    private static AssetsDao assetsDao = null;
    private static AssetsINDao assetsINDao = null;
    private static AssetsOUTDao assetsOUTDao = null;

    public static void setOrdersDao(OrdersDao ordersDao) {
	DaoFactory.ordersDao = ordersDao;
    }

    public static FundsDaoImplJDBC getFundsDao() {
	return Funds;
    }

    public static MysqlDaoImplJDBC getMysqlDao() {
	return MysqlDao;
    }

    public static CategoryDaoImplJDBC getCategoryDAO() {
	return CategoryDao;
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
	userDao = new UsersDao();
	return userDao;
    }

    public static UCodeDao getuCodeDao() {
	if (uCodeDao == null) {
	    uCodeDao = new UCodeDao();
	}
	return uCodeDao;
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

    public static ProductDao getProductDao() {
	productDao = new ProductDao();
	return productDao;
    }

    public static OrdersDao getOrdersDao() {
	ordersDao = new OrdersDao();
	return ordersDao;
    }

    public static AgencyDao getAgencyDao() {
	agencyDao = new AgencyDao();
	return agencyDao;
    }

    public static AssetsDao getAssetsDao() {
	assetsDao = new AssetsDao();
	return assetsDao;
    }

    public static AssetsINDao getAssetsINDao() {
	assetsINDao = new AssetsINDao();
	return assetsINDao;
    }

    public static AssetsOUTDao getAssetsOUTDao() {
	assetsOUTDao = new AssetsOUTDao();
	return assetsOUTDao;
    }

}