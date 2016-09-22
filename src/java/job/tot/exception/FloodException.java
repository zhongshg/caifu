/*
 * FloodException.java
 *
 * Created on 2006��7��25��, ����4:15
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
public class FloodException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>FloodException</code> without detail message.
     */
    public FloodException() {
    }
    
    
    /**
     * Constructs an instance of <code>FloodException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public FloodException(String msg) {
        super(msg);
    }
}
