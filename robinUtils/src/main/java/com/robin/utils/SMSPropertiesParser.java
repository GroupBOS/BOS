package com.robin.utils;

import java.io.IOException;
import java.util.Properties;

import com.aliyuncs.auth.StaticCredentialsProvider;

/**  
 * ClassName:PropertiesParser <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 上午11:14:36 <br/>       
 */
public class SMSPropertiesParser {
    private static final String properties_file = "/sms.properties";

    public static String parser(String prop) throws IOException
    {
        Properties properties = new Properties();
        properties.load(SMSPropertiesParser.class.getResourceAsStream(properties_file));
        String ret = properties.getProperty(prop);
        return ret;
    }
}
  
