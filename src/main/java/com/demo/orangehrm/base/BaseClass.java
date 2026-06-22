package com.demo.orangehrm.base;


import com.demo.orangehrm.actionDriver.ActionDriver;
import com.demo.orangehrm.utils.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;


public class BaseClass {
    private static final Logger logger = LoggerFactory.getLogger(BaseClass.class);

    private static Properties properties = null; // remember why made static ?
    //private static WebDriver driver = null;
    //private static WebDriverWait wait = null;
    //private static ActionDriver actionDriver = null;

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

    public static WebDriverWait getWait() {
        return wait.get();
    }

    public static Properties getProperties() {
        return properties;
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static ActionDriver getActionDriver() {
        return actionDriver.get();
    }

    @BeforeSuite
    public void loadConfig() {
        //Load Config.properties
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
            properties.load(fileInputStream);
            //Initianlize Extent Reports
            ExtentManager.getReporter();
        } catch (Exception e) {
            logger.error("Load Config Failed: ", e);
        }
    }

    @BeforeMethod
    public synchronized void setup() {
        try {
            selectBrowser();
            launchBrowser();
            //Intialize wait and actionDriver
            wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(2)));
            actionDriver.set(new ActionDriver(getDriver(), getWait()));
        } catch (Exception e) {
            logger.error("Set-up Failed: ", e);
        }
    }

    @AfterMethod
    public synchronized void tearDown() {
        driver.get().quit();
        driver.remove();
        actionDriver.remove();
        wait.remove();
        //ExtentManager.endTest();
    }

    public void selectBrowser() {
        //Choose Browser
        try {
            switch (properties.getProperty("browser")) {
                case "chrome":
                    //driver = new ChromeDriver();
                    driver.set(new ChromeDriver());
                    ExtentManager.registerDriver(driver.get());
                    break;
                case "edge":
                    //driver = new EdgeDriver();
                    driver.set(new EdgeDriver());
                    ExtentManager.registerDriver(driver.get());
                    break;
                case "firefox":
                    //driver = new FirefoxDriver();
                    driver.set(new FirefoxDriver());
                    ExtentManager.registerDriver(driver.get());
                    break;
                default:
                    throw new RuntimeException("Browser is not valid");
            }
        } catch (Exception e) {
            logger.error("Select Browser Failed: ", e);
        }
    }

    public void launchBrowser() {
        //Launch Browser
        try {
            driver.get().get(properties.getProperty("local.url"));
            driver.get().manage().window().maximize();
            int implicitWait = Integer.parseInt(properties.getProperty("implicitwait"));
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        } catch (Exception e) {
            logger.error("Launch Browser Failed: ", e);
        }
    }

}
