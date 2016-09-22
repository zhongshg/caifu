/*
 * CategoryDaoImplJDBC.java
 *
 * Created on 2006锟斤拷9锟斤拷16锟斤拷, 锟斤拷锟斤拷4:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package job.tot.dao.jdbc;

import job.tot.dao.AbstractDao;
import job.tot.db.DBUtils;
import job.tot.exception.ObjectNotFoundException;
import job.tot.exception.DatabaseException;
import java.sql.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import job.tot.bean.DataField;

/**
 *
 * @author Administrator
 */
public class CategoryDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(CategoryDaoImplJDBC.class);

    /**
     * Creates a new instance of CategoryDaoImplJDBC
     */
    public CategoryDaoImplJDBC() {
    }

    public int getLastId() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int returnValue = 0;
        String sql = null;
        if (DBUtils.getDatabaseType() == DBUtils.DATABASE_SQLSERVER) {
            sql = "select top 1 id from t_category order by id desc";
        } else if (DBUtils.getDatabaseType() == DBUtils.DATABASE_MYSQL) {
            sql = "select id from t_category order by id desc limit 0,1";
        }
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt(1) + 1;
            } else {
                returnValue = 1;
            }
        } catch (SQLException e) {
            log.error("get last id", e);
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public DataField getCategory(int id) {
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,SortNumber,IsOpen,Photo";
        return getFirstData("select " + fieldArr + " from t_category where id=" + id, fieldArr);
    }

    public boolean addCategory(String SuserId, String catecode, String title, String demons, int parentid, String Photo) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        int lastid = getLastId();
        String sql = "insert into t_category(id,SuserId,CateCode,Title,Demons,ParentId,SortNumber,Photo) values(?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, lastid);
            ps.setString(2, SuserId);
            ps.setString(3, catecode);
            ps.setString(4, title);
            ps.setString(5, demons);
            ps.setInt(6, parentid);
            ps.setInt(7, lastid);
            ps.setString(8, Photo);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("add Category error", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }
    /*
     * mod category
     */

    public boolean modCategory(int id, String catecode, String title, String demons, int parentid, int sortnumber, int isopen, String Photo) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_category set CateCode=?,Title=?,Demons=?,ParentId=?,SortNumber=?,IsOpen=?,Photo=? where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, catecode);
            ps.setString(2, title);
            ps.setString(3, demons);
            ps.setInt(4, parentid);
            ps.setInt(5, sortnumber);
            ps.setInt(6, isopen);
            ps.setString(7, Photo);
            ps.setInt(8, id);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("mod Category error", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public boolean removeCategory(int fromid, int toid) throws ObjectNotFoundException, DatabaseException {
        return exe("update t_topic set CategoryId=" + toid + " where CategoryId=" + fromid);
    }

    public boolean delCategory(int id) throws ObjectNotFoundException, DatabaseException {
        return exe("delete from t_category where id=" + id);
    }

    public Collection getCategoryByParentId(int parentid, String SuserId) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,SortNumber,Photo";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_category where IsOpen=1 and ParentId=");
        sql.append(parentid);
        if (SuserId != null && !SuserId.equals("null") && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        sql.append(" order by SortNumber asc");
        return getData(sql.toString(), fieldArr);
    }
    /* new function for manage categor on controlset */

    public Collection getCategoryByParentId(int parentid, int isopen, String SuserId) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Suserid,CateCode,Title,Demons,ParentId,SortNumber,Photo";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_category where ParentId=");
        sql.append(parentid);
        if (isopen >= 0) {
            sql.append(" and IsOpen=" + isopen);
        }
        if (SuserId != null && !SuserId.equals("null") && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        sql.append(" order by SortNumber asc");
        return getData(sql.toString(), fieldArr);
    }
    //带个数的分类一级搜索

    public Collection getCategoryByParentIdNum(int parentid, int isopen, String SuserId, int CurrentPage, int PageSize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Suserid,CateCode,Title,Demons,ParentId,SortNumber,Photo";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_category where ParentId=");
        sql.append(parentid);
        if (isopen >= 0) {
            sql.append(" and IsOpen=" + isopen);
        }
        if (SuserId != null && !SuserId.equals("null") && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        sql.append(" order by SortNumber asc");
        if (CurrentPage >= 0 && PageSize > 0) {
            sql.append(" limit ");
            sql.append((CurrentPage - 1) * PageSize);
            sql.append(",");
            sql.append(PageSize);
        }
        return getData(sql.toString(), fieldArr);
    }

    /*
     * get categorys
     */
    public Collection getCategorys() {
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,SortNumber,Photo";
        String sql = "select " + fieldArr + " from t_category";
        return getData(sql, fieldArr);
    }
    /*
     * get categorys by parent id
     */

    public Collection getCategorys(int parid) {
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,TopicNum,SortNumber,MinMoney,Photo";
        String sql = "select " + fieldArr + " from t_category where ParentId=" + parid + " order by SortNumber asc";
        return getData(sql, fieldArr);
    }

    public Collection getCategorys(int parid, String wxsid) {
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,IsOpen,SortNumber,Photo";
        String sql = "select " + fieldArr + " from t_category where ParentId=" + parid + " and SuserId='" + wxsid + "' order by SortNumber asc";
        return getData(sql, fieldArr);
    }

    public String getCategoryName(int id) {
        DataField df = getFirstData("select CateCode,Title from t_category where id=" + id, "Title");
        String returnValue = null;
        if (df != null) {
            returnValue = df.getFieldValue("Title");
        } else {
            returnValue = "";
        }
        return returnValue;
    }

    public int getParentId(int categoryid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int returnValue = 0;
        String sql = null;
        sql = "select ParentId from t_category where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryid);
            rs = ps.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("get Parentid", e);
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public String getPosition(int categoryid, String separator, String furl) {
        String returnValue = "";
        DataField df = getCategory(categoryid);
        if (df != null) {
            StringBuffer sb = new StringBuffer(512);
            sb.append(separator);
            sb.append("<a href=\"" + furl + "?id=");
            sb.append(categoryid);
            sb.append("\">");
            sb.append(df.getFieldValue("Title"));
            sb.append("</a>\n");
            int parentid = Integer.parseInt(df.getFieldValue("ParentId"));
            returnValue = sb.toString();
            if (parentid != 0) {
                returnValue = getPosition(parentid, separator, furl) + returnValue;
            }
        }
        return returnValue;
    }

    public int getRootId(int categoryid) {
        int returnValue = 0;
        int parentid = getParentId(categoryid);
        if (parentid != 0) {
            returnValue = getRootId(parentid);
        } else {
            returnValue = categoryid;
        }
        return returnValue;
    }
    // get select Tree view

    public String getSelectTree(String Str, int categoryid, int parentid, String SuserId) {
        String ImageStr = Str + "&nbsp;";
        String returnStr = "";
        String ifSele = "";
        ArrayList categoryList = (ArrayList) getCategoryByParentId(parentid, SuserId);
        for (Iterator iter = categoryList.iterator(); iter.hasNext();) {
            DataField df = (DataField) iter.next();
            if (categoryid == Integer.parseInt(df.getFieldValue("id"))) {
                ifSele = " selected";
            } else {
                ifSele = "";
            }
            returnStr = returnStr + "<option value=\"" + df.getFieldValue("id") + "\"" + ifSele + ">" + ImageStr + df.getFieldValue("Title") + "</option>" + "\n";
            returnStr = returnStr + getSelectTree(ImageStr + "└", categoryid, Integer.parseInt(df.getFieldValue("id")), SuserId);
        }
        return returnStr;
    }

    public int getTotalCount() {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_category");
        return (this.getDataCount(sql.toString()));
    }

    public Collection getList(int id, String Title, String Demons, int ParentId, int IsOpen, int SortNumber, String order, int currentpage, int pagesize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,IsOpen,SortNumber";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_category where 1=1");
        if (id > 0) {
            sql.append(" and id=" + id);
        }
        if (ParentId >= 0) {
            sql.append(" and ParentId=" + ParentId);
        }
        if (IsOpen >= 0) {
            sql.append(" and IsOpen=" + IsOpen);
        }
        sql.append(" order by ").append(order);
        if (currentpage > 0 && pagesize > 0) {
            sql.append(" limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    public Collection getListByParentid(String SuserId, int ParentId) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,IsOpen,SortNumber";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_category where SuserId='" + SuserId + "'");

        if (ParentId >= 0) {
            sql.append(" and ParentId=" + ParentId);
        }
        return this.getData(sql.toString(), fieldArr);
    }
    
    public Collection getListByParentid(int ParentId) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,IsOpen,SortNumber";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_category where 1=1");

        if (ParentId >= 0) {
            sql.append(" and ParentId=" + ParentId);
        }
        return this.getData(sql.toString(), fieldArr);
    }
    //对类型进行计算

    public int getTotalNum(int id, String Title, String Demons, int ParentId, int IsOpen, int SortNumber) {
        int num = 0;
        int count = 0;
        num = getTotalCount(id, Title, Demons, ParentId, IsOpen, SortNumber);
        count = num;
        num = num / 3;
        if (count % 3 != 0) {
            num = num + 1;
        }

        return num;

    }

    public int getTotalCount(int id, String Title, String Demons, int ParentId, int IsOpen, int SortNumber) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_category where 1=1");
        sql.append(" and ParentId=" + id);
        if (IsOpen >= 0) {
            sql.append(" and IsOpen=" + IsOpen);
        }

        return (this.getDataCount(sql.toString()));
    }
    //商家店铺首页对自己店铺的分类搜索

    public Collection getListshop(int id, String SuserId, int ParentId, int IsOpen, int SortNumber, String order, int currentpage, int pagesize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,SuserId,CateCode,Title,Demons,ParentId,IsOpen,SortNumber";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_category where 1=1");
        if (id > 0) {
            sql.append(" and id=" + id);
        }
        if (ParentId >= 0) {
            sql.append(" and ParentId=" + ParentId);
        }
        if (IsOpen >= 0) {
            sql.append(" and IsOpen=" + IsOpen);
        }
        if (SuserId != null && !SuserId.equals("") && !SuserId.equals("null")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        sql.append(" order by ").append(order);
        if (currentpage > 0 && pagesize > 0) {
            sql.append(" limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }
        return this.getData(sql.toString(), fieldArr);
    }
}
