<%@page import="java.util.Random"%>
<%@page import="java.util.Iterator"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="job.tot.util.DateUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList list=null;
    DataField df=null;
    boolean bl=false;
     Random random=new Random();
    String sRand=""; 
         for(int j=0;j<4;j++){  
           String rand=String.valueOf(random.nextInt(10));
           sRand+=rand;
         }  
      String time_pid="0";  
        if(session.getAttribute("session_pid")!=null){
            time_pid=session.getAttribute("session_pid").toString();
        }
        else{
          String session_pid=System.currentTimeMillis()+sRand;
          session.setAttribute("session_pid", session_pid);	
          time_pid=session.getAttribute("session_pid").toString() ;     
       }
    if ("do".equals(RequestUtil.getString(request, "act"))) {
         bl=false;
         df=null;
        int bq_id = RequestUtil.getInt(request, "bq_id");   
        int bq_con_id = RequestUtil.getInt(request, "bq_con_id");
      
        df=DaoFactory.getBiaoQianConDao().getbq_con_id(bq_id, time_pid);
        if(df!=null){
            bl=DaoFactory.getBiaoQianConDao().modbq_con_id(bq_id, bq_con_id,time_pid);
        }else{
       bl =DaoFactory.getBiaoQianConDao().add(0, bq_id, bq_con_id, DateUtil.getCurrentGMTTimestamp(), time_pid);
        }
      if(bl){
     session.setAttribute("session_bq_con_id", "1");
     out.print("<table id=\"tb\" width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#A3B2CC\" >");
     out.print("<tr> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">标签名称</div>");
     out.print("</td> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">标签内容</div>");
     out.print("</td> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">操作</div>");
     out.print("</td> </tr> ");
     list=(ArrayList) DaoFactory.getBiaoQianConDao().getList_pro_lei_leibie(0, 0, 0, time_pid,"Bq_id asc");
     for(Iterator iter=list.iterator();iter.hasNext(); ){
     df=(DataField) iter.next();
      out.print("<tr> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\">"+df.getString("PTitle")+"</div>");
     out.print("</td> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\">"+df.getString("Title")+"</div>");
     out.print("</td> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\"><a href=\"javascript:biaoqianmod("+df.getInt("Bq_id")+");\" style=\"padding: 0 5px;color: #1488cb;\" >[修改]</a> <a href=\"javascript:biaoqiandel("+df.getInt("id")+");\" style=\"padding: 0 5px;color: #1488cb;\" >[删除]</a></div>");
     out.print("</td> </tr> ");
     
     }
     out.print("</table>");
      }else{
      out.print("1");
      }
      
    }
     if ("del".equals(RequestUtil.getString(request,"act"))) {
        bl=false;
        list=null;
        int con_id=RequestUtil.getInt(request, "con_id");
        
        bl=DaoFactory.getBiaoQianConDao().del(con_id);
       
       
      if(bl){  
     list=(ArrayList) DaoFactory.getBiaoQianConDao().getList_pro_lei_leibie(0, 0, 0, time_pid,"Bq_id asc");
      if(list!=null && list.size()>0){
     out.print("<table id=\"tb\" width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#A3B2CC\" >");
     out.print("<tr> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">标签名称</div>");
     out.print("</td> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">标签内容</div>");
     out.print("</td> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">操作</div>");
     out.print("</td> </tr> ");
     for(Iterator iter=list.iterator();iter.hasNext(); ){
     df=(DataField) iter.next();
      out.print("<tr> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\">"+df.getString("PTitle")+"</div>");
     out.print("</td> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\">"+df.getString("Title")+"</div>");
     out.print("</td> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\"><a href=\"javascript:biaoqianmod("+df.getInt("Bq_id")+");\" style=\"padding: 0 5px;color: #1488cb;\" >[修改]</a> <a href=\"javascript:biaoqiandel("+df.getInt("id")+");\" style=\"padding: 0 5px;color: #1488cb;\" >[删除]</a></div>");
     out.print("</td> </tr> "); 
     }
     out.print("</table>");
      }else{
       out.print("2");
      }
          
          }else{
      out.print("1");
      }
      
    }
    
    
    
    if("biaoqianbq".equals(RequestUtil.getString(request, "act"))){
        df=null;
    int biaoqian=RequestUtil.getInt(request, "biaoqian");
    
        out.clear();
        df=DaoFactory.getBiaoQianConDao().get(0, 0, biaoqian, 0, time_pid);
        if(df!=null){                    
        out.print(df.getInt("Bq_con_id"));
        }else{
        out.print("1");
        }
    
    
    }
    //属性标签
    if("shuxingsx".equals(RequestUtil.getString(request, "act"))){
        df=null;
    int shuxing=RequestUtil.getInt(request, "shuxing");
    
        out.clear();
      //df=DaoFactory.getBiaoQianConDao().get(0, 0, shuxing, 0, time_pid);
      df=DaoFactory.getShuXingCzDao().get(0, 0, shuxing, 0, time_pid,null);
        if(df!=null){                    
        out.print(df.getString("Sx_con_id"));
        }else{
        out.print("0");
        }  
    }
     if ("sxdo".equals(RequestUtil.getString(request, "act"))) {
         bl=false;
         df=null;
        int sx_id = RequestUtil.getInt(request, "sx_id");   
        String sx_con_id = new String(RequestUtil.getString(request, "sx_con_id").getBytes("iso-8859-1"),"utf-8");
        df=DaoFactory.getShuXingCzDao().getsx_con_id(sx_id, time_pid);
        if(df!=null){
            //bl=DaoFactory.getBiaoQianConDao().modbq_con_id(sx_id, null,time_pid);
        }else{
      // bl =DaoFactory.getBiaoQianConDao().add(0, sx_id, sx_con_id, DateUtil.getCurrentGMTTimestamp(), time_pid);
       bl=DaoFactory.getShuXingCzDao().add(0, sx_id, sx_con_id, 1, DateUtil.getCurrentGMTTimestamp(), time_pid);
        }
      if(bl){
     session.setAttribute("session_sx_con_id", "1");
     out.print("<table id=\"tb\" width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#A3B2CC\" >");
     out.print("<tr> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">属性名称</div>");
     out.print("</td> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">属性内容</div>");
     out.print("</td> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">操作</div>");
     out.print("</td> </tr> ");
    // list=(ArrayList) DaoFactory.getBiaoQianConDao().getList_pro_lei_leibie(0, 0, 0, time_pid,"Bq_id asc");
   
     list=(ArrayList)DaoFactory.getShuXingCzDao().getList_pro_cz_shuxing(0, 0, null, time_pid,"jb asc");
     for(Iterator iter=list.iterator();iter.hasNext(); ){
     df=(DataField) iter.next();
      out.print("<tr> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\">"+df.getString("Title")+"</div>");
     out.print("</td> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\">"+df.getString("Sx_con_id")+"</div>");
     out.print("</td> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\"><a href=\"javascript:biaoqianmod("+df.getInt("Sx_id")+");\" style=\"padding: 0 5px;color: #1488cb;\" >[修改]</a> <a href=\"javascript:biaoqiandel("+df.getInt("id")+");\" style=\"padding: 0 5px;color: #1488cb;\" >[删除]</a></div>");
     out.print("</td> </tr> ");
     
     }
     out.print("</table>");
      }else{
      out.print("1");
      }
      
    }
     
     
      if ("shuxingdel".equals(RequestUtil.getString(request,"act"))) {
        bl=false;
        list=null;
        int con_id=RequestUtil.getInt(request, "con_id");
        
     
        bl=DaoFactory.getShuXingCzDao().del(con_id);
       
       
      if(bl){  
     list=(ArrayList) DaoFactory.getBiaoQianConDao().getList_pro_lei_leibie(0, 0, 0, time_pid,"Bq_id asc");
      if(list!=null && list.size()>0){
     out.print("<table id=\"tb\" width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#A3B2CC\" >");
     out.print("<tr> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">标签名称</div>");
     out.print("</td> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">标签内容</div>");
     out.print("</td> <td bgcolor=\"#F2F2F2\">");
     out.print("<div align=\"center\">操作</div>");
     out.print("</td> </tr> ");
     for(Iterator iter=list.iterator();iter.hasNext(); ){
     df=(DataField) iter.next();
      out.print("<tr> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\">"+df.getString("PTitle")+"</div>");
     out.print("</td> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\">"+df.getString("Title")+"</div>");
     out.print("</td> <td bgcolor=\"#fff\">");
     out.print("<div align=\"center\"><a href=\"javascript:biaoqianmod("+df.getInt("Bq_id")+");\" style=\"padding: 0 5px;color: #1488cb;\" >[修改]</a> <a href=\"javascript:biaoqiandel("+df.getInt("id")+");\" style=\"padding: 0 5px;color: #1488cb;\" >[删除]</a></div>");
     out.print("</td> </tr> "); 
     }
     out.print("</table>");
      }else{
       out.print("2");
      }
          
          }else{
      out.print("1");
      }
      
    }
    
    
  
%>
