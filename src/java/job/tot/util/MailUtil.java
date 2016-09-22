/*
 * MailUtil.java
 *
 * Created on 2007��7��11��, ����4:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import job.tot.global.Sysconfig;
import job.tot.exception.BadInputException;
import job.tot.filter.DisableHtmlTagFilter;
import job.tot.bean.MailBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class MailUtil {

    public static final int MAX_MESSAGES_PER_TRANSPORT = 100;

    private static Log log = LogFactory.getLog(MailUtil.class);

    private MailUtil() {// prevent instantiation
    }

    //private static MailOptions mailOption = new MailOptions();

    /**
     * Get the user name part of an email. Ex: input: test@yahoo.com => output: test
     * @param email String the email
     * @return String the user name part of an email
     */
    public static String getEmailUsername(String email) {
        if (email == null) return "";
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return "";
        }
        return email.substring(0, atIndex);
    }

    /**
     * Get the domain part of an email. Ex: input: test@yahoo.com => output: yahoo.com
     * @param email String the email
     * @return String the user name part of an email
     */
    public static String getEmailDomain(String email) {
        if (email == null) return "";
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return "";
        }
        return email.substring(atIndex + 1);
    }

    /**
     * Check if an email is good and safe or not.
     * This method should be use for all email input from user
     * @param input String
     * @throws BadInputException if email is not good
     */
    public static void checkGoodEmail(String input) throws BadInputException {
        if (input == null) throw new BadInputException("Sorry, null string is not a good email.");//@todo : localize me
        int atIndex = input.indexOf('@');
        int dotIndex = input.lastIndexOf('.');
        if ((atIndex == -1) || (dotIndex == -1) || (atIndex >= dotIndex)) {
            //@todo : localize me
            throw new BadInputException("Error: '" + DisableHtmlTagFilter.filter(input) + "' is not a valid email value. Please try again.");
        }

        // now check for content of the string
        int length = input.length();
        char c = 0;

        for (int i = 0; i < length; i++) {
            c = input.charAt(i);
            if ((c >= 'a') && (c <= 'z')) {
                // lower char
            } else if ((c >= 'A') && (c <= 'Z')) {
                // upper char
            } else if ((c >= '0') && (c <= '9')/* && (i != 0)*/) {
                // as of 31 Jan 2004, i relax the email checking
                // so that the email can start with an numeric char
                // hopefully it does not introduce a security bug
                // because this value will be inserted into sql script

                // numeric char
            } else if ( ( (c=='_') || (c=='-') || (c=='.') || (c=='@') ) && (i != 0) ) {
                // _ char
            } else {
                // not good char, throw an BadInputException
                //@todo : localize me
                throw new BadInputException(input + " is not a valid email. Reason: character '" + c + "' is not accepted in an email.");
            }
        }// for

        // last check
        try {
            new javax.mail.internet.InternetAddress(input);
        } catch (Exception ex) {
            log.error("Error when running checkGoodEmail", ex);
            throw new BadInputException("Assertion: dont want to occur in Util.checkGoodEmail");
        }
    }

    /**
     * NOTE: param to, cc, bcc cannot be all empty. At least one must have a valid value
     * @param from : must be a valid email. However, if this param is null,
     *              then the default mail in config file will be use
     * @param to : can be null
     * @param cc : can be null
     * @param bcc: can be null
     * @param subject
     * @param message
     * @throws MessagingException
     * @throws BadInputException
     */
    public static void sendMail(String from, String to, String cc, String bcc, String subject, String message)
        throws MessagingException, BadInputException, UnsupportedEncodingException {

        MailBean mailItem = new MailBean();
        mailItem.setFrom(from);
        mailItem.setTo(to);
        mailItem.setCc(cc);
        mailItem.setBcc(bcc);
        mailItem.setSubject(subject);
        mailItem.setMessage(message);

        sendMail(mailItem);
    }

    public static void sendMail(MailBean mailItem)
        throws MessagingException, BadInputException, UnsupportedEncodingException {

        ArrayList mailList = new ArrayList(1);
        mailList.add(mailItem);
        try {
            sendMail(mailList);
        } catch (MessagingException mex) {
            log.error("MessagingException has occured.", mex);
            log.debug("MessagingException has occured. Detail info:");
            log.debug("from = " + mailItem.getFrom());
            log.debug("to = " + mailItem.getTo());
            log.debug("cc = " + mailItem.getCc());
            log.debug("bcc = " + mailItem.getBcc());
            log.debug("subject = " + mailItem.getSubject());
            log.debug("message = " + mailItem.getMessage());
            throw mex;// this may look redundant, but it is not :-)
        }
    }

    public static void sendMail(Collection mailStructCollection)
        throws MessagingException, BadInputException, UnsupportedEncodingException {

        Session session = null;
        Transport transport = null;
        int totalEmails = mailStructCollection.size();
        int count = 0;
        int sendFailedExceptionCount = 0;

        String server = "";
        String userName = "";
        String password = "";
        int port = 25;

        boolean useMailsource = Sysconfig.isUseMailSource();

        try {
            for (Iterator iter = mailStructCollection.iterator(); iter.hasNext(); ) {
                count++;

                if ((transport == null) || (session == null)) {
                    if (useMailsource) {
                        try {
                            InitialContext ic = new InitialContext();
                            // mailSourceName = "java:comp/env/mail/mailSession";
                            String mailSourceName = Sysconfig.getMailSourceName();
                            log.debug("MailUtil : use mailsource = " + mailSourceName);
                            session = (Session) ic.lookup("java:comp/env/" + mailSourceName);
                            transport = session.getTransport("smtp");
                        } catch (NamingException e) {
                            log.error("Cannot get Mail session", e);
                            throw new MessagingException("Cannot get the mail session from JNDI. Send mail failed.");
                        }
                    } else {// does not use datasourse
                        Properties props = new Properties();

                        server = Sysconfig.getMailServer();
                        port = Sysconfig.getMailServerPort();
                        userName = Sysconfig.getMailUserName();
                        password = Sysconfig.getMailPassword();

                        props.put("mail.smtp.host", server);
                        props.put("mail.smtp.port", String.valueOf(port));
                        if ((userName != null) && (userName.length() > 0)) {
                            props.put("mail.smtp.auth", "true");
                        }
                        //props.put("mail.debug", "true");
                        session = Session.getDefaultInstance(props, null);
                        transport = session.getTransport("smtp");
                    }// end of does not use datasource

                    if ((userName != null) && (userName.length() > 0)) {
                        transport.connect(server, userName, password);
                    } else {
                        transport.connect();
                    }
                }

                MailBean mailItem = (MailBean)iter.next();

                String from = mailItem.getFrom();
                String to = mailItem.getTo();
                String cc = mailItem.getCc();
                String bcc = mailItem.getBcc();
                String subject = mailItem.getSubject();
                String message = mailItem.getMessage();

                //if (from == null) from = mailOption.defaultMailFrom;
                if (from == null) from = Sysconfig.getDefaultMailFrom();

                try {
                    // this will also check for email error
                    checkGoodEmail(from);
                    InternetAddress fromAddress = new InternetAddress(from);
                    InternetAddress[] toAddress = getInternetAddressEmails(to);
                    InternetAddress[] ccAddress = getInternetAddressEmails(cc);
                    InternetAddress[] bccAddress = getInternetAddressEmails(bcc);
                    if ((toAddress == null) && (ccAddress == null) && (bccAddress == null)) {
                        //@todo : localize me
                        throw new BadInputException("Cannot send mail since all To, Cc, Bcc addresses are empty.");
                    }

                    // create a message
                    MimeMessage msg = new MimeMessage(session);
                    msg.setSentDate(new Date());
                    msg.setFrom(fromAddress);

                    if (toAddress != null) {
                        msg.setRecipients(Message.RecipientType.TO, toAddress);
                    }
                    if (ccAddress != null) {
                        msg.setRecipients(Message.RecipientType.CC, ccAddress);
                    }
                    if (bccAddress != null) {
                        msg.setRecipients(Message.RecipientType.BCC, bccAddress);
                    }
                    //This code is use to display unicode in Subject
                    //msg.setSubject(MimeUtility.encodeText(subject, "iso-8859-1", "Q"));
                    //msg.setText(message);
                    //String content = new String(message.getBytes(""), "UTF-8");
                    msg.setSubject(subject, "UTF-8");
                    /*msg.setText(message, "UTF-8");*/

                    /*
                     //Below code is use for unicode
                     String content = new String(message.getBytes("UTF-8"), "UTF-8");
                     MimeBodyPart messageBodyPart = new MimeBodyPart();
                     messageBodyPart.setText(message);
                     messageBodyPart.setHeader("Content-Type", "text/html;charset=UTF-8");
                     messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
                     MimeMultipart multipart = new MimeMultipart("alternative");
                     multipart.addBodyPart(messageBodyPart);
                     msg.setContent(multipart);
                    */
                    BodyPart mdp=new MimeBodyPart();//新建一个存放信件内容的BodyPart对象
                    mdp.setContent(message,"text/html;charset=UTF-8");//给BodyPart对象设置内容和格式/编码方式
                    Multipart mm=new MimeMultipart();//新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
                    mm.addBodyPart(mdp);//将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
                    msg.setContent(mm);//把mm作为消息对象的内容
                    msg.saveChanges();
                    transport.sendMessage(msg, msg.getAllRecipients());

                    // now check if sent 100 emails, then close connection (transport)
                    if ((count % MAX_MESSAGES_PER_TRANSPORT) == 0) {
                        try {
                            if (transport != null) transport.close();
                        } catch (MessagingException ex) {}
                        transport = null;
                        session = null;
                    }

                } catch (SendFailedException ex) {
                    sendFailedExceptionCount++;
                    log.error("SendFailedException has occured.", ex);
                    log.warn("SendFailedException has occured. Detail info:");
                    log.warn("from = " + from);
                    log.warn("to = " + to);
                    log.warn("cc = " + cc);
                    log.warn("bcc = " + bcc);
                    log.warn("subject = " + subject);
                    log.info("message = " + message);
                    if ((totalEmails != 1) && (sendFailedExceptionCount > 10)) {
                        throw ex;// this may look redundant, but it is not :-)
                    }
                } catch (MessagingException mex) {
                    log.error("MessagingException has occured.", mex);
                    log.warn("MessagingException has occured. Detail info:");
                    log.warn("from = " + from);
                    log.warn("to = " + to);
                    log.warn("cc = " + cc);
                    log.warn("bcc = " + bcc);
                    log.warn("subject = " + subject);
                    log.info("message = " + message);
                    throw mex;// this may look redundant, but it is not :-)
                }
            }//for
        } finally {
            try {
                if (transport != null) transport.close();
            } catch (MessagingException ex) { }
            if (totalEmails != 1) {
                log.info("sendMail: totalEmails = " + totalEmails + " sent count = " + count);
            }
        }
    }

    /**
     * This method trim the email variable, so if it contains only spaces,
     * then it will be empty string, then we have 0 token :-)
     * The returned value is never null
     */
    public static String[] getEmails(String email) throws BadInputException {
        if (email == null) email = "";
        email = email.trim();// very important
        email = email.replace(',', ';');// replace all occurrence of ',' to ';'
        StringTokenizer t = new StringTokenizer(email, ";");
        String[] ret = new String[t.countTokens()];
        int index = 0;
        while(t.hasMoreTokens()) {
            String mail = t.nextToken().trim();
            checkGoodEmail(mail);
            ret[index] = mail;
            //log.debug(ret[index]);
            index++;
        }
        return ret;
    }

    public static String[] getEmails(String to, String cc, String bcc) throws BadInputException {
        String[] toMail = getEmails(to);
        String[] ccMail = getEmails(cc);
        String[] bccMail= getEmails(bcc);
        String[] ret = new String[toMail.length + ccMail.length + bccMail.length];
        int index = 0;
        for (int i = 0; i < toMail.length; i++) {
            ret[index] = toMail[i];
            index++;
        }
        for (int i = 0; i < ccMail.length; i++) {
            ret[index] = ccMail[i];
            index++;
        }
        for (int i = 0; i < bccMail.length; i++) {
            ret[index] = bccMail[i];
            index++;
        }
        return ret;
    }

    /**
     * This method will return null if there is not any email
     *
     * @param email
     * @return
     * @throws BadInputException
     * @throws AddressException
     */
    private static InternetAddress[] getInternetAddressEmails(String email)
        throws BadInputException, AddressException {
        String[] mails = getEmails(email);
        if (mails.length == 0) return null;// must return null, not empty array

        //log.debug("to = " + mails);
        InternetAddress[] address = new InternetAddress[mails.length];
        for (int i = 0; i < mails.length; i++) {
            address[i] = new InternetAddress(mails[i]);
            //log.debug("to each element = " + mails[i]);
        }
        return address;
    }

}
