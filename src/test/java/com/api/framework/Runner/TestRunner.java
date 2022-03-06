package com.api.framework.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/com/api/framework/Feature/"}, plugin = {"pretty",
        "html:target/cucumber-html-reports.html",
        "json:target/cucumber_reports/Cucumber.json", "rerun:target/rerun.txt",
        "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"},
        glue = {"com/api/framework/StepDefinition"},
        tags = "@IntigralAPI",
        dryRun = false,
        stepNotifications = true)
public class TestRunner {

}
