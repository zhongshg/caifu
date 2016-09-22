/*
 * BasePathConfiguration.java
 *
 * Created on 2006��7��18��, ����12:13
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.xml;

/**
 *
 * @author Administrator
 */
public abstract class BasePathConfiguration
        extends BaseConfiguration
        implements BasePathLoader
{
    /**
     * Base path of the configuration file used to
     * create this Configuration object. Might be null, then a
     * "synthetic" PropertyConfiguration has been created which
     * is not loaded from a file
     */
    private String basePath = null;

    /**
     * Returns the Base path from which this Configuration Factory operates.
     * This is never null. If you set the BasePath to null, then "."
     * is returned.
     *
     * @return The base Path of this configuration factory.
     */
    public String getBasePath()
    {
        return basePath;
    }

    /**
     * Sets the basePath for all file references from this Configuration
     * Factory. If you pass null in, this is interpreted as "current
     * directory".
     *
     * @param basePath The new basePath to set.
     */
    public void setBasePath(String basePath)
    {
        this.basePath = basePath;
    }

}
