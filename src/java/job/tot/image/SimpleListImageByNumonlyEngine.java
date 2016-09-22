/*
 * SimpleListImageByNumonlyEngine.java
 *
 * Created on 2006��7��25��, ����6:06
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.image;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import java.awt.*;

/**
 *
 * @author Administrator
 */
public class SimpleListImageByNumonlyEngine extends com.octo.captcha.engine.image.ListImageCaptchaEngine {
    
    protected void buildInitialFactories() {
        com.octo.captcha.component.word.wordgenerator.WordGenerator wordGenerator = new com.octo.captcha.component.word.wordgenerator.RandomWordGenerator(
                "abcdefghjkmnpqrstuvwxyz23456789");
        TextPaster textPaster = new RandomTextPaster(new Integer(4),
                new Integer(6), Color.white);
        //BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator( new Integer(100), new Integer(45),Color.RED, Color.GREEN);
        BackgroundGenerator backgroundGenerator =new FunkyBackgroundGenerator(new Integer(150), new Integer(50));
        Font[] fonts=new Font[2];
        Font boldFont = new Font("Times New Roman",Font.PLAIN,18);
        fonts[0]=boldFont;
        FontGenerator fontGenerator = new RandomFontGenerator(
            new Integer(25), new Integer(26));
        com.octo.captcha.component.image.wordtoimage.WordToImage wordToImage = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                fontGenerator, backgroundGenerator, textPaster);
        this.addFactory(
                new com.octo.captcha.image.gimpy.GimpyFactory(wordGenerator,
                wordToImage));
    }
    
    
}
