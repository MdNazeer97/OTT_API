package com.api.framework.util;

import io.cucumber.java.Scenario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Reporter {

    public void generateInProgressHtmlReport(Scenario scenario, String screenShotPath) {
        try {

            // Generate JSON Report for Scenario Data
            String jsonFilePath = generateJsonReport(scenario);

            // Creating Map for HTML Report
            Map<String, Object> map = new HashMap<>();
            map.put("scenarioName", scenario.getName());
            map.put("scenarioStatus", scenario.getStatus().toString());
            map.put("screenshotLink", screenShotPath);
            map.put("total", countStatusOccurences("TOTAL", jsonFilePath));
            map.put("passed", countStatusOccurences("PASSED", jsonFilePath));
            map.put("failed", countStatusOccurences("FAILED", jsonFilePath));
            map.put("skipped", countStatusOccurences("SKIPPED", jsonFilePath));
            map.put("passedPercentage", calculatePercentage(Float.parseFloat(map.get("passed").toString()), Float.parseFloat(map.get("total").toString())));
            map.put("failedPercentage", calculatePercentage(Float.parseFloat(map.get("failed").toString()), Float.parseFloat(map.get("total").toString())));

            // Adding Failure Scenarios to Detailed Report
            addFailedScenarioDetails(map);
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }

    }

    private String generateJsonReport(Scenario scenario) {
        File jsonFile = null;
        int count=0;
        try {
            //Checking File Exists & Create New File
            jsonFile = new File(Constant.JSON_PATH + "progress.json");
            if (!jsonFile.exists())
                jsonFile.createNewFile();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            UUID uniqueKey = UUID.randomUUID();
            if (jsonFile.length() > 0) {
                Object obj = jsonParser.parse(new FileReader(jsonFile));
                jsonArray = (JSONArray) obj;
            }
            jsonObject.put("scenarioId",String.valueOf(uniqueKey));
            jsonObject.put("name", scenario.getName());
            jsonObject.put("status", scenario.getStatus().toString());
            jsonArray.add(jsonObject);
            Files.write(Paths.get(jsonFile.getPath()), jsonArray.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
        return jsonFile.getPath();
    }
    private int countStatusOccurences(String status, String jsonFilePath) {
        int count = 0;
        try {
            JSONParser jsonParser = new JSONParser();
            Object obje2 = jsonParser.parse(new FileReader(jsonFilePath));
            org.json.JSONArray jsonArray = new org.json.JSONArray(obje2.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                org.json.JSONObject element = jsonArray.getJSONObject(i);
                if (status.equals("TOTAL")) {
                    count = jsonArray.length();
                    break;
                } else if (status.equals(element.getString("status"))) {
                    count++;
                }
            }
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
        return count;
    }
    private String calculatePercentage(float x, float total) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(x / total * 100);
    }

    private String getHtml(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        return contentBuilder.toString();

    }
    public void addFailedScenarioDetails(Map<String, Object> map) {
        try {
            //Checking File Exists & Create New File
            String filePath;
            File jsonFile = new File(Constant.JSON_PATH + "progress.html");
            if (jsonFile.exists()) {
                filePath = getHtml(jsonFile.getPath());
            } else {
                filePath = getHtml(Constant.ASSETS_PATH + "progress.html");
            }
            Document doc = Jsoup.parse(filePath, "UTF-8");
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(0).text(map.get("total").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(1).text(map.get("passed").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(2).text(map.get("failed").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(3).text(map.get("skipped").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(4).text(map.get("passedPercentage").toString());
            doc.getElementsByTag("tr").get(1).getElementsByTag("td").get(5).text(map.get("failedPercentage").toString());
            if (map.get("scenarioStatus").equals("FAILED")) {
                Elements table = doc.getElementsByTag("table");
                Elements tbody = table.last().getElementsByTag("tbody");
                Element td = tbody.get(tbody.size() - 1);
                td.append("<tr style=\"font-family: Lato-Regular;font-size: 20px;color: white;line-height: 1.4;background-color: #222;line-height: 1.4;text-align:center;\"> <td style=text-align:left;>" + map.get("scenarioName") + "</td> <td>" + map.get("scenarioStatus") + "</td></tr>");
            }
            Files.write(Paths.get(jsonFile.getPath()), doc.html().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
    }

}
