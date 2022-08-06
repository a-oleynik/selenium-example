package com.oleynik.gradle.selenium.example.framework.listeners;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.TestResult;

import java.util.List;
import java.util.Optional;

import static com.oleynik.gradle.selenium.example.framework.utils.DateTimeUtils.getDateTimeForAllureConsoleLog;


public class AllureTestListener implements TestLifecycleListener {

    private static final String REPORT_DELIMITER =
            "---------------------------------------------------------------------------------------------------------";
    @Override
    public void beforeTestStart(TestResult result){
        String testInfo = REPORT_DELIMITER + "\n" +
                getDateTimeForAllureConsoleLog() + ": \n\"" + result.getName() + "\"" + " test started\n" +
                result.getFullName() + "\n" +
                "Feature: " + getFeature(result.getLabels()) +
                "\n" + REPORT_DELIMITER;
        System.out.println(testInfo);
    }

    public void afterTestStop(TestResult result){
        Label testMethodLabel = result.getLabels()
                .stream()
                .filter(label -> label.getName().equals("testMethod"))
                .findFirst().get();

        String testInfo = REPORT_DELIMITER + "\n" +
                getDateTimeForAllureConsoleLog() + ": \n\"" + testMethodLabel.getValue() + "\"" + " test finished\n" +
                result.getFullName() + "\n" +
                "Test status: " + result.getStatus() +
                "\n" + REPORT_DELIMITER;
        System.out.println(testInfo);
    }

    private String getFeature(List<Label> allureLabels) {
        Optional<Label> optionalLabel = allureLabels.stream()
                .filter(label -> label.getName().equals("feature"))
                .findFirst();
        if(optionalLabel.isPresent()){
            return optionalLabel.get().getValue();
        }
        return "";
    }
}
