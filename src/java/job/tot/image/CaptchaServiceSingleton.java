/*
 * CaptchaServiceSingleton.java
 *
 * Created on 2006��7��11��, ����10:12
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.image;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.engine.image.gimpy.*;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;


/**
 *
 * @author Administrator
 */
public class CaptchaServiceSingleton {
    
    //private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService(new FastHashMapCaptchaStore(),new SimpleListImageCaptchaEngine(),180,100000,75000);
    private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService(new FastHashMapCaptchaStore(),new SimpleListImageByNumonlyEngine(),180,100000,75000);
    public static ImageCaptchaService getInstance(){
        return instance;
    }
}
