package example.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Context {

    static Properties properties;

    public static void setupConfiguration() {
        properties = new Properties();
        final String TEST_PROPERTIES_PATH = "build/test.properties";
        try {
            properties.load(new FileInputStream(TEST_PROPERTIES_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return value.equalsIgnoreCase("true");
    }

    public static Integer getIntProperty(String key) {
        String value = getProperty(key);
        return Integer.parseInt(value);
    }

    public static boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public static String getProperty(String key) {
        if (!properties.containsKey(key))
            throw new RuntimeException("Missing expected context property '" + key + "'. It could be caused by setting up incorrect environment in the config.groovy.");

        return properties.getProperty(key);
    }

    public static boolean isPropertyNull(String key) {
        String value = getProperty(key);
        return value == null || value.equals("null");
    }


    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static String getEnv() {
        return getProperty("env");
    }

    public static String getUserName() {
        return getProperty("databucket.username");
    }

}

