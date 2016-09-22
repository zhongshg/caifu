/*
 * MailBean.java
 *
 * Created on 2007��7��11��, ����4:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.bean;

/**
 *
 * @author tot
 */
public class MailBean {
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String message;
    /** Creates a new instance of MailBean */
    public MailBean() {
    }   
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getCc() {
        return cc;
    }
    public void setCc(String cc) {
        this.cc = cc;
    }
    public String getBcc() {
        return bcc;
    }
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
