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
         int pid=RequestUtil.getInt(request, "pid");
        int bq_id = RequestUtil.getInt(request, "bq_id");   
        int bq_con_id = RequestUtil.getInt(request, "bq_con_id");
      
        df=DaoFactory.getBiaoQianConDao().modgetbq_con_id(bq_id,pid);
        if(df!=null){
            bl=DaoFactory.getBiaoQianConDao().modmodbq_con_id(bq_id, bq_con_id,pid);
        }else{
       bl =DaoFactory.getBiaoQianConDao().add(pid, bq_id, bq_con_id, DateUtil.getCurrentGMTTimestamp(), "0");
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
     list=(ArrayList) DaoFactory.getBiaoQianConDao().getList_pro_lei_leibie(pid, 0, 0, null,"Bq_id asc");
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
        int pid=RequestUtil.getInt(request, "pid");
        int con_id=RequestUtil.getInt(request, "con_id");
        
        bl=DaoFactory.getBiaoQianConDao().del(con_id);
       
       
      if(bl){  
     list=(ArrayList) DaoFactory.getBiaoQianConDao().getList_pro_lei_leibie(pid, 0, 0, null,"Bq_id asc");
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
    int pid=RequestUtil.getInt(request,"pid");
    
        out.clear();
        df=DaoFactory.getBiaoQianConDao().get(0, pid, biaoqian, 0, null);
        if(df!=null){                    
        out.print(df.getInt("Bq_con_id"));
        }else{
        out.print("1");
        }
    
    
    }
  
%>
