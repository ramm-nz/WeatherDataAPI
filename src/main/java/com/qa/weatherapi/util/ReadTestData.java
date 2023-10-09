package com.qa.weatherapi.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ReadTestData {
    public String getData(String key) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("./src/test/resources/testData/WeatherTestData.json"));
         key = (String) jsonObject.get(key);
         return key;
    }
}
