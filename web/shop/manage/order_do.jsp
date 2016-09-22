<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="inc/common.jsp"%>
<%    if ("address".equals(RequestUtil.getString(request, "act"))) {
    String UserId=null;
    if((String)session.getAttribute("totjjob_userid")!=null ){
    UserId=(String)session.getAttribute("totjjob_userid");
    }
        String Sname = DaoFactory.getShengDao().getID(RequestUtil.getInt(request, "Sname")).getString("Sming");
        String Ssname = DaoFactory.getShengDao().getSsming(RequestUtil.getInt(request, "Ssname")).getString("Ssming");
        String Xname =DaoFactory.getShengDao().getXian(RequestUtil.getInt(request, "Xname")).getString("Xming");
        int DefaultAddress=RequestUtil.getInt(request,"DefaultAddress");
        String FullAddress=RequestUtil.getString(request,"FullAddress");
        FullAddress=Sname+Ssname+Xname+FullAddress;
        String FullName=RequestUtil.getString(request,"FullName");
        String FullPhone=RequestUtil.getString(request,"FullPhone");
        String FullTelePhone=RequestUtil.getString(request, "FullTelePhone");
        boolean flag=false;
      if(DefaultAddress==1){
            DaoFactory.getAddressDAO().modMoRen(UserId);
            }
        flag = DaoFactory.getAddressDAO().add(UserId, FullName, FullAddress, Sname,Ssname, Xname, FullPhone, FullTelePhone, null, DefaultAddress,DaoFactory.getShengDao().getXian(RequestUtil.getInt(request, "Xname")).getInt("X_id"));
         
        if (flag) {
           
                out.print("1");
            
        } else {
            out.print("0");
        }
    }
%>