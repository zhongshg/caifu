/*
 * BasePathLoader.java
 *
 * Created on 2006��7��18��, ����12:15
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.xml;

public interface BasePathLoader
{
    /**
     * Returns the base path. Relative path names will be resolved based on
     * this path.
     * @return the base path
     */
    String getBasePath();

    /**
     * Sets the base path. Relative path names will be resolved based on
     * this path. For maximum flexibility this base path should be a URL.
     * @param path the base path
     */
    void setBasePath(String path);
}