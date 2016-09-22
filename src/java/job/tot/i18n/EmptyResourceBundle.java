/*
 * EmptyResourceBundle.java
 *
 * Created on 2007��1��24��, ����10:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.i18n;
import java.util.ListResourceBundle;

public class EmptyResourceBundle extends ListResourceBundle {

    public EmptyResourceBundle() {}

    protected Object[][] getContents() {
        return new Object[][] {

                { "emptykey", "Empty Value from EmptyResourceBundle" },

        };
    }
}
