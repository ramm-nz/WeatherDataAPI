package com.qa.weatherapi.base;

import org.testng.annotations.BeforeTest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    Properties prop;
    protected String baseURI,userKey,endPoint,cityName,invalidCityName;
    @BeforeTest
    public void setUp() throws IOException {
        baseURI=initProp().getProperty("baseuri");
        userKey=initProp().getProperty("userkey");
        endPoint=initProp().getProperty("endpoint");
        cityName=initProp().getProperty("city");
        invalidCityName=initProp().getProperty("invalidCity");
    }

    public Properties initProp() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./src/test/resources/config/config.properties");
        prop = new Properties();
        prop.load(fileInputStream);
        return prop;
    }
}
