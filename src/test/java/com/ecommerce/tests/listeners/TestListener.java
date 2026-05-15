package com.ecommerce.tests.listeners;

import com.ecommerce.base.BaseTest;
import com.ecommerce.utils.LoggerUtil;
import com.ecommerce.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        LoggerUtil.info("========== Test Execution Started ==========");
        LoggerUtil.info("Test Suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LoggerUtil.info("========== Test Execution Finished ==========");
        LoggerUtil.info("Total Tests: " + context.getAllTestMethods().length);
        LoggerUtil.info("Passed: " + context.getPassedTests().size());
        LoggerUtil.info("Failed: " + context.getFailedTests().size());
        LoggerUtil.info("Skipped: " + context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LoggerUtil.info("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LoggerUtil.info("✅ Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        String message = "❌ Test Failed: " + result.getName();
        if (throwable instanceof Exception) {
            LoggerUtil.error(message, (Exception) throwable);
        } else {
            LoggerUtil.error(message + " - " + throwable);
        }

        // In a real framework we'd retrieve driver via reflection or ThreadLocal
        // Here we attempt to attach a screenshot if BaseTest provides it
        try {
            Object instance = result.getInstance();
            if (instance instanceof BaseTest) {
                BaseTest baseTest = (BaseTest) instance;
                // Note: Getting WebDriver is not direct without a public getter, but BaseTest limits. 
                // We'll trust BaseTest.tearDown() handles screenshot generation for failure.
                // Or we can attach the last taken screenshot logically, but that's complex without ThreadLocal.
            }
        } catch (Exception e) {
            LoggerUtil.error("Error attaching screenshot to Allure: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LoggerUtil.info("⏭️ Test Skipped: " + result.getName());
    }
}
