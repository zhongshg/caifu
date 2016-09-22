/*
 * Configuration.java
 *
 * Created on 2006��7��18��, ����12:17
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.xml;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

/**
 * Configuration interface.
 * The general idea here is to make something that will work like our
 * extended properties and be compatible with the preferences API if at all
 * possible.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:mpoeschl@marmot.at">Martin Poeschl</a>
 * @version $Id: Configuration.java,v 1.3 2004/06/27 01:20:38 skoehler Exp $
 */
public interface Configuration
{
    /**
     * Create an Configuration object that is a subset
     * of this one. The new Configuration object contains every key from
     * the current Configuration that starts with prefix. The prefix is
     * removed from the keys in the subset.
     *
     * @param prefix The prefix used to select the properties.
     */
    Configuration subset(String prefix);

    /**
     * Check if the configuration is empty.
     *
     * @return true is the configuration contains no key/value pair, false otherwise
     */
    boolean isEmpty();

    /**
     * Check if the configuration contains the key.
     *
     * @return true is the configuration contains a value for this key, false otherwise
     */
    boolean containsKey(String key);

    /**
     * Add a property to the configuration. If it already exists then the value
     * stated here will be added to the configuration entry. For example, if
     *
     * resource.loader = file
     *
     * is already present in the configuration and you
     *
     * addProperty("resource.loader", "classpath")
     *
     * Then you will end up with a Vector like the following:
     *
     * ["file", "classpath"]
     *
     * @param key The Key to add the property to.
     * @param value The Value to add.
     */
    void addProperty(String key, Object value);

    /**
     * Set a property, this will replace any previously
     * set values. Set values is implicitly a call
     * to clearProperty(key), addProperty(key,value).
     *
     * @param key The key of the property to change
     * @param value The new value
     */
    void setProperty(String key, Object value);

    /**
     * Clear a property in the configuration.
     *
     * @param key the key to remove along with corresponding value.
     */
    void clearProperty(String key);

    /**
     *  Gets a property from the configuration.
     *
     *  @param key property to retrieve
     *  @return value as object. Will return user value if exists,
     *          if not then default value if exists, otherwise null
     */
    Object getProperty(String key);

    /**
     * Get the list of the keys contained in the configuration
     * repository that match the specified prefix.
     *
     * @param prefix The prefix to test against.
     * @return An Iterator of keys that match the prefix.
     */
    Iterator getKeys(String prefix);

    /**
     * Get the list of the keys contained in the configuration
     * repository.
     *
     * @return An Iterator.
     */
    Iterator getKeys();

    /**
     * Get a list of properties associated with the given
     * configuration key.
     *
     * @param key The configuration key.
     * @return The associated properties if key is found.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a String/Vector.
     * @exception IllegalArgumentException if one of the tokens is
     * malformed (does not contain an equals sign).
     */
    Properties getProperties(String key);

    // We probably want to at least try to be compatible with
    // the new preferences API.

    /**
     * Get a boolean associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated boolean.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     * map to an existing object.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Boolean.
     */
    boolean getBoolean(String key);

    /**
     * Get a boolean associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated boolean.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Boolean.
     */
    boolean getBoolean(String key, boolean defaultValue);

    /**
     * Get a boolean associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated boolean if key is found and has valid
     * format, default value otherwise.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Boolean.
     */
    Boolean getBoolean(String key, Boolean defaultValue);

    /**
     * Get a byte associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated byte.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     * map to an existing object.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Byte.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    byte getByte(String key);

    /**
     * Get a byte associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated byte.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Byte.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    byte getByte(String key, byte defaultValue);

    /**
     * Get a byte associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated byte if key is found and has valid format, default
     *         value otherwise.
     * @exception ClassCastException is thrown if the key maps to an object that
     *            is not a Byte.
     * @exception NumberFormatException is thrown if the value mapped by the key
     *            has not a valid number format.
     */
    Byte getByte(String key, Byte defaultValue);

    /**
     * Get a double associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated double.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     * map to an existing object.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Double.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    double getDouble(String key);

    /**
     * Get a double associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated double.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Double.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    double getDouble(String key, double defaultValue);

    /**
     * Get a double associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated double if key is found and has valid
     * format, default value otherwise.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Double.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    Double getDouble(String key, Double defaultValue);

    /**
     * Get a float associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated float.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     * map to an existing object.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Float.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    float getFloat(String key);

    /**
     * Get a float associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated float.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Float.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    float getFloat(String key, float defaultValue);

    /**
     * Get a float associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated float if key is found and has valid
     * format, default value otherwise.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Float.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    Float getFloat(String key, Float defaultValue);

    /**
     * Get a int associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated int.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     * map to an existing object.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Integer.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    int getInt(String key);

    /**
     * Get a int associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated int.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Integer.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    int getInt(String key, int defaultValue);

    /**
     * Get a int associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated int if key is found and has valid format, default
     *         value otherwise.
     * @exception ClassCastException is thrown if the key maps to an object that
     *         is not a Integer.
     * @exception NumberFormatException is thrown if the value mapped by the key
     *         has not a valid number format.
     */
    Integer getInteger(String key, Integer defaultValue);

    /**
     * Get a long associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated long.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     * map to an existing object.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Long.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    long getLong(String key);

    /**
     * Get a long associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated long.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Long.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    long getLong(String key, long defaultValue);

    /**
     * Get a long associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated long if key is found and has valid
     * format, default value otherwise.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Long.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    Long getLong(String key, Long defaultValue);

    /**
     * Get a short associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated short.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     * map to an existing object.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Short.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    short getShort(String key);

    /**
     * Get a short associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated short.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Short.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     */
    short getShort(String key, short defaultValue);

    /**
     * Get a short associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated short if key is found and has valid
     * format, default value otherwise.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Short.
     * @exception NumberFormatException is thrown if the value mapped
     * by the key has not a valid number format.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     * map to an existing object.
     */
    Short getShort(String key, Short defaultValue);

    /**
     * Get a string associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated string.
     * @exception ClassCastException is thrown if the key maps to an object that
     *            is not a String.
     * @exception java.util.NoSuchElementException is thrown if the key doesn't
     *         map to an existing object.
     */
    String getString(String key);

    /**
     * Get a string associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated string if key is found and has valid
     * format, default value otherwise.
     * @exception ClassCastException is thrown if the key maps to an object that
     *            is not a String.
     */
    String getString(String key, String defaultValue);

    /**
     * Get an array of strings associated with the given configuration
     * key.
     *
     * @param key The configuration key.
     * @return The associated string array if key is found.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a String/Vector of Strings.
     */
    String[] getStringArray(String key);

    /**
     * Get a Vector of strings associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated Vector.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Vector.
     */
    Vector getVector(String key);

    /**
     * Get a Vector of strings associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated Vector.
     * @exception ClassCastException is thrown if the key maps to an
     * object that is not a Vector.
     */
    Vector getVector(String key, Vector defaultValue);
}