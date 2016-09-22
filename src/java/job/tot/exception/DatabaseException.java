/*
 * DatabaseException.java
 *
 * Created on 2006��7��25��, ����11:23
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.exception;

/**
 *
 * @author Administrator
 */
public class DatabaseException extends Exception {

   public DatabaseException(String msg) {
      super(msg);
   }
}