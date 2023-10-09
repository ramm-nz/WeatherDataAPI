package com.qa.weatherapi.tests;

import com.qa.weatherapi.base.BaseTest;
import com.qa.weatherapi.util.ReadTestData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class WeatherDataApiTest extends BaseTest {

    ReadTestData readTestData = new ReadTestData();

    @Test(priority = 1, description = "Verify the data accuracy of weather data GET call")
    public void getWeatherDataAccuracyTest() throws IOException, ParseException {
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON);
        Response response = httpRequest.queryParam("key",userKey).get(endPoint+cityName);
        response.then().assertThat().statusCode(200);
        ResponseBody body = response.body();
        String responseBody = body.asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        String latitude = jsonPath.getString("latitude");
        String longitude = jsonPath.getString("longitude");
        String resolvedAddress = jsonPath.getString("resolvedAddress");
        String address = jsonPath.getString("address");
        String timezone = jsonPath.getString("timezone");
        String tzoffset = jsonPath.getString("tzoffset");

        //Assert the accuracy of the GET call weather data
        Assert.assertEquals(latitude,readTestData.getData("latitude"));
        Assert.assertEquals(longitude,readTestData.getData("longitude"));
        Assert.assertEquals(resolvedAddress,readTestData.getData("resolvedAddress"));
        Assert.assertEquals(address,readTestData.getData("address"));
        Assert.assertEquals(timezone,readTestData.getData("timezone"));
        Assert.assertEquals(tzoffset,readTestData.getData("tzoffset"));
    }

    @Test(priority = 2, description = "Verify that weather data get call is successfully responds under 3seconds")
    public void getWeatherDataResponseTimeTest() throws IOException, ParseException {
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON);
        Response response = httpRequest.queryParam("key",userKey).get(endPoint+cityName);

        //Verify the GET call is successful
        response.then().log().all().assertThat().
                statusCode(200).
                time(Matchers.lessThan(3000L));
        Assert.assertEquals(response.getContentType(), readTestData.getData("contentType"));
    }

    @Test(priority = 3, description = "Verify that proper error message is thrown for invalid data")
    public void getWeatherDataNegativeTest() throws IOException, ParseException {
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON);
        Response response = httpRequest.queryParam("key",userKey).get(endPoint+invalidCityName);
        response.then().log().all().assertThat().
                statusCode(400);

        Assert.assertEquals(response.getBody().asString(),readTestData.getData("invalidresponse"));
    }
}
