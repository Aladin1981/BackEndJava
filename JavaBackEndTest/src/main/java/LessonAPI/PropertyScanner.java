package LessonAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyScanner  {
    private Properties properties;

    public PropertyScanner() throws IOException {
        properties = new Properties();
        FileInputStream in = new FileInputStream("C:\\java\\JavaBackEndTest\\src\\main\\resources\\application.properties");
        properties.load(in);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
