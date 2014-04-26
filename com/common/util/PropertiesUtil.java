package com.common.util;

import java.util.Properties;
import java.io.InputStream;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.List;
import java.util.ArrayList;
import java.io.UnsupportedEncodingException;

/**
 * <p>Description: </p>
 * Collection of some handy methods to handle properties files
 * and (key, value) pairs
 */

public class PropertiesUtil {

    private static Log log = LogFactory.getLog(PropertiesUtil.class);

    /**
     * load a properties file and return a instance of Properties
     * @param path String
     * @return Properties
     */
    public static Properties load(String path) {
        if(path == null || path.equals("")) {
            return null;
        }

        InputStream is = PropertiesUtil.class.getResourceAsStream(path);
        Properties properties = new Properties();
        try {
            properties.load(is);
        }
        catch (Exception e) {
            throw new RuntimeException(
                "can't load options properties: " + path);
        }
        return properties;
    }

    /**
     * split the values assigned to a single property key.
     * skip white spaces
     * if *values* is null or empty, return a zero length array
     * @param values String
     * @return String[]
     */
    public static String[] split(String values) {
        return split(values, " ");
    }

    /**
     * split the values assigned to a single property key.
     * skip white spaces between values
     * if *values* is null or empty, return a zero length array
     * if *delim* is null or empty, return a one length array
     * CAUTION: delim is used as regular expression, so we must
     * use backslash character ('\') to escape some special character
     * containing *values* itself
     * @param values String
     * @return String[]
     */
    public static String[] split(String values, String delim) {
        //check arguments
        if (values == null || values.equals("")) {
            return EMPTY_ARRAY;
        }
        else if (delim == null || delim.equals("")) {
            return new String[] {values};
        }

        //split values, and filter empty tokens
        String[] origTokens = values.split(delim);

        List buffer = new ArrayList();
        for (int i = 0; i < origTokens.length; i++) {
            String currToken = origTokens[i].trim();
            if (!"".equals(currToken)) {
                buffer.add(currToken);
            }
        }

        String[] tokens = new String[buffer.size()];

        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = (String) buffer.get(i);
        }

        return tokens;
    }
    private static String[] EMPTY_ARRAY = new String[0];

    /**
     * encode conversion that properties can be written in Chinaese
     * @param value String
     * @return String
     */
    public static String encode(String value) {
        return encode(value, "8859_1", "GB2312");
    }

    /**
     * encode conversion that properties can be written in other
     * character set
     * @param value String
     * @param srcEncode String
     * @param destEncode String
     * @return String
     */
    public static String encode(String value, String srcEncode, String destEncode) {
        try {
            return new String(value.getBytes(srcEncode), destEncode);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(
                "unsupported encoding used: " + e);
        }
    }

    /**
     * get a subkey of *key* with segment No *start* and *end*, inclusively.
     * *start* and *end* can be negtive.
     * if head and tail segment can be trimed to empty string, then they are
     * discarded.
     * @param key String
     * @param start int
     * @param end int
     * @return String
     */
    public static String subkey(String key, int start, int end) {
        //check arguments
        if(key == null || key.equals("")) {
            return null;
        }

        //split key
        String[] tokens = split(key, "\\.");
        int length = tokens.length;

        //positive the segment number
        start = start < 0 ? start + length : start;
        end = end < 0 ? end + length : end;

        //start must not greater than end
        if(start > end || end >= length) {
            return null;
        }

        //make subkey
        StringBuffer buffer = new StringBuffer();
        for(int i=start; i<end; i++) {
            buffer.append(tokens[i]);
            buffer.append(".");
        }
        buffer.append(tokens[end]);
        return buffer.toString();
    }

    /**
     * check *value* is empty or not.
     * empty means *value* is null, is zero length String or
     * contains only white spaces
     * @param value String
     * @return boolean
     */
    public static boolean isEmpty(String value) {
        if(value == null || "".equals(value.trim())) {
            return true;
        }
        return false;
    }
}
