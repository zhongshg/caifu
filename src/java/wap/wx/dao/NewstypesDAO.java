/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import wap.wx.util.DbConn;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
public class NewstypesDAO {

    public List<Map<String, String>> getList(String sid, int wxsid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select nt.id,nt.name,nt.img,nt.parentid,nt2.name parentname,nt.remark,nt.times,nt.sid,nt.uid from newstypes nt left join newstypes nt2 on nt2.id=nt.parentid where nt.sid=? and nt.wxsid=? order by nt.orders desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("img", rs.getString("img"));
                map.put("parentid", rs.getString("parentid"));
                map.put("parentname", rs.getString("parentname"));
                map.put("remark", rs.getString("remark"));
                map.put("times", rs.getString("times"));
                map.put("sid", rs.getString("sid"));
                map.put("uid", rs.getString("uid"));
                list.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return list;
    }

    public List<Map<String, String>> getList(String sid, String parentid, int wxsid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select nt.id,nt.name,nt.img,nt.parentid,nt2.name parentname,nt.remark,nt.times,nt.sid,nt.uid from newstypes nt left join newstypes nt2 on nt2.id=nt.parentid where nt.sid=? and nt.parentid=? and nt.wxsid=? order by nt.orders desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, Integer.parseInt(parentid));
            ptst.setInt(3, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("img", rs.getString("img"));
                map.put("parentid", rs.getString("parentid"));
                map.put("parentname", rs.getString("parentname"));
                map.put("remark", rs.getString("remark"));
                map.put("times", rs.getString("times"));
                map.put("sid", rs.getString("sid"));
                map.put("uid", rs.getString("uid"));
                list.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return list;
    }

    public List<Map<String, String>> getList(PageUtil pu, String sid, int wxsid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select nt.id,nt.name,nt.img,nt.parentid,nt2.name parentname,nt.remark,nt.times,nt.sid,nt.uid from newstypes nt left join newstypes nt2 on nt2.id=nt.parentid where nt.sid=? and nt.wxsid=? order by nt.orders desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("img", rs.getString("img"));
                map.put("parentid", rs.getString("parentid"));
                map.put("parentname", rs.getString("parentname"));
                map.put("remark", rs.getString("remark"));
                map.put("times", rs.getString("times"));
                map.put("sid", rs.getString("sid"));
                map.put("uid", rs.getString("uid"));
                list.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return list;
    }

