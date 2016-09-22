/*
 * CountThread.java
 *
 * Created on 2006��10��18��, ����4:57
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.count;
import job.tot.db.DBUtils;
import java.sql.*;
/**
 *
 * @author Administrator
 */
public class CountControl{   
    private static long lastExecuteTime=0;
    private static long executeSep=15000;
    /** Creates a new instance of CountThread */
    public CountControl() {
    }
    public synchronized void executeUpdate(){
        Connection conn=null;
        PreparedStatement ps=null;
        try{
            conn = DBUtils.getConnection();            
            conn.setAutoCommit(false);
            ps=conn.prepareStatement("update t_task set Hits=Hits+1 where id=?");
            for(int i=0;i<CountCache.list.size();i++){
                CountBean cb=(CountBean)CountCache.list.getFirst();
                CountCache.list.removeFirst();                            
                ps.setInt(1, cb.getCountId());
                ps.addBatch();                
            }
            int [] counts = ps.executeBatch();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            try{
                if(ps!=null) {
                    ps.clearParameters();
                    ps.close();
                    ps=null;
                }
            }catch(SQLException e){}
            DBUtils.closeConnection(conn);
        }
    }
    public long getLast(){
        return lastExecuteTime;
    }
    public  void run(){
        long now = System.currentTimeMillis();
        if ((now - lastExecuteTime) > executeSep) {
            //System.out.print("lastExecuteTime:"+lastExecuteTime);
            //System.out.print(" now:"+now+"\n");
           // System.out.print(" sep="+(now - lastExecuteTime)+"\n");
            lastExecuteTime=now;
            executeUpdate();
            
        }
        else{
            //System.out.print("wait for "+(now - lastExecuteTime)+" seconds:"+"\n");
        }
    }
}
