package com.demo.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    private static ExtentReports reports = null;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> webDriverMap = new HashMap<>();

    public synchronized static ExtentReports getReporter() {
        if (reports == null) {
            //Spark
            ExtentSparkReporter spark = new ExtentSparkReporter("src/test/java/resources/extentreport");
            spark.config().setReportName("OrangeHRMAutomation");
            spark.config().setDocumentTitle("OrangeHRMAutomationTesting");
            //Report
            reports = new ExtentReports();
            reports.attachReporter(spark);
            reports.setSystemInfo("Operating System", System.getProperty("os.name"));
            reports.setSystemInfo("Java Version", System.getProperty("java.version"));
            reports.setSystemInfo("User Name", System.getProperty("user.name"));
        }
        return reports;
    }

    //Register WebDriver
    public synchronized static void registerDriver(WebDriver driver) {
        webDriverMap.put(Thread.currentThread().getId(), driver);
    }

    // Start the Test
    public synchronized static ExtentTest startTest(String testName) {
        ExtentTest extentTest = getReporter().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    //End a Test
    public synchronized static void endTest() {
        getReporter().flush();
    }

    //Get Current Thread's test
    public synchronized static ExtentTest getTest() {
        return test.get();
    }

    //Method to get the name of the current test
    public synchronized static String getTestName() {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            return currentTest.getModel().getName();
        } else {
            return "No test is currently active for this thread";
        }
    }


    //Log a step
    public synchronized static void logStep(String logMessage) {
        getTest().info(logMessage);
    }

    //Log a step validation with screenshot
    public synchronized static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenShotMessage) {
        getTest().pass(logMessage);
        //Screenshot method
        attachScreenshot(driver, screenShotMessage);

    }

    //Log a Failure
    public synchronized static void logFailure(WebDriver driver, String logMessage, String screenShotMessage) {
        String colorMessage = String.format("<span style=\"color:red;\">%s</span>", logMessage);
        getTest().fail(colorMessage);
        //Screenshot method
        attachScreenshot(driver, screenShotMessage);
    }


    //Log a skip
    public synchronized static void logSkip(String logMessage) {
        String colorMessage = String.format("<span style = \"color:orange;\" > % s </span > ", logMessage);
        getTest().skip(colorMessage);
    }

    //Take a screenshot with date and time in the file
    public synchronized static String takeScreenshot(WebDriver driver, String screenshotName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        //Format date and Time for file name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        //Saving the screenshot to a file
        String destPath = "/src/test/resources/screenshots/" + screenshotName + "_" + timeStamp + ".png";

        File finalPath = new File(destPath);
        try {
            FileUtils.copyFile(src, finalPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Convert screenshot to Base64 fir embedding in the Report
        String base64Format = convertToBase64(src);
        return base64Format;
    }


    //Convert screenshot to Base64 format
    public synchronized static String convertToBase64(File screenShotFile) {
        String base64Format = "";
        //Read the file content into a byte array
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(screenShotFile);
            base64Format = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return base64Format;
    }

    //Attach screenshot to report using Base64
    public synchronized static void attachScreenshot(WebDriver driver, String message) {
        try {
            String screenShotBase64 = takeScreenshot(driver, getTestName());
            getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
        } catch (Exception e) {
            getTest().fail("Failed to attach screenshot:" + message);
            e.printStackTrace();
        }
    }
}
