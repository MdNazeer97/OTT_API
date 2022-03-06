package com.api.framework.StepDefinition;


import com.api.framework.base.TestBase;
import com.api.framework.methods.RestClient;
import com.api.framework.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class OTTSteps extends TestBase {
    TestBase testBase;
    String serviceUrl;
    String apiUrl;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;


    @Given("user hits the api url")
    public void userHitstheURL() throws IOException {
        testBase = new TestBase();
        serviceUrl = prop.getProperty("URL");
        apiUrl = prop.getProperty("serviceURL");
        url = serviceUrl + apiUrl;
        System.out.println("Desired output is:" + url);
        System.out.println("================= END ===============");
    }

    @When("user get the status code")
    public void userGetTheValidStatusCode() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code--->" + statusCode);
        System.out.println("================= END ===============");
    }

    @Then("user validate the status code as {string}")
    public void userValidateTheStatusCodeAs(String statusCode) {
        Assert.assertEquals(Integer.parseInt(statusCode), RESPONSE_STATUS_CODE_200, "Status code is not 200");
        System.out.println("Expected = " + statusCode);
        System.out.println("================= END ===============");
    }

    @When("user create Response String")
    public void userCreateResponseString() throws IOException {
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        System.out.println("================= END ===============");
    }

    @When("user get the Response")
    public void userGetResponseInJsonFormat() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        String fileName = "ottallResponse.json";
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API---->" + responseJson);
        System.out.println("================= END ===============");
    }

    @Then("user get All the Header without Headertype")
    public void userGetAllTheHeader() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        Header[] headerArray = closeableHttpResponse.getAllHeaders();

        HashMap<String, String> allHeaders = new HashMap<String, String>();

        for (Header header : headerArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Headers Array--->" + allHeaders);
        System.out.println("================= END ===============");
    }

    @Then("user get All the Response Header with Headertype")
    public void userGetAllTheResponseHeader() throws IOException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        closeableHttpResponse = restClient.get(url, headerMap);
        Header[] headerArray = closeableHttpResponse.getAllHeaders();

        HashMap<String, String> allHeaders = new HashMap<String, String>();

        for (Header header : headerArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Headers Array--->" + allHeaders);
        System.out.println("================= END ===============");
    }

    @Then("user get All details for first")
    public void usergetAlldetails() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API---> " + responseJson);

        File file = new File("response.json");
        if (file.createNewFile()) {
            FileWriter fwrite = new FileWriter("response.json");
            fwrite.write(responseString);
            fwrite.close();

        } else {
            System.out.println("$$$$$$$$$$$$$$$$$  Failed $$$$$$$$$$$$$$$$$$$$$");
        }
        System.out.println("================= END ===============");


    }

    @Then("user get path")
    public void userGetPath() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        String rep = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject response = new JSONObject(rep);
        System.out.println("================  RESPONSE  ================");
        System.out.println(response);
        System.out.println("================  RESPONSE for selected  DATA 0 ================");
        String path = TestUtil.getValueByJPath(response, "promotions[0]");
        System.out.println(path);
        System.out.println("================  RESPONSE for selected  DATA 1 ================");
        String path1 = TestUtil.getValueByJPath(response, "promotions[1]");
        System.out.println(path1);
        System.out.println("================  RESPONSE for selected  DATA 2 ================");
        String path2 = TestUtil.getValueByJPath(response, "promotions[2]");
        System.out.println(path2);
        System.out.println("================  RESPONSE for selected  DATA 3 ================");
        String path3 = TestUtil.getValueByJPath(response, "promotions[3]");
        System.out.println(path3);
        System.out.println("================  RESPONSE for selected  DATA 4 ================");
        String path4 = TestUtil.getValueByJPath(response, "promotions[4]");
        System.out.println(path4);
        System.out.println("================  RESPONSE for selected  DATA 5 ================");
        String path5 = TestUtil.getValueByJPath(response, "promotions[5]");
        System.out.println(path5);



        File file = new File("userdetails.json");
        if (file.createNewFile()) {
            FileWriter fwrite = new FileWriter("response.json");
            fwrite.write(rep);
            fwrite.close();

        } else {
            System.out.println("$$$$$$$$$$$$$$$$$  Failed $$$$$$$$$$$$$$$$$$$$$");

        }
    }

    @Then("user get details from array path")
    public void userGetDetailsFromArrayPath() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        String rep = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject response = new JSONObject(rep);

        System.out.println("================  RESPONSE  ================");
        System.out.println(response);

        String pID1 = TestUtil.getValueByJPath(response, "promotions[0]/promotionId");
        System.out.println(pID1);
        String pID2 = TestUtil.getValueByJPath(response, "promotions[1]/promotionId");
        System.out.println(pID2);
        String pID3 = TestUtil.getValueByJPath(response, "promotions[2]/promotionId");
        System.out.println(pID3);
        String pID4 = TestUtil.getValueByJPath(response, "promotions[3]/promotionId");
        System.out.println(pID4);
        String pID5 = TestUtil.getValueByJPath(response, "promotions[4]/promotionId");
        System.out.println(pID5);
        String pID6 = TestUtil.getValueByJPath(response, "promotions[5]/promotionId");
        System.out.println(pID6);
    }
}