    public int getConut(String sid, int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from newstypes where sid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public List<Map<String, String>> getList(PageUtil pu, String sid, String parentid, int wxsid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select nt.id,nt.name,nt.img,nt.parentid,nt2.name parentname,nt.remark,nt.times,nt.sid,nt.uid from newstypes nt left join newstypes nt2 on nt2.id=nt.parentid where nt.sid=? and nt.parentid=? and nt.wxsid=? order by nt.orders desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, Integer.parseInt(parentid));
            ptst.setInt(3, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("img", rs.getString("img"));
                map.put("parentid", rs.getString("parentid"));
                map.put("parentname", rs.getString("parentname"));
                map.put("remark", rs.getString("remark"));
                map.put("times", rs.getString("times"));
                map.put("sid", rs.getString("sid"));
                map.put("uid", rs.getString("uid"));
                list.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return list;
    }

    public int getConut(String sid, String parentid, int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from newstypes where sid=? and parentid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, Integer.parseInt(parentid));
            ptst.setInt(3, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public int getMaxId(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select max(id) count from newstypes where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public Map<String, String> getById(Map<String, String> map) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select nt.id,nt.name,nt.img,nt.parentid,nt2.name parentname,nt.remark,nt.times,nt.sid,nt.uid,nt.orders from newstypes nt left join newstypes nt2 on nt2.id=nt.parentid where nt.id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(map.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                map = new HashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("img", rs.getString("img"));
                map.put("parentid", rs.getString("parentid"));
                map.put("parentname", rs.getString("parentname"));
                map.put("remark", rs.getString("remark"));
                map.put("times", rs.getString("times"));
                map.put("sid", rs.getString("sid"));
                map.put("uid", rs.getString("uid"));
                map.put("orders", rs.getString("orders"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return map;
    }

    public void add(Map<String, String> map, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into newstypes(name,img,parentid,remark,times,sid,uid,orders,wxsid) values (?,?,?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, map.get("name"));
            ptst.setString(2, map.get("img"));
            ptst.setInt(3, Integer.parseInt(map.get("parentid")));
            ptst.setString(4, map.get("remark"));
            ptst.setString(5, map.get("times"));
            ptst.setInt(6, Integer.parseInt(map.get("sid")));
            ptst.setInt(7, Integer.parseInt(map.get("uid")));
            ptst.setInt(8, Integer.parseInt(map.get("orders")));
            ptst.setInt(9, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update(Map<String, String> map) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update newstypes set name=?,img=?,parentid=?,remark=?,times=?,sid=?,uid=?,orders=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, map.get("name"));
            ptst.setString(2, map.get("img"));
            ptst.setInt(3, Integer.parseInt(map.get("parentid")));
            ptst.setString(4, map.get("remark"));
            ptst.setString(5, map.get("times"));
            ptst.setInt(6, Integer.parseInt(map.get("sid")));
            ptst.setInt(7, Integer.parseInt(map.get("uid")));
            ptst.setInt(8, Integer.parseInt(map.get("orders")));
            ptst.setInt(9, Integer.parseInt(map.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void delete(Map<String, String> map) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from newstypes where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(map.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void delete(Map<String, String> map, Connection conn) {
        String sql = "delete from newstypes where id=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(map.get("id")));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(NewstypesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSelectTree(String optionStr, String targettypeid, String parentid, String sid, int wxsid) {
        String returnStr = "";
        String isSelect;
        optionStr = "".equals(optionStr) ? "" : optionStr + "-";
        List<Map<String, String>> list = new NewstypesDAO().getList(sid, parentid, wxsid);
        for (Map<String, String> map : list) {
            if (targettypeid.equals(map.get("id"))) {
                isSelect = " selected";
            } else {
                isSelect = "";
            }
            returnStr = returnStr + "<option value=\"" + map.get("id") + "\"" + isSelect + ">" + optionStr + map.get("name") + "</option>";
            returnStr = returnStr + getSelectTree(optionStr + map.get("name"), targettypeid, map.get("id"), sid, wxsid);
        }
        return returnStr;
    }

    public String getSelectTreeDir(String returnStr, String id, int wxsid) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map = new NewstypesDAO().getById(map);
        if (null != map.get("parentid")) {
            returnStr = "".equals(returnStr) ? returnStr : "-" + returnStr;
            returnStr = map.get("name") + returnStr;
            returnStr = getSelectTreeDir(returnStr, map.get("parentid"), wxsid);
        }
        return returnStr;
    }

    public Map<String, String> getSelectList(NewstypesDAO newstypesDAO, String sid, String stypeid, Map<String, String> newstypesmap, int wxsid) {
        newstypesmap.put(stypeid, stypeid);
        List<Map<String, String>> newstypesList = newstypesDAO.getList(sid, stypeid, wxsid);
        for (Map<String, String> map : newstypesList) {
            getSelectList(newstypesDAO, sid, map.get("id"), newstypesmap, wxsid);
        }
        return newstypesmap;
    }

    public void deleteList(Map<String, String> newstypes, NewstypesDAO newstypesDAO, HttpServletRequest request, Connection conn, int wxsid) {
        if (!"".equals(newstypes.get("img")) && null != newstypes.get("img")) {
            java.io.File oldFile = new java.io.File(request.getServletContext().getRealPath(newstypes.get("img")));
            oldFile.delete();
        }
        newstypesDAO.delete(newstypes, conn);
        List<Map<String, String>> list = newstypesDAO.getList(newstypes.get("sid"), newstypes.get("id"), wxsid);
        for (Map<String, String> map : list) {
            deleteList(map, newstypesDAO, request, conn, wxsid);
        }
    }
}
