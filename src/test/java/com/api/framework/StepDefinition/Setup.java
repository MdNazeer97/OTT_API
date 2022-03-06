package com.api.framework.StepDefinition;

import com.api.framework.util.CacheUtil;
import com.api.framework.util.Log;
import com.api.framework.util.Reporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Setup {

    Reporter reporter = new Reporter();

    @Before
    public void setUp(Scenario scenario) {

        CacheUtil.cacheInfo.put("SCENARIO NAME", scenario.getName());

        if (CacheUtil.skipTestCaseFlag) {
            throw new RuntimeException(
                    "Skipping the scenario : " + scenario.getName() + "as test data creation got failed");
        } else {
            Log.startTestCase(scenario.getName());
        }

    }

    @After
    public void tearDown(Scenario scenario) {

        CacheUtil.cacheInfo.put("STATUS", scenario.getStatus());
        reporter.generateInProgressHtmlReport(scenario, "NA");

        if (scenario.getSourceTagNames().contains("@CREATETESTDATA") && scenario.isFailed()) {
            CacheUtil.skipTestCaseFlag = true;
        } else if (!CacheUtil.skipTestCaseFlag) {
            Log.endTestCase();
        }

    }

}
